package org.denys.hudymov.aionystesttask.model.response;

import lombok.Builder;

@Builder
public record NoteDto(
        Integer id,
        String title,
        String text
) {
}
