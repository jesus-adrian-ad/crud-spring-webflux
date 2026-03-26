package asl.development.controllers;

import asl.development.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> productRoute(ProductHandler productHandler){
        return RouterFunctions.route(GET("/api/products/{id}"), productHandler::productByIdHandler)
                .andRoute(POST("/api/products"), productHandler::createProductHandler)
                .andRoute(PUT("/api/products/{id}"), productHandler::updateProductHandler)
                .andRoute(GET("/api/products/"), productHandler::getAllProductsHandler)
                .andRoute(DELETE("/api/products/{id}"), productHandler::deleteProductHandler);
    }
}
