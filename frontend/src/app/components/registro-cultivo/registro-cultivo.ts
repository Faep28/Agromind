import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
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
  parcelaId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snack: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    private cultivoService: CultivoService
  ) {}

  ngOnInit() {
    this.CargarFormulario();
    
    // Leer parcelaId de query params y precargarlo
    this.route.queryParams.subscribe(params => {
      const parcelaId = params['parcelaId'];
      if (parcelaId) {
        this.parcelaId = Number(parcelaId);
        this.cultivoForm.patchValue({ parcelaId: this.parcelaId });
        // Deshabilitar el campo para que no se pueda modificar
        this.cultivoForm.get('parcelaId')?.disable();
      }
    });
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

    // getRawValue() obtiene valores incluso si están deshabilitados
    const formValues = this.cultivoForm.getRawValue();
    
    const cultivo: Partial<Cultivo> = {
      nombre: formValues.nombre,
      descripcion: formValues.descripcion,
      temporada: formValues.temporada,
      fechaSiembra: formValues.fechaSiembra,
      fechaCosechaEsperada: formValues.fechaCosechaEsperada,
      estado: formValues.estado,
      parcelaId: formValues.parcelaId,
    };

    // Usar servicio: el backend espera POST /api/cultivos/insert/{parcelaId}
    const parcelaId = formValues.parcelaId;
    this.cultivoService.createForParcela(parcelaId, cultivo).subscribe({
      next: (res) => {
        this.snack.open('Cultivo registrado correctamente', 'OK', { duration: 4000 });
        this.router.navigate(['/cultivos'], { queryParams: { parcelaId } });
      },
      error: (err) => {
        console.error('Error al crear cultivo', err);
        this.snack.open('Error al registrar cultivo', 'OK', { duration: 5000 });
      }
    });
  }
}
