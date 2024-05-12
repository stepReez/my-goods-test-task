package org.goods.goods.repository;

import org.goods.goods.model.Product;
import org.goods.goods.model.ProductDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class ProductDeliveryRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductDeliveryRepository productDeliveryRepository;

    @Autowired
    private ProductRepository productRepository;

    private ProductDelivery productDelivery;

    @BeforeEach
    void beforeEach() {
        Product product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(20)
                .build();

        product = productRepository.save(product);

        productDelivery = ProductDelivery.builder()
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
    void saveDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryRepository.save(productDelivery);

        Assertions.assertNotNull(savedDelivery.getId());
        test(savedDelivery);
    }

    @Test
    void updateDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryRepository.save(productDelivery);
        long id = savedDelivery.getId();

        productDelivery.setId(id);
        productDelivery.setTitle("Another title");
        ProductDelivery updatedDelivery = productDeliveryRepository.save(productDelivery);
        Assertions.assertEquals(id, updatedDelivery.getId());
        test(updatedDelivery);
    }

    @Test
    void findDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryRepository.save(productDelivery);
        long id = savedDelivery.getId();

        ProductDelivery foundedDelivery = productDeliveryRepository.findById(id).get();
        test(foundedDelivery);
    }

    @Test
    void deleteDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryRepository.save(productDelivery);
        long id = savedDelivery.getId();

        productDeliveryRepository.deleteById(id);
        Assertions.assertTrue(productDeliveryRepository.findById(id).isEmpty());
    }

    @Test
    void findAllDeliveryTest() {
        productDeliveryRepository.save(productDelivery);

        List<ProductDelivery> productDeliveries = productDeliveryRepository.findAll();
        Assertions.assertEquals(1, productDeliveries.size());
        test(productDeliveries.get(0));
    }

    private void test(ProductDelivery testedDelivery) {
        Assertions.assertEquals(productDelivery.getTitle(), testedDelivery.getTitle());
        Assertions.assertEquals(productDelivery.getAmount(), testedDelivery.getAmount());
        Assertions.assertEquals(productDelivery.getProduct().getName(), testedDelivery.getProduct().getName());
    }
}
