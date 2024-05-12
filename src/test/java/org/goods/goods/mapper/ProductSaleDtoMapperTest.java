package org.goods.goods.mapper;

import org.goods.goods.dto.ProductSaleIncomingDto;
import org.goods.goods.dto.ProductSaleOutcomingDto;
import org.goods.goods.dto.mapper.ProductDtoMapperImpl;
import org.goods.goods.dto.mapper.ProductSaleDtoMapperImpl;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ProductSaleDtoMapperTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(ProductSaleDtoMapperImpl.class,
            ProductDtoMapperImpl.class);

    ProductSaleDtoMapperImpl productSaleDtoMapper;

    ProductSaleIncomingDto productSaleIncomingDto = ProductSaleIncomingDto.builder()
            .title("Title")
            .productId(1)
            .amount(10)
            .build();

    ProductSale productSale = ProductSale.builder()
            .id(1L)
            .title("Title")
            .product(Product.builder()
                    .id(1L)
                    .name("name")
                    .description("description")
                    .cost(135.1)
                    .inStock(10)
                    .build())
            .amount(10)
            .totalCost(1351.0)
            .build();

    @BeforeEach
    void beforeEach() {
        productSaleDtoMapper = context.getBean(ProductSaleDtoMapperImpl.class);
    }

    @Test
    void productSaleDtoMapper_incomingMap() {
        ProductSale mappedSale = productSaleDtoMapper.incomingMap(productSaleIncomingDto);

        Assertions.assertEquals(productSaleIncomingDto.getTitle(), mappedSale.getTitle());
        Assertions.assertEquals(productSaleIncomingDto.getAmount(), mappedSale.getAmount());
        Assertions.assertEquals(productSaleIncomingDto.getProductId(), mappedSale.getProduct().getId());
    }

    @Test
    void productSaleDtoMapper_outcomingMap() {
        ProductSaleOutcomingDto mappedSale = productSaleDtoMapper.outcomingMap(productSale);

        Assertions.assertEquals(productSale.getId(), mappedSale.getId());
        Assertions.assertEquals(productSale.getTitle(), mappedSale.getTitle());
        Assertions.assertEquals(productSale.getAmount(), mappedSale.getAmount());
        Assertions.assertEquals(productSale.getTotalCost(), mappedSale.getTotalCost());
        Assertions.assertEquals(productSale.getProduct().getId(), mappedSale.getProduct().getId());
        Assertions.assertEquals(productSale.getProduct().getName(), mappedSale.getProduct().getName());
        Assertions.assertEquals(productSale.getProduct().getDescription(), mappedSale.getProduct().getDescription());
        Assertions.assertEquals(productSale.getProduct().getCost(), mappedSale.getProduct().getCost());
        Assertions.assertEquals(productSale.getProduct().getInStock(), mappedSale.getProduct().getInStock());
    }
}
