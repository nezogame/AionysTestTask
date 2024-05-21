package org.denys.hudymov.aionystesttask.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.denys.hudymov.aionystesttask.entity.Note;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.denys.hudymov.aionystesttask.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @InjectMocks
    NoteServiceImpl service;

    @Mock
    NoteRepository repository;

    private Note note;
    private NoteDto noteDto;

    @BeforeEach
    public void setup() {
        note = Note.builder()
                .id(1)
                .title("Test Note")
                .text("Text inside test note")
                .build();

        noteDto = NoteDto.builder()
                .id(1)
                .title("Test Note")
                .text("Text inside test note")
                .build();
    }


    @Test
    @DisplayName("Test for findAllNotes method")
    public void givenNoteList_whenFindAllNotes_thenReturnNotesList() {
        // given
        var note2 = Note.builder()
                .id(1)
                .title("Test Note")
                .text("Text inside test note")
                .build();

        given(repository.findAll()).willReturn(List.of(note, note2));

        // when
        List<NoteDto> noteDtoList = service.findAllNotes();

        // then
        assertThat(noteDtoList).isNotNull();
        assertThat(noteDtoList.size()).isEqualTo(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test for findAllNotes method when no notes is present")
    void givenEmptyList_whenFindAllNotes_thenReturnEmptyNotesList() {
        // given
        given(repository.findAll()).willReturn(List.of());

        // when
        List<NoteDto> noteDtoList = service.findAllNotes();

        // then
        assertThat(noteDtoList).isNotNull();
        assertThat(noteDtoList).isEmpty();
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test for save method")
    void givenNoteObject_whenSave_thenReturnNoteObject() {
        // given
        Note saveNote = Note.builder()
                .title("Test Note")
                .text("Text inside test note")
                .build();
        NoteDtoRequest noteDtoRequest = NoteDtoRequest.builder()
                .title("Test Note")
                .text("Text inside test note")
                .build();

        given(repository.save(saveNote)).willReturn(note);

        // when
        NoteDto result = service.save(noteDtoRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(noteDto);
        verify(repository, times(1)).save(saveNote);
    }

    @Test
    @DisplayName("Test for findById method")
    void givenNoteId_whenFindById_thenReturnNoteObject() {
        // given
        Integer id = 1;
        given(repository.findById(id)).willReturn(Optional.of(note));

        // when
        var note = service.findById(id);

        // then
        assertThat(note).isNotNull();
        assertThat(note).isEqualTo(noteDto);
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test for findById method when record doesn't exist")
    void givenNoteId_whenFindById_thenThrowsException() {
        // given
        Integer id = 1;
        given(repository.findById(id)).willReturn(Optional.empty());

        // when
        var exception = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        // then
        assertThat(exception.getMessage()).isEqualTo("Note with id: 1 doesn't exist");
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test for update method")
    void givenUpdatedNoteObjectAndId_whenUpdate_thenReturnUpdatedNoteObject() {
        // given
        Note updateNote = Note.builder()
                .id(1)
                .title("New Test Note")
                .text("Text inside new test note")
                .build();
        NoteDtoRequest noteDtoRequest = NoteDtoRequest.builder()
                .title("New Test Note")
                .text("Text inside new test note")
                .build();
        NoteDto updatednoteDto = NoteDto.builder()
                .id(1)
                .title("New Test Note")
                .text("Text inside new test note")
                .build();

        Integer id = 1;
        given(repository.findById(id)).willReturn(Optional.of(note));
        given(repository.save(updateNote)).willReturn(updateNote);

        // when
        NoteDto result = service.update(noteDtoRequest, id);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(updatednoteDto);
        verify(repository, times(1)).save(updateNote);
    }

    @Test
    @DisplayName("Test for update method when record doesn't exist")
    void givenUpdatedNoteObjectAndId_whenUpdate_thenThrowException() {
        // given
        NoteDtoRequest noteDtoRequest = NoteDtoRequest.builder()
                .title("New Test Note")
                .text("Text inside new test note")
                .build();
        Integer id = 1;
        given(repository.findById(id)).willReturn(Optional.empty());

        // when
        var exception = assertThrows(EntityNotFoundException.class, () -> service.update(noteDtoRequest, id));

        // then
        assertThat(exception.getMessage()).isEqualTo("Note with id: 1 doesn't exist");
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test for deleteById method")
    void givenId_whenDeleteById_thenDeleteNoteObject() {
        // given
        Integer id = 1;
        given(repository.existsById(id)).willReturn(true);
        willDoNothing().given(repository).deleteById(id);

        // when
        service.deleteById(id);

        // then
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Test for deleteById method when record doesn't exist")
    void givenId_whenDeleteById_thenThrowException() {
        // given
        Integer id = 1;
        given(repository.existsById(id)).willReturn(false);

        // when
        var exception = assertThrows(EntityNotFoundException.class, () ->  service.deleteById(id));

        // then
        assertThat(exception.getMessage()).isEqualTo("Note with id: 1 doesn't exist");
        verify(repository, times(1)).existsById(id);
    }
}