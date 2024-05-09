package org.goods.goods.repository;

import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.repository.impl.ProductRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductRepositoryTest {
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void beforeEach() {
        productRepository = new ProductRepositoryImpl();
        product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(true)
                .build();
    }

    @Test
    void saveWithoutIdTest() {
        Product savedProduct = productRepository.save(product);

        Assertions.assertNotNull(savedProduct.getId());
        test(savedProduct);
    }

    @Test
    void saveTwoProductTest() {
        Product savedProduct1 = productRepository.save(product);
        long id1 = savedProduct1.getId();

        product.setId(null);
        Product savedProduct2 = productRepository.save(product);
        long id2 = savedProduct2.getId();

        Assertions.assertNotEquals(id1, id2);
        test(productRepository.findById(id1));
        test(productRepository.findById(id2));
    }

    @Test
    void saveWithIdProductAlreadyExistTest() {
        Product savedProduct = productRepository.save(product);
        long id = savedProduct.getId();
        product.setId(id);
        product.setName("Another name");

        Product updatedProduct = productRepository.save(product);
        test(updatedProduct);
    }

    @Test
    void saveWithIdProductDontExistTest() {
        long id = 111;
        product.setId(id);
        productRepository.save(product);
        Product updatedProduct = productRepository.findById(id);
        test(updatedProduct);
    }

    @Test
    void findProductByIdTest() {
        Product savedProduct = productRepository.save(product);
        long id = savedProduct.getId();

        Product foundedProduct = productRepository.findById(id);
        test(foundedProduct);
    }

    @Test
    void findProductByIdProductDontExistTest() {
        Assertions.assertThrows(NotFoundException.class, () -> productRepository.findById(111));
    }

    @Test
    void deleteByIdProductDontExistTest() {
        Assertions.assertThrows(BadRequestException.class, () -> productRepository.deleteById(111));
    }

    @Test
    void findAllTest() {
        productRepository.save(product);
        product.setId(null);
        productRepository.save(product);

        List<Product> products = productRepository.findAll();
        Assertions.assertEquals(2, products.size());
        test(products.get(0));
        test(products.get(1));
    }

    private void test(Product testProduct) {
        Assertions.assertEquals(product.getName(), testProduct.getName());
        Assertions.assertEquals(product.getDescription(), testProduct.getDescription());
        Assertions.assertEquals(product.getCost(), testProduct.getCost());
        Assertions.assertEquals(product.isInStock(), testProduct.isInStock());
    }
}
