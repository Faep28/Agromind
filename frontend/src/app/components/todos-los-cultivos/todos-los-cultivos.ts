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
    MatSnackBarModule
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
    this.cultivoService.listAll().subscribe({
      next: (data) => {
        console.log('Datos recibidos:', data);
        this.dsCultivos.data = data || [];
        setTimeout(() => {
          this.dsCultivos.paginator = this.paginator;
        });
      },
      error: (err) => {
        console.error('Error al obtener todos los cultivos:', err);
        this.dsCultivos.data = [];
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsCultivos.filter = filterValue.trim().toLowerCase();
  }

  editCultivo(id: number): void {
    this.router.navigate(['/registro-cultivo'], { 
      queryParams: { cultivoId: id } 
    });
  }

  deleteCultivo(id: number): void {
    if (!confirm('¿Está seguro de que desea eliminar este cultivo?')) return;
    
    this.cultivoService.delete(id).subscribe({
      next: () => {
        this.snackBar.open('Cultivo eliminado correctamente', 'Cerrar', { duration: 3000 });
        this.loadCultivos();
      },
      error: (err) => {
        console.error('Error al eliminar cultivo:', err);
        this.snackBar.open('Error al eliminar el cultivo', 'Cerrar', { duration: 3000 });
      }
    });
  }
}
