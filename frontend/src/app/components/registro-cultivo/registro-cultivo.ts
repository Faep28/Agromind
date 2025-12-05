import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CultivoService } from '../../services/cultivo-service';
import { Cultivo } from '../../models/cultivo';

@Component({
  selector: 'app-registro-cultivo',
  standalone: false,
  templateUrl: './registro-cultivo.html',
  styleUrl: './registro-cultivo.css',
})
export class RegistroCultivo {
  cultivoForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snack: MatSnackBar,
    private router: Router,
    private cultivoService: CultivoService
  ) {}

  ngOnInit() {
    this.CargarFormulario();
  }

  CargarFormulario() {
    this.cultivoForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ['', []],
      temporada: ['', [Validators.required]],
      fechaSiembra: ['', [Validators.required]],
      fechaCosechaEsperada: ['', [Validators.required]],
      estado: ['', [Validators.required]],
      parcelaId: [null, [Validators.required]]
    });
  }

  onSubmit() {
    if (this.cultivoForm.invalid) {
      this.snack.open('Por favor complete el formulario correctamente', 'OK', { duration: 4000 });
      return;
    }

    const cultivo: Partial<Cultivo> = {
      nombre: this.cultivoForm.value.nombre,
      descripcion: this.cultivoForm.value.descripcion,
      temporada: this.cultivoForm.value.temporada,
      fechaSiembra: this.cultivoForm.value.fechaSiembra,
      fechaCosechaEsperada: this.cultivoForm.value.fechaCosechaEsperada,
      estado: this.cultivoForm.value.estado,
      parcelaId: this.cultivoForm.value.parcelaId,
    };

    // Usar servicio: el backend espera POST /api/cultivos/insert/{parcelaId}
    const parcelaId = this.cultivoForm.value.parcelaId;
    this.cultivoService.createForParcela(parcelaId, cultivo).subscribe({
      next: (res) => {
        this.snack.open('Cultivo registrado correctamente', 'OK', { duration: 4000 });
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Error al crear cultivo', err);
        this.snack.open('Error al registrar cultivo', 'OK', { duration: 5000 });
      }
    });
  }
}
