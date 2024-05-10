package org.goods.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.repository.ProductRepository;
import org.goods.goods.service.ProductService;
import org.goods.goods.util.CostComparison;
import org.goods.goods.util.ProductSort;
import org.goods.goods.util.SortType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Product with id = %d not found", id)));
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

    @Override
    public List<Product> searchProducts(String query, Double cost, CostComparison costComparison, boolean isInStock,
                                        ProductSort sort, SortType type, int from, int size) {
        List<Product> products = new ArrayList<>();

        if (sort == ProductSort.NAME) {
            products = productRepository.findProductsSortByName(query, PageRequest.of(from / size, size));
        } else if (sort == ProductSort.COST) {
            products = productRepository.findProductsSortByCost(query, PageRequest.of(from / size, size));
        }

        if (cost != null && costComparison == CostComparison.EQUALS) {
            products = products.stream().filter(x -> x.getCost() == cost).toList();
        } else if (cost != null && costComparison == CostComparison.MORE) {
            products = products.stream().filter(x -> x.getCost() >= cost).toList();
        } else if (cost != null && costComparison == CostComparison.LESS) {
            products = products.stream().filter(x -> x.getCost() <= cost).toList();
        }

        if (isInStock) {
            products = products.stream().filter(Product::isInStock).toList();
        }

        if (type == SortType.DESC) {
            List<Product> reverseList = new ArrayList<>();
            for(int i = products.size() - 1; i >= 0; i -= 1) {
                reverseList.add(products.get(i));
            }
            products = reverseList;
        }

        return products;
    }
}
