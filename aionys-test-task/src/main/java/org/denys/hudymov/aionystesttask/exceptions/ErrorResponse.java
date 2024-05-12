package org.denys.hudymov.aionystesttask.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus statusCode;
    private LocalDateTime timestamp;
    private List<String> errors;
}