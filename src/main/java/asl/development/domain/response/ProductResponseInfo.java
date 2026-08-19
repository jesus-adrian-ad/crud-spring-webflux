package asl.development.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ProductResponseInfo", description = "Acuse de recibo de una operacion de escritura")
public record ProductResponseInfo(

        @Schema(description = "Mensaje descriptivo del resultado", example = "Product Created Successfully")
        String message,

        @Schema(description = "Codigo HTTP asociado a la operacion", example = "201")
        int code,

        @Schema(description = "Momento en que se ejecuto la operacion", example = "2026-08-19T10:15:30.123")
        LocalDateTime timestamp) {
}
