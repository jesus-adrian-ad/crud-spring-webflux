package asl.development.service;

import asl.development.domain.request.ProductRequest;
import asl.development.domain.response.ProductResponse;
import asl.development.domain.response.ProductResponseInfo;
import asl.development.exception.CustomException;
import asl.development.mapper.IProductMapper;
import asl.development.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    private static final String SAVED = "Product Created Successfully";
    private static final String PRODUCT_NOT_FOUND = "Product not found";
    private static final String PRODUCT_UPDATED = "Updated Product";
    private static final String PRODUCT_DELETED = "Deleted Product";

    @Override
    public Mono<ProductResponse> getProductById(int id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .map(productMapper::toResponse);
    }

    @Override
    public Flux<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<ProductResponseInfo> createProduct(ProductRequest createProductRequest){
        return Mono.just(productMapper.toEntity(createProductRequest))
                .flatMap(productRepository::save)
                .map(ignored ->
                        new ProductResponseInfo(
                                SAVED,
                                HttpStatus.CREATED.value(),
                                LocalDateTime.now())
                );
    }

    @Override
    public Mono<ProductResponseInfo> updateProduct(int id, ProductRequest productRequest){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .flatMap(product -> {
                    productMapper.updateEntityFromRequest(productRequest, product);
                    return productRepository.save(product);
                })
                .map(ignored ->
                        new ProductResponseInfo(PRODUCT_UPDATED, HttpStatus.OK.value(), LocalDateTime.now()));
    }

    @Override
    public Mono<ProductResponseInfo> deleteProduct(int id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .flatMap(productRepository::delete)
                .then(Mono.fromCallable(() -> new ProductResponseInfo(PRODUCT_DELETED, HttpStatus.OK.value(), LocalDateTime.now())));
    }
}
