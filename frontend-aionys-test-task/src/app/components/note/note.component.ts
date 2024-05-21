import { Component, OnInit } from '@angular/core';
import { NoteService } from '../../services/services';
import { NoteDto, NoteDtoRequest } from '../../services/models';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';


@Component({
  selector: 'app-note',
  standalone: true,
  imports: [FormsModule, TranslateModule],
  templateUrl: './note.component.html',
  styleUrl: './note.component.scss'
})
export class NoteComponent implements OnInit {

  notes: NoteDto[];
  displayedColumn: string[];
  noteDtoRequest: NoteDtoRequest;
  errorMsg: string[];

  constructor(private noteService: NoteService) {
    this.notes = [];
    this.displayedColumn = ["title", "text", "action"];
    this.noteDtoRequest = { text: '', title: '' };
    this.errorMsg = [];
  }
  ngOnInit(): void {
    this.fetchNotes();
  }

  clearErrorMessages() {
    this.errorMsg = [];
  }

  saveNote() {
    this.noteService.save({
      body: this.noteDtoRequest
    })
      .subscribe({
        next: (res) => {
          this.fetchNotes();
        },
        error: (err) => {
          console.log(err)
          for (const key in err.error.errors) {
            this.errorMsg.push(err.error.errors[key]);
          }
        }
      }).unsubscribe
  }

  updateNote(note: NoteDto) {
    this.noteService.update({
      id: note.id as number,
      body: {
        title: note.title as string,
        text: note.text as string
      }
    })
      .subscribe({
        next: (res) => {
          this.fetchNotes();
        },
        error: (err) => {
          console.log(err)
          for (const key in err.error.errors) {
            this.errorMsg.push(err.error.errors[key]);
          }
        }
      }).unsubscribe
  }

  fetchNotes() {
    this.noteService.getAll()
      .subscribe({
        next: (res) => {
          console.log(res);
          this.notes = res;
        },
        error: (err) => {
          console.log(err);
        }
      }).unsubscribe
  }

  deleteRow(id: number | undefined) {
    this.noteService.deleteById({
      id: id as number
    })
      .subscribe({
        next: (res) => {
          this.fetchNotes();
        },
        error: (err) => {
          console.log(err)
          for (const key in err.error.errors) {
            this.errorMsg.push(err.error.errors[key]);
          }
        }
      }).unsubscribe
  }

  findNoteById(id: string) {
    this.noteService.getById({
      id: Number(id)
    })
      .subscribe({
        next: (res) => {
          this.notes = [];
          this.notes.push(res);
        },
        error: (err) => {
          console.log(err)
          for (const key in err.error.errors) {
            this.errorMsg.push(err.error.errors[key]);
          }
        }
      }).unsubscribe
  }

}
