package org.goods.goods.service;

import org.goods.goods.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product, long id);

    Product findProduct(long id);

    void deleteProduct(long id);

    List<Product> findAllProducts();
}
