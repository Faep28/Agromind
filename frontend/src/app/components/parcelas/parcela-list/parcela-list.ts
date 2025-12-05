import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

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
    'seleccionar',
    'id',
    'nombre',
    'longitud',
    'latitud',
    'tamano',
    'editar',
    'eliminar'  
  ];

  dsParcelas = new MatTableDataSource<Parcela>();

  // Id de parcela seleccionada por el usuario
  selectedParcelaId: number | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private parcelaService: ParcelaService, private router: Router, private route: ActivatedRoute) {}

  ngAfterViewInit(): void {
    // Si venimos con query param parcelaId (por ejemplo desde Cultivos), preseleccionarla
    this.route.queryParams.subscribe(params => {
      const pid = params['parcelaId'];
      if (pid) {
        this.selectedParcelaId = Number(pid);
      }
    });

    this.listarParcelas();
  }

  listarParcelas(): void {

    // Tu tokenDTO guarda el id del usuario con la clave: user_id
    const clienteId = Number(localStorage.getItem('user_id'));

    console.log("Cliente ID obtenido del localStorage:", clienteId);

    if (!clienteId) {
      console.error("No existe user_id en localStorage");
      return;
    }

    this.parcelaService.getByClienteId(clienteId).subscribe({
      next: (data) => {
        console.log("Parcelas obtenidas desde backend:", data);

        this.dsParcelas.data = data;

        // Conectar paginator después de tener datos
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

  // Seleccionar una parcela (marca el id)
  selectParcela(parcela: Parcela): void {
    // Toggle: si ya está seleccionada, deselecciona; si no, selecciona
    if (this.selectedParcelaId === parcela.id) {
      this.selectedParcelaId = null;
    } else {
      this.selectedParcelaId = parcela.id;
    }
  }

  // Navegar a la vista de cultivos pasando la parcela seleccionada como query param
  goToCultivos(): void {
    if (!this.selectedParcelaId) return;
    this.router.navigate(['/cultivos'], { queryParams: { parcelaId: this.selectedParcelaId } });
  }

  deleteParcela(id: number): void {
    if (!confirm('¿Eliminar parcela?')) return;

    this.parcelaService.delete(id).subscribe({
      next: () => this.listarParcelas(),
      error: (err) => console.error('Error al eliminar parcela:', err)
    });
  }
}
