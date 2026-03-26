package asl.development.mapper;

import asl.development.domain.entity.Product;
import asl.development.domain.request.CreateProductRequest;
import asl.development.domain.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    @Mapping(target = "productName", source = "name")
    @Mapping(target = "productPrice", source = "price")
    ProductResponse toResponse(Product product);


    Product toEntity(CreateProductRequest productRequest);
}
