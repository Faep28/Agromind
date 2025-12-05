import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { Noticia } from '../../models/noticia';
import { NoticiaService } from '../../services/noticia-service';
import { Router, NavigationEnd, ActivationEnd, ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-landing-page',
  standalone: false,
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.css',
})
export class LandingPage implements OnInit, OnDestroy {
  isMenuOpen = false;
  isMobile = false;  
  noticias: Noticia[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private noticiaService: NoticiaService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isMobile = window.innerWidth <= 800;
    
    // Cargar noticias inicialmente
    this.cargarNoticias();

    // Recargar noticias cuando se navega a la ruta raíz
    this.router.events
      .pipe(takeUntil(this.destroy$))
      .subscribe((event) => {
        if (event instanceof NavigationEnd) {
          console.log('NavigationEnd detectado, URL:', event.url, 'urlAfterRedirects:', event.urlAfterRedirects);
          if (event.url === '/' || event.urlAfterRedirects === '/') {
            console.log('Usuario navegó a landing-page, recargando noticias...');
            this.cargarNoticias();
          }
        }
        
        // También recargar cuando se activa el componente
        if (event instanceof ActivationEnd) {
          if (event.snapshot.component === LandingPage) {
            console.log('LandingPage componente activado, recargando noticias...');
            this.cargarNoticias();
          }
        }
      });

    // Suscribirse a cambios en noticias desde el NoticiaService
    this.noticiaService.obtenerActualizaciones$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        console.log('Cambios detectados en noticias, recargando...');
        this.cargarNoticias();
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  cargarNoticias() {
    this.noticiaService.getAll().subscribe({
      next: (noticias) => {
        console.log('Noticias cargadas en landing:', noticias);
        console.log('Total de noticias del servidor:', noticias.length);
        // Mostrar solo las 3 primeras noticias
        this.noticias = noticias && noticias.length > 0 ? noticias.slice(0, 3) : [];
        console.log('Noticias asignadas a landing-page:', this.noticias.length);
      },
      error: (err) => {
        // Si hay error 403 (Forbidden) o sin permisos, simplemente no mostrar noticias
        if (err.status === 403) {
          console.warn('Permisos insuficientes para cargar noticias (403)');
        } else if (err.status === 401) {
          console.warn('No autenticado (401) - endpoint debería ser público');
        } else {
          console.error('Error al cargar las noticias:', err);
        }
        this.noticias = []; // Mostrar lista vacía en lugar de error
      }
    });
  }

  // Método para obtener la imagen por índice
  getImagenNoticia(index: number): string {
    const imagenes = [
      'assets/img/noticia1.jpeg',
      'assets/img/noticia2.jpeg',
      'assets/img/noticia 3.jpeg'
    ];
    return imagenes[index] || 'assets/img/imagen1.png';
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    this.isMobile = window.innerWidth <= 800;
    if (!this.isMobile) {
      this.isMenuOpen = false;
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  scrollToSection() {
    const element = document.getElementById('services');
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' })
    }
  }
}
  