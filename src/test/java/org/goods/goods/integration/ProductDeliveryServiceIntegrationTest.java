package org.goods.goods.integration;

import lombok.RequiredArgsConstructor;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductDelivery;
import org.goods.goods.service.impl.ProductDeliveryServiceImpl;
import org.goods.goods.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.NotActiveException;
import java.util.List;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProductDeliveryServiceIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductDeliveryServiceImpl productDeliveryService;

    private Product product;

    private long productId;

    private Product createdProduct;

    private ProductDelivery productDelivery;

    @BeforeEach
    void beforeEach() {
        product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(10)
                .build();

        createdProduct = productService.createProduct(product);
        productId = createdProduct.getId();

        productDelivery = ProductDelivery.builder()
                .title("Title")
                .product(createdProduct)
                .amount(10)
                .build();
    }

    @AfterEach
    void afterEach() {
        productService.deleteProduct(productId);
    }

    @Test
    void createDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        long id = savedDelivery.getId();
        productDeliveryService.deleteDelivery(id);

        test(savedDelivery);
        Assertions.assertEquals(20, savedDelivery.getProduct().getInStock());
    }

    @Test
    void updateDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        long id = savedDelivery.getId();
        productDelivery.setTitle("Another title");
        ProductDelivery updatedDelivery = productDeliveryService.updateDelivery(productDelivery, id);
        productDeliveryService.deleteDelivery(id);

        test(updatedDelivery);
    }

    @Test
    void findDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        long id = savedDelivery.getId();

        ProductDelivery foundedDelivery = productDeliveryService.getDelivery(id);
        productDeliveryService.deleteDelivery(id);

        test(foundedDelivery);
    }

    @Test
    void deleteDeliveryTest() {
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        long id = savedDelivery.getId();

        productDeliveryService.deleteDelivery(id);

        Assertions.assertThrows(NotFoundException.class, () -> productDeliveryService.getDelivery(id));
    }

    @Test
    void findAllDeliveriesTest() {
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        long id = savedDelivery.getId();

        List<ProductDelivery> deliveries = productDeliveryService.getAllDelivery();
        productDeliveryService.deleteDelivery(id);

        Assertions.assertFalse(deliveries.isEmpty());
    }

    @Test
    void deliveryForNonExistentProductTest() {
        productDelivery.setProduct(Product.builder().id(999L).build());
        Assertions.assertThrows(NotFoundException.class, () -> productDeliveryService.createDelivery(productDelivery));
    }

    private void test(ProductDelivery testedDelivery) {
        Assertions.assertEquals(productDelivery.getTitle(), testedDelivery.getTitle());
        Assertions.assertEquals(productDelivery.getAmount(), testedDelivery.getAmount());
        Assertions.assertEquals(productDelivery.getProduct().getId(), testedDelivery.getProduct().getId());
    }
}
