package org.example.infrastructure.config.httpexceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
public record ApiErrorDto(
        List<String> messages,
        HttpStatus status
) {
}
