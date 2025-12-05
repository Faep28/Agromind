import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Cultivo } from '../../models/cultivo';
import { CultivoService } from '../../services/cultivo-service';

@Component({
  selector: 'app-cultivos',
  standalone: false,
  templateUrl: './cultivos.html',
  styleUrl: './cultivos.css',
})
export class Cultivos implements OnInit {

  displayedColumns: string[] = [
    'id',
    'nombre',
    'descripcion',
    'temporada',
    'fechaSiembra',
    'fechaCosechaEsperada',
    'estado',
    'editar',
    'eliminar'
  ];

  dsCultivos = new MatTableDataSource<Cultivo>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  parcelaId: number | null = null;

  estadisticasPorParcela: Array<{ parcelaId: number; cantidad: number }> = [];

  constructor(
    private http: HttpClient, 
    private route: ActivatedRoute,
    private router: Router,
    private cultivoService: CultivoService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const id = params['parcelaId'];
      this.parcelaId = id ? Number(id) : null;
      this.loadCultivos();
    });
  }

  loadCultivos(): void {
  if (this.parcelaId) {
    this.cultivoService.getByParcela(this.parcelaId).subscribe({
      next: (data) => {
        console.log('Datos recibidos:', data);  // Agregar esta línea para inspeccionar los datos
        this.dsCultivos.data = data;
        this.dsCultivos.paginator = this.paginator;
      },
      error: (err) => {
        console.error('Error al obtener cultivos por parcela:', err);
        this.dsCultivos.data = [];
      }
    });
  }
}



  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsCultivos.filter = filterValue.trim().toLowerCase();
  }

  editCultivo(id: number): void {
    // Navegar a registro-cultivo con parcelaId y cultivoId
    this.router.navigate(['/registro-cultivo'], { 
      queryParams: { parcelaId: this.parcelaId, cultivoId: id } 
    });
  }

  deleteCultivo(id: number): void {
    if (!confirm('¿Está seguro de que desea eliminar este cultivo?')) return;
    
    this.cultivoService.delete(id).subscribe({
      next: () => {
        this.snackBar.open('Cultivo eliminado correctamente', 'OK', { duration: 3000 });
        this.loadCultivos();
      },
      error: (err) => {
        console.error('Error al eliminar cultivo:', err);
        this.snackBar.open('Error al eliminar el cultivo', 'OK', { duration: 4000 });
      }
    });
  }

  // Obtener estadísticas (delegado al servicio)
  loadEstadisticasPorParcela(): void {
    this.cultivoService.estadisticasPorParcela().subscribe({
      next: (data) => {
        // data es lista de arrays [parcelaId, cantidad]
        this.estadisticasPorParcela = data.map((item: any) => ({ parcelaId: Number(item[0]), cantidad: Number(item[1]) }));
      },
      error: (err) => console.error('Error al cargar estadisticas:', err)
    });
  }
}
