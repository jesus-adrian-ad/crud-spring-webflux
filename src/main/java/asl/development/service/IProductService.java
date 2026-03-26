package asl.development.service;

import asl.development.domain.request.CreateProductRequest;
import asl.development.domain.response.CreateProductResponse;
import asl.development.domain.response.ProductResponse;
import reactor.core.publisher.Mono;

public interface IProductService {

    Mono<ProductResponse> getProductById(int id);

    Mono<CreateProductResponse> createProduct(CreateProductRequest createProductRequest);
}
