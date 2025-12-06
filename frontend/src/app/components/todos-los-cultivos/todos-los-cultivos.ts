import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { Cultivo } from '../../models/cultivo';
import { CultivoService } from '../../services/cultivo-service';

@Component({
  selector: 'app-todos-los-cultivos',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatCardModule,
    MatTooltipModule
  ],
  templateUrl: './todos-los-cultivos.html',
  styleUrl: './todos-los-cultivos.css',
})
export class TodosLosCultivosComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = [
    'id',
    'nombre',
    'descripcion',
    'temporada',
    'fechaSiembra',
    'fechaCosechaEsperada',
    'estado',
    'fertilizantes',
    'editar',
    'eliminar'
  ];

  dsCultivos = new MatTableDataSource<Cultivo>();

  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  constructor(
    private http: HttpClient,
    private router: Router,
    private cultivoService: CultivoService,
    private snackBar: MatSnackBar
  ) {}
  
  ngOnInit(): void {
    this.loadCultivos();
  }

  ngAfterViewInit(): void {
    this.dsCultivos.paginator = this.paginator;
  }

  loadCultivos(): void {
    // Obtener el ID del usuario logueado
    const clienteId = Number(localStorage.getItem('user_id'));
    
    if (!clienteId) {
      console.error('No se encontró user_id en localStorage');
      this.snackBar.open('Error: Usuario no autenticado', 'Cerrar', { duration: 3000 });
      return;
    }

    // Obtener solo los cultivos del usuario logueado
    this.cultivoService.getCultivosByCliente(clienteId).subscribe({
      next: (data) => {
        console.log('Cultivos del usuario:', data);
        this.dsCultivos.data = data || [];
        setTimeout(() => {
          this.dsCultivos.paginator = this.paginator;
        });
      },
      error: (err) => {
        console.error('Error al obtener cultivos del usuario:', err);
        this.snackBar.open('Error al cargar cultivos', 'Cerrar', { duration: 3000 });
        this.dsCultivos.data = [];
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsCultivos.filter = filterValue.trim().toLowerCase();
  }

  editCultivo(id: number): void {
    // Navegar solo con cultivoId, el componente cargará los datos del backend
    this.router.navigate(['/registro-cultivo'], { 
      queryParams: { cultivoId: id } 
    });
  }

  deleteCultivo(id: number): void {
    if (!confirm('¿Está seguro de que desea eliminar este cultivo?')) return;
    
    // Intentar primero con DELETE
    this.cultivoService.delete(id).subscribe({
      next: () => {
        this.snackBar.open('Cultivo eliminado correctamente', 'Cerrar', { duration: 3000 });
        this.loadCultivos();
      },
      error: (err) => {
        console.error('Error al eliminar cultivo:', err);
        
        // Si falla por permisos (403), intentar soft delete
        if (err.status === 403) {
          console.log('Intentando soft delete...');
          this.cultivoService.softDelete(id).subscribe({
            next: () => {
              this.snackBar.open('Cultivo marcado como inactivo', 'Cerrar', { duration: 3000 });
              this.loadCultivos();
            },
            error: (softErr) => {
              console.error('Error en soft delete:', softErr);
              this.snackBar.open('Error: No se pudo eliminar el cultivo', 'Cerrar', { duration: 5000 });
            }
          });
        } else {
          const errorMsg = err.error?.message || err.message || 'Error al eliminar el cultivo';
          this.snackBar.open(`Error: ${errorMsg}`, 'Cerrar', { duration: 5000 });
        }
      }
    });
  }

  verFertilizantes(cultivoId: number): void {
    this.router.navigate(['/fertilizantes-cultivo', cultivoId]);
  }
}
