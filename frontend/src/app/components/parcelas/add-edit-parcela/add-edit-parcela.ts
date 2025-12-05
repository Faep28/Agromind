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
    ) { }

    ngOnInit(): void {
        this.formParcela = this.fb.group({
            nombre: ['', Validators.required],
            longitud: [null, [Validators.required]],
            latitud: [null, [Validators.required]],
            tamano: [null, [Validators.required]]
        });
    }

    guardarParcela(): void {

        // Hay que reevaluar, seria para la forma de editar

        // Verificar que el formulario es válido
        if (this.formParcela.invalid) {
            this.formParcela.markAllAsTouched();
            console.log('Formulario inválido:', this.formParcela);
            return;
        }

        // Obtener el user_id desde el localStorage, que es el ID del cliente
        const clienteId = Number(localStorage.getItem('user_id'));
        if (!clienteId || clienteId === 0) {
            console.error('No se encontró clienteId/user_id en localStorage');
            return;  // Salir si no hay clienteId
        }

        // Crear el objeto nuevaParcela con los valores del formulario
        //   const nuevaParcela: Parcela = {
        //     id: 0,  // El id será generado por el backend
        //     nombre: this.formParcela.value.nombre,
        //     longitud: Number(this.formParcela.value.longitud),
        //     latitud: Number(this.formParcela.value.latitud),
        //     tamano: Number(this.formParcela.value.tamano)
        //   };

        // Marcar como "en proceso" para evitar múltiples envíos
        this.submitting = true;

        /*
        
        export interface Parcela {
            id: number;
            nombre: string;
            longitud: number;
            latitud: number;
            tamano: number;
            
        }
        
        */
    }

    CrearParcela() {
        // Llamar al servicio para crear la parcela (el interceptor añadirá automáticamente el token)
        // El token ya estaria, es necesario?

        const nuevaParcela:Parcela = {
            id:this.formParcela.get("id")?.value,
            nombre:this.formParcela.get("nombre")?.value,
            longitud:this.formParcela.get("longitud")?.value,
            latitud:this.formParcela.get("latitud")?.value,
            tamano:this.formParcela.get("tamano")?.value            
        }
        const clienteId = Number(localStorage.getItem('user_id'));
        if (!clienteId || clienteId === 0) {
            console.error('No se encontró clienteId/user_id en localStorage');
            return;  // Salir si no hay clienteId
        }

        this.parcelaService.create(clienteId, nuevaParcela).subscribe({
            next: (data:Parcela) => {
                this.submitting = false;
                // Navegar de vuelta a la lista de parcelas
                this.router.navigate(['/parcelas']);
            },
            error: (err:any) => {
                this.submitting = false;
                console.error('Error al crear parcela:', err);
            }
        });
    }

}