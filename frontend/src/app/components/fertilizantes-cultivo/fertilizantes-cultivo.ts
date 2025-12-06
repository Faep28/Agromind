import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { FertilizanteService } from '../../services/fertilizante-service';
import { CultivoFertilizante } from '../../models/cultivo-fertilizante';
import { RegistroFertilizanteComponent } from '../registro-fertilizante/registro-fertilizante';
import { AsignarFertilizanteDialogComponent } from '../asignar-fertilizante-dialog/asignar-fertilizante-dialog';
import { Fertilizante } from '../../models/fertilizante';

@Component({
  selector: 'app-fertilizantes-cultivo',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDialogModule
  ],
  templateUrl: './fertilizantes-cultivo.html',
  styleUrl: './fertilizantes-cultivo.css',
})
export class FertilizantesCultivoComponent implements OnInit {

  cultivoId: number = 0;
  cultivoNombre: string = '';
  isLoading: boolean = true;

  displayedColumns: string[] = [
    'nombre',
    'tipo',
    'dosisRecomendada',
    'fechaAplicacion',
    'cantidad',
    'acciones'
  ];

  dsFertilizantes = new MatTableDataSource<CultivoFertilizante>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fertilizanteService: FertilizanteService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('cultivoId');
      if (id) {
        this.cultivoId = +id;
        this.loadFertilizantes();
      } else {
        this.showMessage('Error: No se proporcionó ID del cultivo');
        this.router.navigate(['/todos-los-cultivos']);
      }
    });
  }

  loadFertilizantes(): void {
    this.isLoading = true;
    this.fertilizanteService.getFertilizantesByCultivo(this.cultivoId).subscribe({
      next: (data) => {
        this.dsFertilizantes.data = data;
        if (data.length > 0) {
          this.cultivoNombre = data[0].cultivo.nombre;
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error al cargar fertilizantes:', err);
        this.showMessage('Error al cargar los fertilizantes del cultivo');
        this.isLoading = false;
      }
    });
  }

  volver(): void {
    this.router.navigate(['/todos-los-cultivos']);
  }

  showMessage(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }

  registrarNuevoFertilizante(): void {
    const dialogRef = this.dialog.open(RegistroFertilizanteComponent, {
      width: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((fertilizanteCreado: Fertilizante) => {
      if (fertilizanteCreado) {
        // Fertilizante creado, ahora abrir el segundo modal para asignar
        this.abrirDialogAsignar(fertilizanteCreado.id, fertilizanteCreado.nombre);
      }
    });
  }

  abrirDialogAsignar(fertilizanteId: number, fertilizanteNombre: string): void {
    const dialogRef = this.dialog.open(AsignarFertilizanteDialogComponent, {
      width: '500px',
      disableClose: true,
      data: {
        cultivoId: this.cultivoId,
        cultivoNombre: this.cultivoNombre,
        fertilizanteId: fertilizanteId,
        fertilizanteNombre: fertilizanteNombre
      }
    });

    dialogRef.afterClosed().subscribe((resultado) => {
      if (resultado) {
        // Recargar la lista de fertilizantes
        this.loadFertilizantes();
      }
    });
  }

  editarFertilizante(relacion: CultivoFertilizante): void {
    const dialogRef = this.dialog.open(RegistroFertilizanteComponent, {
      width: '500px',
      disableClose: true,
      data: {
        fertilizante: relacion.fertilizante
      }
    });

    dialogRef.afterClosed().subscribe((resultado) => {
      if (resultado) {
        // Recargar la lista de fertilizantes
        this.loadFertilizantes();
      }
    });
  }

  editarRelacion(relacion: CultivoFertilizante): void {
    const dialogRef = this.dialog.open(AsignarFertilizanteDialogComponent, {
      width: '500px',
      disableClose: true,
      data: {
        cultivoId: this.cultivoId,
        cultivoNombre: this.cultivoNombre,
        fertilizanteId: relacion.fertilizante.id,
        fertilizanteNombre: relacion.fertilizante.nombre,
        relacionId: relacion.id,
        fechaAplicacion: relacion.fechaAplicacion,
        cantidad: relacion.cantidad,
        isEditMode: true
      }
    });

    dialogRef.afterClosed().subscribe((resultado) => {
      if (resultado) {
        // Recargar la lista de fertilizantes
        this.loadFertilizantes();
      }
    });
  }

  eliminarRelacion(relacion: CultivoFertilizante): void {
    if (!confirm(`¿Está seguro de eliminar la relación con el fertilizante "${relacion.fertilizante.nombre}"?`)) {
      return;
    }

    this.fertilizanteService.deleteRelacion(relacion.id).subscribe({
      next: () => {
        this.showMessage('Relación eliminada correctamente');
        this.loadFertilizantes();
      },
      error: (err) => {
        console.error('Error al eliminar relación:', err);
        this.showMessage('Error al eliminar la relación');
      }
    });
  }
}
