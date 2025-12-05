import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
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
    editMode = false;
    parcelaId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private parcelaService: ParcelaService,
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.formParcela = this.fb.group({
            id: [null],
            nombre: ['', Validators.required],
            longitud: [null, [Validators.required]],
            latitud: [null, [Validators.required]],
            tamano: [null, [Validators.required]]
        });

        // Revisar si venimos con query param ?id= para entrar en modo edición
        this.route.queryParams.subscribe(params => {
            const idParam = params['id'] ?? params['parcelaId'];
            if (idParam) {
                this.editMode = true;
                this.parcelaId = Number(idParam);
                this.loadParcela(this.parcelaId);
            }
        });
    }

    private loadParcela(id: number): void {
        this.parcelaService.getById(id).subscribe({
            next: (p: Parcela) => {
                // Rellenar el formulario con los datos existentes
                this.formParcela.patchValue({
                    id: p.id,
                    nombre: p.nombre,
                    longitud: p.longitud,
                    latitud: p.latitud,
                    tamano: p.tamano
                });
            },
            error: (err) => {
                console.error('Error al cargar parcela para editar:', err);
            }
        });
    }

    guardarParcela(): void {
        if (this.formParcela.invalid) {
            this.formParcela.markAllAsTouched();
            return;
        }

        const clienteId = Number(localStorage.getItem('user_id'));
        if (!clienteId || clienteId === 0) {
            console.error('No se encontró clienteId/user_id en localStorage');
            return;
        }

        this.submitting = true;

        const parcela: Parcela = {
            id: this.formParcela.get('id')?.value ?? 0,
            nombre: this.formParcela.get('nombre')?.value,
            longitud: Number(this.formParcela.get('longitud')?.value),
            latitud: Number(this.formParcela.get('latitud')?.value),
            tamano: Number(this.formParcela.get('tamano')?.value)
        };

        if (this.editMode && this.parcelaId) {
            // Actualizar
            this.parcelaService.update(this.parcelaId, parcela).subscribe({
                next: (data: Parcela) => {
                    this.submitting = false;
                    this.router.navigate(['/parcelas']);
                },
                error: (err) => {
                    this.submitting = false;
                    console.error('Error al actualizar parcela:', err);
                }
            });
        } else {
            // Crear nueva
            this.parcelaService.create(clienteId, parcela).subscribe({
                next: (data: Parcela) => {
                    this.submitting = false;
                    this.router.navigate(['/parcelas']);
                },
                error: (err) => {
                    this.submitting = false;
                    console.error('Error al crear parcela:', err);
                }
            });
        }
    }
    

}