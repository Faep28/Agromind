import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { FertilizanteService } from '../../services/fertilizante-service';

export interface AsignarFertilizanteData {
  cultivoId: number;
  cultivoNombre: string;
  fertilizanteId: number;
  fertilizanteNombre: string;
  // Datos opcionales para modo edición
  relacionId?: number;
  fechaAplicacion?: string;
  cantidad?: number;
  isEditMode?: boolean;
}

@Component({
  selector: 'app-asignar-fertilizante-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule
  ],
  templateUrl: './asignar-fertilizante-dialog.html',
  styleUrl: './asignar-fertilizante-dialog.css',
})
export class AsignarFertilizanteDialogComponent implements OnInit {

  asignarForm: FormGroup;
  isLoading: boolean = false;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AsignarFertilizanteDialogComponent>,
    private fertilizanteService: FertilizanteService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: AsignarFertilizanteData
  ) {
    this.asignarForm = this.fb.group({
      fechaAplicacion: [new Date(), [Validators.required]],
      cantidad: [null, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.isEditMode = this.data.isEditMode || false;
    
    // Si está en modo edición, pre-llenar el formulario
    if (this.isEditMode && this.data.fechaAplicacion && this.data.cantidad) {
      this.asignarForm.patchValue({
        fechaAplicacion: new Date(this.data.fechaAplicacion),
        cantidad: this.data.cantidad
      });
    }
  }

  guardar(): void {
    if (this.asignarForm.invalid) {
      this.snackBar.open('Por favor complete todos los campos requeridos', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.isLoading = true;
    
    // Formatear fecha a string (yyyy-MM-dd)
    const fechaAplicacion = this.formatDate(this.asignarForm.get('fechaAplicacion')?.value);
    const cantidad = this.asignarForm.get('cantidad')?.value;

    if (this.isEditMode && this.data.relacionId) {
      // Modo edición: actualizar relación existente
      const requestData = {
        id: this.data.relacionId,
        fechaAplicacion,
        cantidad,
        cultivo: {
          id: this.data.cultivoId
        },
        fertilizante: {
          id: this.data.fertilizanteId
        }
      };
      
      this.fertilizanteService.updateRelacion(
        this.data.relacionId,
        requestData as any
      ).subscribe({
        next: (relacion) => {
          this.isLoading = false;
          this.snackBar.open('Relación actualizada exitosamente', 'Cerrar', {
            duration: 2000
          });
          this.dialogRef.close(true);
        },
        error: (err) => {
          console.error('Error al actualizar relación:', err);
          this.isLoading = false;
          const mensaje = err.error?.message || 'Error al actualizar la relación';
          this.snackBar.open(mensaje, 'Cerrar', {
            duration: 4000
          });
        }
      });
    } else {
      // Modo creación: asignar nuevo fertilizante
      const requestData = {
        fechaAplicacion,
        cantidad
      };
      
      this.fertilizanteService.asignarFertilizanteACultivo(
        this.data.cultivoId,
        this.data.fertilizanteId,
        requestData
      ).subscribe({
        next: (relacion) => {
          this.isLoading = false;
          this.snackBar.open('Fertilizante asignado exitosamente al cultivo', 'Cerrar', {
            duration: 2000
          });
          this.dialogRef.close(true);
        },
        error: (err) => {
          console.error('Error al asignar fertilizante:', err);
          this.isLoading = false;
          const mensaje = err.error?.message || 'Error al asignar el fertilizante al cultivo';
          this.snackBar.open(mensaje, 'Cerrar', {
            duration: 4000
          });
        }
      });
    }
  }

  cancelar(): void {
    this.dialogRef.close();
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
