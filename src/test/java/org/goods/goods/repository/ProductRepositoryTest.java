package org.goods.goods.repository;

import org.goods.goods.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    private Product productCopy;

    @BeforeEach
    void beforeEach() {
        product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(10)
                .build();

        productCopy = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(10)
                .build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
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

        Product savedProduct2 = productRepository.save(productCopy);
        long id2 = savedProduct2.getId();

        Assertions.assertNotEquals(id1, id2);
        test(productRepository.findById(id1).get());
        test(productRepository.findById(id2).get());
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
    void findProductByIdTest() {
        Product savedProduct = productRepository.save(product);
        long id = savedProduct.getId();

        Product foundedProduct = productRepository.findById(id).get();
        test(foundedProduct);
    }

    @Test
    void findAllTest() {
        System.out.println(product.getId());
        productRepository.save(product);
        productRepository.save(productCopy);

        List<Product> products = productRepository.findAll();
        Assertions.assertEquals(2, products.size());
        test(products.get(0));
        test(products.get(1));
    }

    private void test(Product testProduct) {
        Assertions.assertEquals(product.getName(), testProduct.getName());
        Assertions.assertEquals(product.getDescription(), testProduct.getDescription());
        Assertions.assertEquals(product.getCost(), testProduct.getCost());
        Assertions.assertEquals(product.getInStock(), testProduct.getInStock());
    }
}
