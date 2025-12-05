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
  // Verificar que el formulario es válido
  if (this.formParcela.invalid) {
    this.formParcela.markAllAsTouched();
    console.log('Formulario inválido:', this.formParcela);
    return;
  }

  // Obtener el token JWT desde el localStorage
  const token = localStorage.getItem('jwtToken');
  if (!token) {
    console.error('No se encontró JWT en localStorage');
    return;  // Salir si no hay token
  }

  // Obtener el user_id desde el localStorage, que es el ID del cliente
  const clienteId = Number(localStorage.getItem('user_id'));
  if (!clienteId || clienteId === 0) {
    console.error('No se encontró clienteId/user_id en localStorage');
    return;  // Salir si no hay clienteId
  }

  // Crear el objeto nuevaParcela con los valores del formulario
  const nuevaParcela: Parcela = {
    id: 0,  // El id será generado por el backend
    nombre: this.formParcela.value.nombre,
    longitud: Number(this.formParcela.value.longitud),
    latitud: Number(this.formParcela.value.latitud),
    tamano: Number(this.formParcela.value.tamano)
  };

  // Marcar como "en proceso" para evitar múltiples envíos
  this.submitting = true;

  // Llamar al servicio para crear la parcela, pasando el token en los encabezados
  this.parcelaService.create(clienteId, nuevaParcela, token).subscribe({
    next: (created) => {
      this.submitting = false;
      // Navegar de vuelta a la lista de parcelas
      this.router.navigate(['/parcelas']);
    },
    error: (err) => {
      this.submitting = false;
      console.error('Error al crear parcela:', err);
    }
  });
}

}
