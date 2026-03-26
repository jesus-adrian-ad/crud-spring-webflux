package asl.development.domain.response;

import java.time.LocalDateTime;

public record CreateProductResponse(String message, int code, LocalDateTime timestamp) {
}
