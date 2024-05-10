package org.goods.goods.service;

import org.goods.goods.model.Product;
import org.goods.goods.util.CostComparison;
import org.goods.goods.util.ProductSort;
import org.goods.goods.util.SortType;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product, long id);

    Product findProduct(long id);

    void deleteProduct(long id);

    List<Product> findAllProducts();

    List<Product> searchProducts(String query, Double cost, CostComparison costComparison, boolean isInStock,
                                 ProductSort sort, SortType type, int from, int size);
}
