package com.practice.service.impl;

import com.practice.entity.ProductEntity;
import com.practice.exception.EntityNotFoundException;
import com.practice.model.Product;
import com.practice.repo.ProductRepository;
import com.practice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.repo = productRepository;
    }
    @Override
    public Flux<Product> getAllProducts() {
        return repo.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .map(this::mapToModel);
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return repo.findById(id).map(this::mapToModel)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        Mono<ProductEntity> productEntity = repo.save(this.mapToEntity(product))
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .doOnError(e -> log.error("Add product getting exception {}", e.getMessage()));
        return productEntity.map(this::mapToModel);
    }

    @Override
    public Mono<Product> updateProduct(Product product, Long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")))
                .flatMap(currentProduct -> {
                    ProductEntity productEntity = this.mapToEntity(product);
                    productEntity.setId(currentProduct.getId());
                    return repo.save(productEntity).map(this::mapToModel);
                }).doOnError(e -> log.error("Update product getting exception {}", e.getMessage()));
    }

    @Override
    public Mono<String> deleteProduct(Long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")))
                .flatMap(currentProduct ->
                    repo.deleteById(currentProduct.getId())
                            .then(Mono.just("Product Deleted Successfully!"))
                );
    }

    private Product mapToModel(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getProductType(),
                productEntity.getPrice(),
                productEntity.getQuantity()
        );
    }

    private ProductEntity mapToEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(product,entity);
        return entity;
    }

}
