package com.practice.service.impl;

import com.practice.entity.ProductEntity;
import com.practice.error.EntityNotFoundException;
import com.practice.model.Product;
import com.practice.repo.ProductRepo;
import com.practice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo repo;

    public ProductServiceImpl(ProductRepo productRepository) {
        this.repo = productRepository;
    }

    private Product convertToModel(ProductEntity entity){
        Product model = new Product();
        BeanUtils.copyProperties(entity,model);
        return model;
    }

    private ProductEntity convertToEntity(Product model){
        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(model,entity);
        return entity;
    }
    @Override
    public Flux<Product> getAllProducts() {
        return repo
                .findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .map(this::convertToModel);
    }


    @Override
    public Mono<Product> getProductById(Long id) {
        return repo
                .findById(id)
                .map(this::convertToModel)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        Mono<ProductEntity> productEntity = repo
                .save(convertToEntity(product))
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .doOnError(e->log.error("Add product getting Excpetion {} ",e.getMessage()));
        return productEntity.map(this::convertToModel);
    }

    @Override
    public Mono<Product> updateProduct(Product product, Long id) {
        return repo
                .findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")))
                .flatMap(currProduct->{
                    ProductEntity entity = convertToEntity(product);
                    entity.setId(currProduct.getId());
                    return repo.save(entity).map(this::convertToModel);
                })
                .doOnError(e->log.error("Update product getting Exception {}",e.getMessage()));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return repo
                .findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")))
                .flatMap(currProduct -> repo.deleteById(currProduct.getId()));
    }
}
