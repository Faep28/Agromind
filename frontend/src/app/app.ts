import { Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
   
  protected readonly title = signal('frontend');

  // Aqui colocar los nombres de rutas donde no mostrar el header
  private readonly noHeaderRoutes: string[] = [
    '/', '/login', '/register'
  ];

  isNoHeaderPage: boolean = false;

  private router = inject(Router);

  constructor() {
    this.isNoHeaderPage = this.noHeaderRoutes.includes(this.router.url);

    this.router.events.subscribe(() => {
      this.isNoHeaderPage = this.noHeaderRoutes.includes(this.router.url);
    });
  }
}
