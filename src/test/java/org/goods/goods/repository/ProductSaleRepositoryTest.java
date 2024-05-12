package org.goods.goods.repository;

import org.goods.goods.model.Product;
import org.goods.goods.model.ProductSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class ProductSaleRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductSaleRepository productSaleRepository;

    @Autowired
    private ProductRepository productRepository;

    private ProductSale productSale;

    @BeforeEach
    void beforeEach() {
        Product product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(20)
                .build();

        product = productRepository.save(product);

        productSale = ProductSale.builder()
                .title("Title")
                .product(product)
                .amount(10)
                .build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void saveTest() {
        ProductSale savedSale = productSaleRepository.save(productSale);

        Assertions.assertNotNull(savedSale.getId());
        test(savedSale);
    }

    @Test
    void updateTest() {
        ProductSale savedSale = productSaleRepository.save(productSale);
        long id = savedSale.getId();

        productSale.setId(id);
        productSale.setTitle("Another title");
        ProductSale updatedSale = productSaleRepository.save(productSale);
        Assertions.assertEquals(id, updatedSale.getId());
        test(updatedSale);
    }

    @Test
    void findSaleByIdTest() {
        ProductSale savedSale = productSaleRepository.save(productSale);
        long id = savedSale.getId();

        ProductSale foundedSale = productSaleRepository.findById(id).get();
        test(foundedSale);
    }

    @Test
    void deleteSaleByIdTest() {
        ProductSale savedSale = productSaleRepository.save(productSale);
        long id = savedSale.getId();
        productSaleRepository.deleteById(id);

        Assertions.assertTrue(productSaleRepository.findById(id).isEmpty());
    }

    @Test
    void findAllTest() {
        productSaleRepository.save(productSale);

        List<ProductSale> sales = productSaleRepository.findAll();
        Assertions.assertEquals(1, sales.size());
        test(sales.get(0));
    }

    private void test(ProductSale testedSale) {
        Assertions.assertEquals(productSale.getTitle(), testedSale.getTitle());
        Assertions.assertEquals(productSale.getAmount(), testedSale.getAmount());
        Assertions.assertEquals(productSale.getProduct().getName(), testedSale.getProduct().getName());
    }
}
