package org.goods.goods.integration;

import lombok.RequiredArgsConstructor;
import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductSale;
import org.goods.goods.service.impl.ProductSaleServiceImpl;
import org.goods.goods.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProductSaleServiceIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductSaleServiceImpl productSaleService;

    private Product product;

    private long productId;

    private Product createdProduct;

    private ProductSale productSale;

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

        productSale = ProductSale.builder()
                .title("Title")
                .product(createdProduct)
                .amount(5)
                .build();
    }

    @AfterEach
    void afterEach() {
        productService.deleteProduct(productId);
    }

    @Test
    void createSaleTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();
        double totalCost = createdProduct.getCost() * productSale.getAmount();

        productSaleService.deleteSale(id);
        test(savedSale);
        Assertions.assertEquals(5, savedSale.getProduct().getInStock());
        Assertions.assertEquals(totalCost, savedSale.getTotalCost());
    }

    @Test
    void updateSaleTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();
        productSale.setTitle("Another title");

        ProductSale updatedSale = productSaleService.updateSale(productSale, id);
        productSaleService.deleteSale(id);
        test(updatedSale);
    }

    @Test
    void findSaleTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();

        ProductSale foundedSale = productSaleService.getSale(id);
        productSaleService.deleteSale(id);
        test(foundedSale);
    }

    @Test
    void deleteSaleTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();
        productSaleService.deleteSale(id);
        Assertions.assertThrows(NotFoundException.class, () ->productSaleService.getSale(id));
    }

    @Test
    void findAllSalesTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();
        List<ProductSale> sales = productSaleService.getAllSale();
        productSaleService.deleteSale(id);
        Assertions.assertFalse(sales.isEmpty());
    }

    @Test
    void saleAmountMoreThanInStockTest() {
        productSale.setAmount(15);
        Assertions.assertThrows(BadRequestException.class, () -> productSaleService.createSale(productSale));
    }

    @Test
    void saleAmountMoreThanInStockUpdateTest() {
        ProductSale savedSale = productSaleService.createSale(productSale);
        long id = savedSale.getId();
        productSale.setAmount(25);
        Assertions.assertThrows(BadRequestException.class, () -> productSaleService.updateSale(productSale, id));
        productSaleService.deleteSale(id);
    }

    @Test
    void saleForNonExistentProductTest() {
        productSale.setProduct(Product.builder().id(999L).build());
        Assertions.assertThrows(NotFoundException.class, () -> productSaleService.createSale(productSale));
    }

    private void test(ProductSale testedSale) {
        Assertions.assertEquals(productSale.getTitle(), testedSale.getTitle());
        Assertions.assertEquals(productSale.getAmount(), testedSale.getAmount());
        Assertions.assertEquals(productSale.getProduct().getId(), testedSale.getProduct().getId());
    }
}
