package asl.development.handler;

import asl.development.domain.request.ProductRequest;
import asl.development.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final IProductService productService;

    public Mono<ServerResponse> productByIdHandler(ServerRequest request){
        int id = Integer.parseInt(request.pathVariable("id"));
        return productService.getProductById(id)
                .flatMap(productResponse -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(productResponse)
                );
    }

    public Mono<ServerResponse> getAllProductsHandler(ServerRequest serverRequest){
        return productService.getAllProducts()
                .collectList()
                .flatMap(productResponse -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(productResponse)
                );
    }

    public Mono<ServerResponse> createProductHandler(ServerRequest serverRequest){
        return serverRequest.bodyToMono(ProductRequest.class)
                .flatMap(productService::createProduct)
                .flatMap(createResponse -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(createResponse)
                );
    }

    public Mono<ServerResponse> updateProductHandler(ServerRequest serverRequest){
        int productId = Integer.parseInt(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(ProductRequest.class)
                .flatMap(productRequest -> productService.updateProduct(productId, productRequest))
                .flatMap(createResponse -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(createResponse)
                );
    }
}
