package asl.development.service;

import asl.development.domain.request.ProductRequest;
import asl.development.domain.response.ProductResponse;
import asl.development.domain.response.ProductResponseInfo;
import reactor.core.publisher.Mono;

public interface IProductService {

    Mono<ProductResponse> getProductById(int id);

    Mono<ProductResponseInfo> createProduct(ProductRequest createProductRequest);

    Mono<ProductResponseInfo> updateProduct(int id, ProductRequest productRequest);
}
