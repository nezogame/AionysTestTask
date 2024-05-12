package org.denys.hudymov.aionystesttask.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.springframework.stereotype.Service;

@Service
public interface NoteService {

    List<NoteDto> findAllNotes();

    NoteDto save(NoteDtoRequest noteDto);

    NoteDto findById(Integer id) throws EntityNotFoundException;

    NoteDto update(NoteDtoRequest noteDto,Integer id) throws EntityNotFoundException;
    void deleteById(Integer id) throws EntityNotFoundException;
}
