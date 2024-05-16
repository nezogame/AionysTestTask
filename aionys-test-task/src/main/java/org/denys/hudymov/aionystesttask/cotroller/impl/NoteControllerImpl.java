package org.denys.hudymov.aionystesttask.cotroller.impl;

import java.util.List;
import org.denys.hudymov.aionystesttask.cotroller.NoteController;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.denys.hudymov.aionystesttask.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteControllerImpl implements NoteController {
    private final NoteService noteService;

    public NoteControllerImpl(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public ResponseEntity<List<NoteDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(noteService.findAllNotes());
    }

    @Override
    public ResponseEntity<NoteDto> save(NoteDtoRequest noteDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noteService.save(noteDtoRequest));
    }

    @Override
    public ResponseEntity<NoteDto> getById(Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(noteService.findById(id));
    }

    @Override
    public ResponseEntity<NoteDto> update(NoteDtoRequest noteDtoRequest, Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(noteService.update(noteDtoRequest, id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        noteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
