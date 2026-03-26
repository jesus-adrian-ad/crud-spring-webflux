package asl.development.handler;

import asl.development.domain.request.CreateProductRequest;
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

    public Mono<ServerResponse> createProductHandler(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateProductRequest.class)
                .flatMap(productService::createProduct)
                .flatMap(createResponse -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(createResponse)
                );
    }
}
