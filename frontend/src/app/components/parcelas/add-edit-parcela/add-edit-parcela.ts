import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ParcelaService } from '../../../services/parcela-service';
import { Parcela } from '../../../models/parcela';

@Component({
  selector: 'app-add-edit-parcela',
  standalone: false,
  templateUrl: './add-edit-parcela.html',
  styleUrl: './add-edit-parcela.css',
})
export class AddEditParcela implements OnInit {

  formParcela!: FormGroup;
  submitting = false;

  constructor(
    private fb: FormBuilder,
    private parcelaService: ParcelaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formParcela = this.fb.group({
      nombre: ['', Validators.required],
      longitud: [null, [Validators.required]],
      latitud: [null, [Validators.required]],
      tamano: [null, [Validators.required]]
    });
  }

  guardarParcela(): void {
    if (this.formParcela.invalid) {
      this.formParcela.markAllAsTouched();
      console.log('Formulario inválido:', this.formParcela);
      return;
    }

    // El id del usuario/cliente se guarda en el token (localStorage)
    const clienteId = Number(localStorage.getItem('user_id') ?? localStorage.getItem('clienteId'));

    if (!clienteId || clienteId === 0) {
      console.error('No se encontró clienteId/user_id en localStorage');
      return;
    }

    const nuevaParcela: Parcela = {
      // tu modelo Parcela no tiene clienteId en el JSON devuelto; backend asocia cliente en el POST
      id: 0, // backend generará el id; poner 0 o null es opcional pero evita TS errors
      nombre: this.formParcela.value.nombre,
      longitud: Number(this.formParcela.value.longitud),
      latitud: Number(this.formParcela.value.latitud),
      tamano: Number(this.formParcela.value.tamano)
    };

    this.submitting = true;
    this.parcelaService.create(clienteId, nuevaParcela).subscribe({
      next: (created) => {
        this.submitting = false;
        // navegar de vuelta a la lista de parcelas
        this.router.navigate(['/parcelas']);
      },
      error: (err) => {
        this.submitting = false;
        console.error('Error al crear parcela:', err);
      }
    });
  }
}
