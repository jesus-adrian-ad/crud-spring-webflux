package asl.development.service;

import asl.development.domain.request.ProductRequest;
import asl.development.domain.response.ProductResponse;
import asl.development.domain.response.ProductResponseInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {

    Mono<ProductResponse> getProductById(int id);

    Flux<ProductResponse> getAllProducts();

    Mono<ProductResponseInfo> createProduct(ProductRequest createProductRequest);

    Mono<ProductResponseInfo> updateProduct(int id, ProductRequest productRequest);

    Mono<ProductResponseInfo> deleteProduct(int id);
}
