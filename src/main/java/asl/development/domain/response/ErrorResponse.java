package asl.development.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ErrorResponse", description = "Contrato unico de error devuelto por el manejador global de excepciones")
public class ErrorResponse {

    @Schema(description = "Descripcion del error", example = "Product not found")
    private String message;

    @Schema(description = "Codigo HTTP del error", example = "404")
    private int statusCode;

    @Schema(description = "Momento en que se produjo el error", example = "2026-08-19T10:15:30.123Z")
    private Instant timestamp;

    @Schema(description = "Ruta que origino el error", example = "/api/products/99")
    private String path;
}
