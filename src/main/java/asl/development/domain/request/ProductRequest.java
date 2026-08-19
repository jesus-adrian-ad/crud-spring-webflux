package asl.development.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductRequest", description = "Datos de entrada para crear o actualizar un producto")
public class ProductRequest {

    @Schema(description = "Nombre comercial del producto", example = "Teclado mecanico")
    private String name;

    @Schema(description = "Precio del producto", example = "1299.00")
    private String price;
}
