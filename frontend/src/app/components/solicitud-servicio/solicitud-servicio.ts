import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { Cultivo } from '../../models/cultivo';
import { Servicio } from '../../models/servicio';
import { SolicitudServicio as SolicitudServicioModel } from '../../models/solicitud-servicio';
import { CultivoService } from '../../services/cultivo-service';
import { ServicioService } from '../../services/servicio-service';
import { SolicitudServicioService } from '../../services/solicitud-servicio-service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-solicitud-servicio',
  standalone: false,
  templateUrl: './solicitud-servicio.html',
  styleUrls: ['./solicitud-servicio.css']
})
export class SolicitudServicio implements OnInit, AfterViewInit {
  titulo = 'Solicitud de Servicio';

  cultivos: Cultivo[] = [];
  servicios: Servicio[] = [];
  cultivoFiltroId: number | null = null;

  // Campos del formulario (template-driven)
  cultivoId: number | null = null;
  servicioId: number | null = null;
  fechaSolicitud: Date | null = null;
  estado: string = '';

  mensaje = '';

  private apiUrl = environment.apiUrl;

  constructor(
    private cultivoService: CultivoService,
    private servicioService: ServicioService,
    private http: HttpClient,
    private router: Router,
    private solicitudService: SolicitudServicioService
  ) {}

  // Table
  displayedColumns: string[] = ['id', 'fechaSolicitud', 'cultivo', 'servicio', 'estado'];
  dataSource = new MatTableDataSource<SolicitudServicioModel>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadCultivos();
    this.loadServicios();
    this.loadSolicitudes();
  }

  ngAfterViewInit(): void {
    // connect paginator (will be available after view init)
    this.dataSource.paginator = this.paginator;
  }

  loadCultivos(): void {
    this.cultivoService.listAll().subscribe({
      next: (data) => (this.cultivos = data || []),
      error: (err) => {
        console.error('Error cargando cultivos:', err);
        this.cultivos = [];
      }
    });
  }

  loadServicios(): void {
    this.servicioService.listAll().subscribe({
      next: (data) => (this.servicios = data || []),
      error: (err) => {
        console.error('Error cargando servicios:', err);
        this.servicios = [];
      }
    });
  }

  crearSolicitud(): void {
    if (!this.cultivoId || !this.servicioId || !this.fechaSolicitud || !this.estado) {
      this.mensaje = 'Complete todos los campos obligatorios.';
      return;
    }

    const fechaStr = (this.fechaSolicitud instanceof Date)
      ? this.fechaSolicitud.toISOString().split('T')[0]
      : String(this.fechaSolicitud);

    const payload: Partial<SolicitudServicioModel> = {
      fechaSolicitud: fechaStr,
      estado: this.estado,
      servicioId: this.servicioId,
      cultivoId: this.cultivoId
    };

    this.solicitudService.createForServicioAndCultivo(this.servicioId!, this.cultivoId!, payload).subscribe({
      next: (res: any) => {
        this.mensaje = 'Solicitud registrada correctamente.';
        // refrescar lista y limpiar formulario
        this.loadSolicitudes();
        this.cultivoId = null;
        this.servicioId = null;
        this.fechaSolicitud = null;
        this.estado = '';
      },
      error: (err: any) => {
        console.error('Error al crear solicitud:', err);
        this.mensaje = 'Error al registrar la solicitud. Intente más tarde.';
      }
    });
  }

  // LISTADO DE SOLICITUDES
  solicitudes: SolicitudServicioModel[] = [];

  loadSolicitudes(): void {
    this.solicitudService.listMy().subscribe({
      next: (data: any[]) => {
        // Normalizar la respuesta para soportar dos formatos comunes:
        // 1) { id, fechaSolicitud, estado, servicioId, cultivoId }
        // 2) { id, fechaSolicitud, estado, servicio: { id, nombre }, cultivo: { id, nombre } }
        const mapped = (data || []).map((item: any) => {
          const cultivoId = item.cultivoId ?? item.cultivo?.id ?? null;
          const servicioId = item.servicioId ?? item.servicio?.id ?? null;
          // Asegurar fecha como string (YYYY-MM-DD) si viene como Date/LocalDate
          const fecha = item.fechaSolicitud ?? item.fecha ?? (item.fechaSolicitudLocal ? item.fechaSolicitudLocal : null);
          return {
            ...item,
            cultivoId,
            servicioId,
            fechaSolicitud: fecha
          } as SolicitudServicioModel;
        });

        this.solicitudes = mapped;
        this.dataSource.data = this.solicitudes;
      },
      error: (err: any) => {
        console.error('Error cargando solicitudes:', err);
        this.solicitudes = [];
        this.dataSource.data = [];
      }
    });
  }

  getCultivoNombre(id: number | null): string {
    if (!id) return '-';
    const c = this.cultivos.find(x => x.id === id);
    return c ? c.nombre : `#${id}`;
  }

  getServicioNombre(id: number | null): string {
    if (!id) return '-';
    const s = this.servicios.find(x => x.id === id);
    return s ? s.nombre : `#${id}`;
  }

  filtrarPorCultivo(): void {
  if (!this.cultivoFiltroId) {
    this.loadSolicitudes();
    return;
  }

  this.solicitudService.listByCultivo(this.cultivoFiltroId).subscribe({
    next: (data: any[]) => {
      const mapped = (data || []).map((item: any) => {
        const cultivoId = item.cultivoId ?? item.cultivo?.id ?? null;
        const servicioId = item.servicioId ?? item.servicio?.id ?? null;
        const fecha = item.fechaSolicitud;
        return { ...item, cultivoId, servicioId, fechaSolicitud: fecha };
      });

      this.solicitudes = mapped;
      this.dataSource.data = mapped;
    },
    error: err => {
      console.error('Error listando por cultivo:', err);
      this.dataSource.data = [];
    }
  });
}

}
