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
  cultivoId: number | null = null;
  isEditMode: boolean = false;

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
    
    // Leer parcelaId y cultivoId de query params
    this.route.queryParams.subscribe(params => {
      const parcelaId = params['parcelaId'];
      const cultivoId = params['cultivoId'];
      
      if (parcelaId) {
        this.parcelaId = Number(parcelaId);
        this.cultivoForm.patchValue({ parcelaId: this.parcelaId });
        this.cultivoForm.get('parcelaId')?.disable();
      }
      
      if (cultivoId) {
        this.cultivoId = Number(cultivoId);
        this.isEditMode = true;
        this.loadCultivoData(this.cultivoId);
      }
    });
  }

  loadCultivoData(id: number): void {
    // Obtener todos los cultivos de la parcela y filtrar el que necesitamos
    if (this.parcelaId) {
      this.cultivoService.getByParcela(this.parcelaId).subscribe({
        next: (cultivos: Cultivo[]) => {
          const cultivo = cultivos.find(c => c.id === id);
          if (cultivo) {
            this.cultivoForm.patchValue({
              nombre: cultivo.nombre,
              descripcion: cultivo.descripcion,
              temporada: cultivo.temporada,
              fechaSiembra: cultivo.fechaSiembra,
              fechaCosechaEsperada: cultivo.fechaCosechaEsperada,
              estado: cultivo.estado,
              parcelaId: cultivo.parcelaId
            });
          }
        },
        error: (err: any) => {
          console.error('Error al cargar cultivo:', err);
          this.snack.open('Error al cargar los datos del cultivo', 'OK', { duration: 4000 });
        }
      });
    }
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

    const parcelaId = formValues.parcelaId;

    if (this.isEditMode && this.cultivoId) {
      // Modo edición: actualizar cultivo existente
      this.cultivoService.update(this.cultivoId, cultivo).subscribe({
        next: (res) => {
          this.snack.open('Cultivo actualizado correctamente', 'OK', { duration: 4000 });
          this.router.navigate(['/cultivos'], { queryParams: { parcelaId } });
        },
        error: (err) => {
          console.error('Error al actualizar cultivo', err);
          this.snack.open('Error al actualizar cultivo', 'OK', { duration: 5000 });
        }
      });
    } else {
      // Modo creación: crear nuevo cultivo
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
}
