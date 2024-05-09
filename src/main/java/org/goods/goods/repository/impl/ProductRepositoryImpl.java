package org.goods.goods.repository.impl;

import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();

    private long counter = 0;

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            counter++;
            product.setId(counter);
        } else if (!products.containsKey(product.getId())) {
            throw new BadRequestException("A non-existent product cannot be changed");
        }
        products.put(counter, product);
        return products.get(counter);
    }

    @Override
    public Product findById(long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new NotFoundException(String.format("Product with id = %d not found", id));
        }
        return product;
    }

    @Override
    public void deleteById(long id) {
        if (!products.containsKey(id)) {
            throw new BadRequestException("A non-existent product cannot be deleted");
        }
        products.remove(id);
    }

    @Override
    public List<Product> findAll() {
        return products.values().stream().toList();
    }
}
