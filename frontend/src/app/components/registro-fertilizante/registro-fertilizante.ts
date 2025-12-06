import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { FertilizanteService } from '../../services/fertilizante-service';
import { Fertilizante } from '../../models/fertilizante';

@Component({
  selector: 'app-registro-fertilizante',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatSnackBarModule
  ],
  templateUrl: './registro-fertilizante.html',
  styleUrl: './registro-fertilizante.css',
})
export class RegistroFertilizanteComponent {

  fertilizanteForm: FormGroup;
  isLoading: boolean = false;

  tiposFertilizante: string[] = [
    'Orgánico',
    'Químico',
    'Mineral',
    'Biológico',
    'Foliar',
    'NPK',
    'Otro'
  ];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<RegistroFertilizanteComponent>,
    private fertilizanteService: FertilizanteService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.fertilizanteForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      tipo: ['', [Validators.required]],
      dosisRecomendada: [null, [Validators.required, Validators.min(0)]]
    });
  }

  guardar(): void {
    if (this.fertilizanteForm.invalid) {
      this.snackBar.open('Por favor complete todos los campos requeridos', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.isLoading = true;
    const fertilizanteData: Partial<Fertilizante> = this.fertilizanteForm.value;

    this.fertilizanteService.create(fertilizanteData).subscribe({
      next: (fertilizanteCreado) => {
        this.isLoading = false;
        this.snackBar.open('Fertilizante creado exitosamente', 'Cerrar', {
          duration: 2000
        });
        // Cerrar el diálogo y pasar el fertilizante creado
        this.dialogRef.close(fertilizanteCreado);
      },
      error: (err) => {
        console.error('Error al crear fertilizante:', err);
        this.isLoading = false;
        this.snackBar.open('Error al crear el fertilizante', 'Cerrar', {
          duration: 3000
        });
      }
    });
  }

  cancelar(): void {
    this.dialogRef.close();
  }
}
