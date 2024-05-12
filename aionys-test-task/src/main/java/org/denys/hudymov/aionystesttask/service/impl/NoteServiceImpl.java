package org.denys.hudymov.aionystesttask.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.denys.hudymov.aionystesttask.mapper.NoteMapper;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.denys.hudymov.aionystesttask.repository.NoteRepository;
import org.denys.hudymov.aionystesttask.service.NoteService;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<NoteDto> findAllNotes() {
        var stations = noteRepository.findAll();

        return stations.stream()
                .map(NoteMapper.INSTANCE::noteToNoteDto)
                .toList();
    }

    @Override
    public NoteDto save(NoteDtoRequest noteDto) {
        var note = NoteMapper.INSTANCE.noteDtoRequestToNote(noteDto);
        note = noteRepository.save(note);
        return NoteMapper.INSTANCE.noteToNoteDto(note);
    }

    @Override
    public NoteDto findById(Integer id) throws EntityNotFoundException {
        var optionalNote = noteRepository.findById(id);
        var note = optionalNote.orElseThrow(
                () -> new EntityNotFoundException("Note with id: " + id + " doesn't exist")
        );
        return NoteMapper.INSTANCE.noteToNoteDto(note);
    }

    @Override
    public NoteDto update(NoteDtoRequest noteDto, Integer id) throws EntityNotFoundException {
        var note = NoteMapper.INSTANCE.noteDtoRequestToNote(noteDto);

        note = noteRepository.findById(id)
                .map(n -> {
                    n.setTitle(noteDto.title());
                    n.setText(noteDto.text());
                    return noteRepository.save(n);
                })
                .orElseThrow(() -> new EntityNotFoundException("Note with id: " + id + " doesn't exist"));

        return NoteMapper.INSTANCE.noteToNoteDto(note);
    }

    @Override
    public void deleteById(Integer id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note with id: " + id + " doesn't exist");
        }

        noteRepository.deleteById(id);
    }
}
