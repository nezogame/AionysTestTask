package org.denys.hudymov.aionystesttask.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record NoteDtoRequest(
        @NotBlank(message = "Note must have a title")
        String title,
        @NotBlank(message = "Note must have a text")
        String text
) {
}
