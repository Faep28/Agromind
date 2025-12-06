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
        const clienteId = Number(localStorage.getItem('user_id'));
        if (!clienteId) {
            console.error('No se encontró clienteId en localStorage');
            return;
        }

        // Obtener todas las parcelas del cliente y buscar la que coincida con el ID
        this.parcelaService.getByClienteId(clienteId).subscribe({
            next: (parcelas: Parcela[]) => {
                const parcela = parcelas.find(p => p.id === id);
                if (parcela) {
                    this.formParcela.patchValue({
                        id: parcela.id,
                        nombre: parcela.nombre,
                        longitud: parcela.longitud,
                        latitud: parcela.latitud,
                        tamano: parcela.tamano
                    });
                } else {
                    console.error('Parcela no encontrada en la lista del cliente');
                }
            },
            error: (err) => {
                console.error('Error al cargar parcelas del cliente:', err);
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
            id:this.formParcela.get("id")?.value,
            nombre:this.formParcela.get("nombre")?.value,
            longitud:this.formParcela.get("longitud")?.value,
            latitud:this.formParcela.get("latitud")?.value,
            tamano:this.formParcela.get("tamano")?.value   
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