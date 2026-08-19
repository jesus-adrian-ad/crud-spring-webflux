package asl.development.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ProductResponse", description = "Representacion de un producto almacenado")
public class ProductResponse {

    @Schema(description = "Identificador del producto", example = "1")
    private Integer id;

    @Schema(description = "Nombre comercial del producto", example = "Teclado mecanico")
    private String productName;

    @Schema(description = "Precio del producto", example = "1299.00")
    private String productPrice;
}
