package org.goods.goods.integration;

import lombok.RequiredArgsConstructor;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProductServiceIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(true)
                .build();
    }

    @Test
    void createProductTest() {
        Product createdProduct = productService.createProduct(product);
        long id = createdProduct.getId();

        Assertions.assertEquals(id, createdProduct.getId());
        test(createdProduct);
    }

    @Test
    void updateProductTest() {
        Product createdProduct = productService.createProduct(product);
        long id = createdProduct.getId();
        product.setName("Another name");

        Product updatedProduct = productService.updateProduct(product, id);
        Assertions.assertEquals(id, updatedProduct.getId());
        test(updatedProduct);
    }

    @Test
    void findProductTest() {
        Product createdProduct = productService.createProduct(product);
        long id = createdProduct.getId();

        Product foundedProduct = productService.findProduct(id);
        test(foundedProduct);
    }

    @Test
    void deleteProductTest() {
        Product createdProduct = productService.createProduct(product);
        long id = createdProduct.getId();

        productService.deleteProduct(id);
        Assertions.assertThrows(NotFoundException.class, () -> productService.findProduct(id));
    }

    @Test
    void findAllProductsTest() {
        productService.createProduct(product);
        List<Product> products = productService.findAllProducts();
        Assertions.assertTrue(!products.isEmpty());
    }

    @Test
    void findNotExistentProductTest() {
        Assertions.assertThrows(NotFoundException.class, () -> productService.findProduct(999L));
    }

    private void test(Product testProduct) {
        Assertions.assertEquals(product.getName(), testProduct.getName());
        Assertions.assertEquals(product.getDescription(), testProduct.getDescription());
        Assertions.assertEquals(product.getCost(), testProduct.getCost());
        Assertions.assertEquals(product.isInStock(), testProduct.isInStock());
    }
}
