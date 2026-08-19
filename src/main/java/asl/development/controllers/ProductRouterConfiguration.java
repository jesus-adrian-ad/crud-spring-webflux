package asl.development.controllers;

import asl.development.domain.request.ProductRequest;
import asl.development.domain.response.ErrorResponse;
import asl.development.domain.response.ProductResponse;
import asl.development.domain.response.ProductResponseInfo;
import asl.development.handler.ProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRouterConfiguration {

    private static final String TAG = "Products";
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @RouterOperations({

            @RouterOperation(
                    path = "/api/products/{id}",
                    method = RequestMethod.GET,
                    produces = JSON,
                    operation = @Operation(
                            operationId = "getProductById",
                            tags = TAG,
                            summary = "Consulta un producto por id",
                            description = "Devuelve el producto identificado por el `id` recibido en la ruta.",
                            parameters = @Parameter(
                                    name = "id",
                                    in = ParameterIn.PATH,
                                    required = true,
                                    description = "Identificador del producto",
                                    schema = @Schema(type = "integer", format = "int32", example = "1")),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Producto encontrado",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ProductResponse.class))),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "El producto no existe",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error inesperado o problema con la base de datos",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            })),

            @RouterOperation(
                    path = "/api/products/",
                    method = RequestMethod.GET,
                    produces = JSON,
                    operation = @Operation(
                            operationId = "getAllProducts",
                            tags = TAG,
                            summary = "Lista todos los productos",
                            description = """
                                    Devuelve el catalogo completo de productos.

                                    Ojo con la barra final: la ruta registrada es `/api/products/`, no `/api/products`.""",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Listado de productos (puede venir vacio)",
                                            content = @Content(mediaType = JSON,
                                                    array = @ArraySchema(
                                                            schema = @Schema(implementation = ProductResponse.class)))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error inesperado o problema con la base de datos",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            })),

            @RouterOperation(
                    path = "/api/products",
                    method = RequestMethod.POST,
                    consumes = JSON,
                    produces = JSON,
                    operation = @Operation(
                            operationId = "createProduct",
                            tags = TAG,
                            summary = "Crea un producto",
                            description = "Persiste un nuevo producto y devuelve el acuse de recibo de la operacion.",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del producto a crear",
                                    content = @Content(mediaType = JSON,
                                            schema = @Schema(implementation = ProductRequest.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Producto creado",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ProductResponseInfo.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error inesperado o problema con la base de datos",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            })),

            @RouterOperation(
                    path = "/api/products/{id}",
                    method = RequestMethod.PUT,
                    consumes = JSON,
                    produces = JSON,
                    operation = @Operation(
                            operationId = "updateProduct",
                            tags = TAG,
                            summary = "Actualiza un producto",
                            description = """
                                    Actualiza el producto indicado. Es una actualizacion parcial:
                                    los campos que lleguen en `null` conservan su valor actual.""",
                            parameters = @Parameter(
                                    name = "id",
                                    in = ParameterIn.PATH,
                                    required = true,
                                    description = "Identificador del producto",
                                    schema = @Schema(type = "integer", format = "int32", example = "1")),
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Campos a modificar",
                                    content = @Content(mediaType = JSON,
                                            schema = @Schema(implementation = ProductRequest.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Producto actualizado",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ProductResponseInfo.class))),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "El producto no existe",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error inesperado o problema con la base de datos",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            })),

            @RouterOperation(
                    path = "/api/products/{id}",
                    method = RequestMethod.DELETE,
                    produces = JSON,
                    operation = @Operation(
                            operationId = "deleteProduct",
                            tags = TAG,
                            summary = "Elimina un producto",
                            description = "Borra el producto indicado y devuelve el acuse de recibo de la operacion.",
                            parameters = @Parameter(
                                    name = "id",
                                    in = ParameterIn.PATH,
                                    required = true,
                                    description = "Identificador del producto",
                                    schema = @Schema(type = "integer", format = "int32", example = "1")),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Producto eliminado",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ProductResponseInfo.class))),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "El producto no existe",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error inesperado o problema con la base de datos",
                                            content = @Content(mediaType = JSON,
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            }))
    })
    @Bean
    public RouterFunction<ServerResponse> productRoute(ProductHandler productHandler){
        return RouterFunctions.route(GET("/api/products/{id}"), productHandler::productByIdHandler)
                .andRoute(POST("/api/products"), productHandler::createProductHandler)
                .andRoute(PUT("/api/products/{id}"), productHandler::updateProductHandler)
                .andRoute(GET("/api/products/"), productHandler::getAllProductsHandler)
                .andRoute(DELETE("/api/products/{id}"), productHandler::deleteProductHandler);
    }
}
