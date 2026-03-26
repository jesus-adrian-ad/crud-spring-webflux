package asl.development.repository;

import asl.development.domain.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
