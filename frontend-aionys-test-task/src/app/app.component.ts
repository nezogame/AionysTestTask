import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NoteComponent } from "./components/note/note.component";
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [
    RouterOutlet,
    NoteComponent,
    MatSelectModule,
    TranslateModule
  ]
})
export class AppComponent {

  title = 'frontend-aionys-test-task';
  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('en');
  }

  changeLanguage(language: string) {
    this.translate.use(language)
  }
}
