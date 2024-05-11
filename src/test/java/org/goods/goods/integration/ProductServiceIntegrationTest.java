package org.goods.goods.integration;

import lombok.RequiredArgsConstructor;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.service.impl.ProductServiceImpl;
import org.goods.goods.util.CostComparison;
import org.goods.goods.util.ProductSort;
import org.goods.goods.util.SortType;
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

    private long id;

    private Product createdProduct;

    @BeforeEach
    void beforeEach() {
        product = Product.builder()
                .name("name")
                .description("description")
                .cost(135.1)
                .inStock(10)
                .build();

        createdProduct = productService.createProduct(product);
        id = createdProduct.getId();
    }

    @AfterEach
    void afterEach() {
        productService.deleteProduct(id);
    }

    @Test
    void createProductTest() {
        Assertions.assertEquals(id, createdProduct.getId());
        test(createdProduct);
    }

    @Test
    void updateProductTest() {
        product.setName("Another name");

        Product updatedProduct = productService.updateProduct(product, id);
        Assertions.assertEquals(id, updatedProduct.getId());
        test(updatedProduct);
    }

    @Test
    void findProductTest() {
        Product foundedProduct = productService.findProduct(id);
        test(foundedProduct);
    }

    @Test
    void deleteProductTest() {
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

    @Test
    void querySearchTest() {
        long id = productService.createProduct(Product.builder()
                .name("Another")
                .description("description")
                .cost(135.1)
                .inStock(10)
                .build())
                .getId();

        List<Product> products = productService.searchProducts("NAmE", null, CostComparison.EQUALS, false,
                ProductSort.NAME, SortType.ASC, 0, 10);
        Product searchProduct = products.get(0);

        productService.deleteProduct(id);

        Assertions.assertEquals(1, products.size());
        test(searchProduct);
    }

    @Test
    void equalCostSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(138.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, 135.1, CostComparison.EQUALS, false,
                ProductSort.NAME, SortType.ASC, 0, 10);
        Product searchProduct = products.get(0);

        productService.deleteProduct(id);

        Assertions.assertEquals(1, products.size());
        test(searchProduct);
    }

    @Test
    void lessCostSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, 150.0, CostComparison.LESS, false,
                ProductSort.NAME, SortType.ASC, 0, 10);
        Product searchProduct = products.get(0);

        productService.deleteProduct(id);

        Assertions.assertEquals(1, products.size());
        test(searchProduct);
    }

    @Test
    void moreCostSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(50.0)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, 100.0, CostComparison.MORE, false,
                ProductSort.NAME, SortType.ASC, 0, 10);
        Product searchProduct = products.get(0);

        productService.deleteProduct(id);

        Assertions.assertEquals(1, products.size());
        test(searchProduct);
    }

    @Test
    void inStockSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(0)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, null, CostComparison.EQUALS, true,
                ProductSort.NAME, SortType.ASC, 0, 10);
        Product searchProduct = products.get(0);

        productService.deleteProduct(id);

        Assertions.assertEquals(1, products.size());
        test(searchProduct);
    }

    @Test
    void sortByNameAscSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, null, CostComparison.EQUALS, true,
                ProductSort.NAME, SortType.ASC, 0, 10);

        Product productAnother = products.get(0);
        Product productName = products.get(1);

        productService.deleteProduct(id);

        Assertions.assertEquals(2, products.size());

        Assertions.assertEquals("Another", productAnother.getName());
        Assertions.assertEquals("description", productAnother.getDescription());
        Assertions.assertEquals(200.1, productAnother.getCost());
        Assertions.assertEquals(10, productAnother.getInStock());

        test(productName);
    }

    @Test
    void sortByNameDescSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, null, CostComparison.EQUALS, true,
                ProductSort.NAME, SortType.DESC, 0, 10);

        Product productName = products.get(0);
        Product productAnother = products.get(1);

        productService.deleteProduct(id);

        Assertions.assertEquals(2, products.size());

        Assertions.assertEquals("Another", productAnother.getName());
        Assertions.assertEquals("description", productAnother.getDescription());
        Assertions.assertEquals(200.1, productAnother.getCost());
        Assertions.assertEquals(10, productAnother.getInStock());

        test(productName);
    }

    @Test
    void sortByCostAscSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, null, CostComparison.EQUALS, true,
                ProductSort.COST, SortType.ASC, 0, 10);

        Product productName = products.get(0);
        Product productAnother = products.get(1);

        productService.deleteProduct(id);

        Assertions.assertEquals(2, products.size());

        Assertions.assertEquals("Another", productAnother.getName());
        Assertions.assertEquals("description", productAnother.getDescription());
        Assertions.assertEquals(200.1, productAnother.getCost());
        Assertions.assertEquals(10, productAnother.getInStock());

        test(productName);
    }

    @Test
    void sortByCostDescSearchTest() {
        long id = productService.createProduct(Product.builder()
                        .name("Another")
                        .description("description")
                        .cost(200.1)
                        .inStock(10)
                        .build())
                .getId();

        List<Product> products = productService.searchProducts(null, null, CostComparison.EQUALS, true,
                ProductSort.COST, SortType.DESC, 0, 10);

        Product productAnother = products.get(0);
        Product productName = products.get(1);

        productService.deleteProduct(id);

        Assertions.assertEquals(2, products.size());

        Assertions.assertEquals("Another", productAnother.getName());
        Assertions.assertEquals("description", productAnother.getDescription());
        Assertions.assertEquals(200.1, productAnother.getCost());
        Assertions.assertEquals(10, productAnother.getInStock());

        test(productName);
    }

    private void test(Product testProduct) {
        Assertions.assertEquals(product.getName(), testProduct.getName());
        Assertions.assertEquals(product.getDescription(), testProduct.getDescription());
        Assertions.assertEquals(product.getCost(), testProduct.getCost());
        Assertions.assertEquals(product.getInStock(), testProduct.getInStock());
    }
}
