package org.denys.hudymov.aionystesttask.cotroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Note")
@RequestMapping("/notes")
public interface NoteController {
    @GetMapping()
    ResponseEntity<List<NoteDto>> getAll();

    @PostMapping
    ResponseEntity<NoteDto> save(@Valid @RequestBody NoteDtoRequest noteDtoRequest);

    @GetMapping("/{id}")
    ResponseEntity<NoteDto> getById(@PathVariable Integer id);


    @PutMapping("/{id}")
    ResponseEntity<NoteDto> update(@Valid @RequestBody NoteDtoRequest updateNoteDto, @PathVariable Integer id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);
}
