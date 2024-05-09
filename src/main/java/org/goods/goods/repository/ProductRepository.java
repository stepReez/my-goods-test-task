package org.goods.goods.repository;

import org.goods.goods.model.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);

    Product findById(long id);

    void deleteById(long id);

    List<Product> findAll();
}
