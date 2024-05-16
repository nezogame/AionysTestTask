import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NoteComponent } from "./components/note/note.component";
import { NoteService } from './services/services';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterOutlet, NoteComponent]
})
export class AppComponent {
  title = 'frontend-aionys-test-task';
}
