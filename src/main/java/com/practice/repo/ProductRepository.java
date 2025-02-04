package com.practice.repo;

import com.practice.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
//    @Query("SELECT * FROM public.product")
//    Flux<ProductEntity> findAllProducts();

}
