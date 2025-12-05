import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { Cultivo } from '../../models/cultivo';

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
    'estado',
    'editar',
    'eliminar'
  ];

  dsCultivos = new MatTableDataSource<Cultivo>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  parcelaId: number | null = null;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngAfterViewInit(): void {
    // Leer query param parcelaId
    this.route.queryParams.subscribe(params => {
      const id = params['parcelaId'];
      this.parcelaId = id ? Number(id) : null;
      this.loadCultivos();
    });
  }

  loadCultivos(): void {
    // Llamada al backend para obtener lista de cultivos y filtrar por parcelaId
    // Ajusta la URL si tu backend expone otra ruta
    this.http.get<Cultivo[]>('/api/cultivos/list').subscribe({
      next: (data) => {
        if (this.parcelaId) {
          this.dsCultivos.data = data.filter(c => c.parcelaId === this.parcelaId);
        } else {
          this.dsCultivos.data = data;
        }
        this.dsCultivos.paginator = this.paginator;
      },
      error: (err) => {
        console.error('Error al obtener cultivos:', err);
      }
    });
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
    // Implementa llamada DELETE si tu API lo soporta
    console.log('Eliminar cultivo', id);
  }
}
