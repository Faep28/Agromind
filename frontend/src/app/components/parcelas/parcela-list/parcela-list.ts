import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { Parcela } from '../../../models/parcela';
import { ParcelaService } from '../../../services/parcela-service';

@Component({
  selector: 'app-parcela-list',
  standalone: false,
  templateUrl: './parcela-list.html',
  styleUrl: './parcela-list.css',
})
export class ParcelaList implements AfterViewInit {

  displayedColumns: string[] = [
    'id',
    'nombre',
    'longitud',
    'latitud',
    'tamano',
    'clienteId',
    'actions'
  ];

  dsParcelas = new MatTableDataSource<Parcela>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private parcelaService: ParcelaService) {}

  ngAfterViewInit(): void {
    this.listarParcelas();
  }

  listarParcelas(): void {

    const clienteId = Number(localStorage.getItem('clienteId'));

    if (!clienteId) {
      console.error("No existe clienteId en localStorage");
      return;
    }

    this.parcelaService.getByClienteId(clienteId).subscribe({
      next: (data) => {
        this.dsParcelas.data = data;
        this.dsParcelas.paginator = this.paginator;
      },
      error: (err) => { 
        console.error('Error al obtener parcelas por cliente:', err);
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsParcelas.filter = filterValue.trim().toLowerCase();
  }

  deleteParcela(id: number): void {
    if (!confirm('¿Eliminar parcela?')) return;

    this.parcelaService.delete(id).subscribe({
      next: () => this.listarParcelas(),
      error: (err) => console.error('Error al eliminar parcela:', err)
    });
  }
}
