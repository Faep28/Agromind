import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { Cultivo } from '../../models/cultivo';
import { CultivoService } from '../../services/cultivo-service';

@Component({
  selector: 'app-cultivos',
  standalone: false,
  templateUrl: './cultivos.html',
  styleUrl: './cultivos.css',
})
export class Cultivos implements AfterViewInit {

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

  constructor(private http: HttpClient, private route: ActivatedRoute, private cultivoService: CultivoService) {}

  ngAfterViewInit(): void {
    // Leer query param parcelaId
    this.route.queryParams.subscribe(params => {
      const id = params['parcelaId'];
      this.parcelaId = id ? Number(id) : null;
      this.loadCultivos();
    });
  }

  loadCultivos(): void {
    // Si hay parcelaId, usar endpoint específico /parcela/{id}; si no, listar todos
    if (this.parcelaId) {
      this.cultivoService.getByParcela(this.parcelaId).subscribe({
        next: (data) => {
          this.dsCultivos.data = data;
          this.dsCultivos.paginator = this.paginator;
        },
        error: (err) => {
          // Si no hay contenido (204) o falla, dejar arreglo vacío y loguear
          console.error('Error al obtener cultivos por parcela:', err);
          this.dsCultivos.data = [];
        }
      });
    } else {
      this.cultivoService.listAll().subscribe({
        next: (data) => {
          this.dsCultivos.data = data;
          this.dsCultivos.paginator = this.paginator;
        },
        error: (err) => {
          console.error('Error al obtener cultivos:', err);
        }
      });
    }
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsCultivos.filter = filterValue.trim().toLowerCase();
  }

  // métodos placeholder para editar/eliminar
  editCultivo(id: number): void {
    console.log('Editar cultivo', id);
  }

  deleteCultivo(id: number): void {
    if (!confirm('¿Eliminar cultivo?')) return;
    // Llamada al servicio para eliminar
    this.cultivoService.delete(id).subscribe({
      next: () => this.loadCultivos(),
      error: (err) => console.error('Error al eliminar cultivo:', err)
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
