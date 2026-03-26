package asl.development.domain.response;

import java.time.LocalDateTime;

public record ProductResponseInfo(String message, int code, LocalDateTime timestamp) {
}
