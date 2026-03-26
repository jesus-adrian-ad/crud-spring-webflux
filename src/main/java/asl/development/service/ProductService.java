package asl.development.service;

import asl.development.domain.request.CreateProductRequest;
import asl.development.domain.response.CreateProductResponse;
import asl.development.domain.response.ProductResponse;
import asl.development.exception.CustomException;
import asl.development.mapper.IProductMapper;
import asl.development.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    private static final String SAVED = "Customer Created Successfully";
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    @Override
    public Mono<ProductResponse> getProductById(int id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<CreateProductResponse> createProduct(CreateProductRequest createProductRequest){
        return Mono.just(productMapper.toEntity(createProductRequest))
                .flatMap(productRepository::save)
                .map(ignored ->
                        new CreateProductResponse(
                                SAVED,
                                HttpStatus.CREATED.value(),
                                LocalDateTime.now())
                );
    }
}
