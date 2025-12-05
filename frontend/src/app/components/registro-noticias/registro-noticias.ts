import { ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Noticia } from '../../models/noticia';
import { NoticiaService } from '../../services/noticia-service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registro-noticias',
  standalone: false,
  templateUrl: './registro-noticias.html',
  styleUrl: './registro-noticias.css',
})
export class RegistroNoticias {
  noticiaForm!: FormGroup;
  noticias: Noticia[] = []; 

  constructor(private formBuilder: FormBuilder, private noticiaService: NoticiaService, private router: Router, private snack: MatSnackBar,  private cdr: ChangeDetectorRef) {
    this.noticiaForm = this.formBuilder.group({
      titulo: ['', [Validators.required]],
      contenido: ['', [Validators.required, Validators.maxLength(500)]],  
      fechapublicacion: ['', [Validators.required]],
      imagen: ['', [Validators.required]]
    });
  }


  ngOnInit() {
    // Cargar las noticias existentes al cargar el componente
    this.loadNoticias();
  }

  // Método para enviar los datos al backend

  onSubmit() {
    if (this.noticiaForm.valid) {
      const formData = new FormData();
      formData.append('titulo', this.noticiaForm.get('titulo')?.value);
      formData.append('contenido', this.noticiaForm.get('contenido')?.value);

      // Obtener la fecha del formulario y formatearla en 'yyyy-MM-dd'
      const fecha = this.noticiaForm.get('fechapublicacion')?.value;
      const fechaISO = fecha ? fecha.toISOString().split('T')[0] : '';
    
      formData.append('fechapublicacion', fechaISO);  // Enviar la fecha en formato 'yyyy-MM-dd'
      
      const imagen = this.noticiaForm.get('imagen')?.value;
      if (imagen) {
        formData.append('imagen', imagen);
      }

      this.noticiaService.new(formData).subscribe({
        next: (data) => {
          console.log('Noticia creada correctamente:', data);

          // Mostrar mensaje de éxito con MatSnackBar
          this.snack.open('Noticia registrada con éxito!', 'Cerrar', {
            duration: 3000, // Duración del mensaje (en milisegundos)
          });

          // Limpiar el formulario completamente
          this.noticiaForm.reset();
          
          // Limpiar el input file manualmente (reset no limpia los input file)
          const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
          if (fileInput) {
            fileInput.value = '';
          }
          
          this.loadNoticias();       // Recargar lista de noticias
          this.noticiaService.notificarCambios(); // Notificar a otros componentes
        },
        error: (err) => {
          console.error('Error al crear la noticia:', err);
          this.snack.open('Error al crear la noticia. Intenta de nuevo.', 'Cerrar', {
            duration: 3000,
          });
        }
      });
    }
  }



  // Método para cargar todas las noticias
  loadNoticias() {
  this.noticiaService.getAll().subscribe({
    next: (noticias) => {
      console.log('Noticias cargadas:', noticias);  // Verifica que las noticias se están recibiendo correctamente
      this.noticias = noticias;

      // Forzamos una detección de cambios después de actualizar los datos
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error al cargar las noticias:', err);
    }
  });
}
  // Método para eliminar una noticia
  eliminarNoticia(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta noticia?')) {
      this.noticiaService.delete(id).subscribe({
        next: () => {
          this.noticias = this.noticias.filter((noticia) => noticia.id !== id); // Actualiza la lista localmente
          alert('Noticia eliminada correctamente');
          this.noticiaService.notificarCambios(); // Notificar a otros componentes
        },
        error: (err) => {
          console.error('Error al eliminar la noticia', err);
        }
      });
    }
  }

  // Método para manejar el cambio de archivo en el input
  onFileChange(event: any) {
    const file = event.target.files[0]; // Obtén el primer archivo
    if (file) {
      // No usar setValue con input file, solo guardar el archivo
      // El formulario control 'imagen' almacenará la referencia al archivo
      this.noticiaForm.get('imagen')?.patchValue(file, { emitEvent: false });
      this.noticiaForm.get('imagen')?.markAsTouched();
    }
  }



}
