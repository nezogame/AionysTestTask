package org.denys.hudymov.aionystesttask.mapper;


import org.denys.hudymov.aionystesttask.entity.Note;
import org.denys.hudymov.aionystesttask.model.request.NoteDtoRequest;
import org.denys.hudymov.aionystesttask.model.response.NoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    NoteDto noteToNoteDto(Note note);

    Note noteDtoRequestToNote(NoteDtoRequest noteDto);
}
