package org.goods.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goods.goods.model.Product;
import org.goods.goods.repository.ProductRepository;
import org.goods.goods.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("Product with id = {} saved", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product updateProduct(Product product, long id) {
        product.setId(id);
        Product savedProduct = productRepository.save(product);
        log.info("Product with id = {} updated", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product findProduct(long id) {
        Product product = productRepository.findById(id);
        log.info("Product with id = {} found", id);
        return product;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
        log.info("Product with id = {} deleted", id);
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("All products found");
        return products;
    }
}
