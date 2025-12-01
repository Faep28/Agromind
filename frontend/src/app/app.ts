import { Component, inject, signal } from '@angular/core';
import { UserService } from './services/user-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
   
  protected readonly title = signal('frontend');

}
