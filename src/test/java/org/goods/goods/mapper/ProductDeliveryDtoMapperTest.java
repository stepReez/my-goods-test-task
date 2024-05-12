package org.goods.goods.mapper;

import org.goods.goods.dto.ProductDeliveryIncomingDto;
import org.goods.goods.dto.ProductDeliveryOutcomingDto;
import org.goods.goods.dto.mapper.ProductDeliveryDtoMapperImpl;
import org.goods.goods.dto.mapper.ProductDtoMapperImpl;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ProductDeliveryDtoMapperTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(ProductDeliveryDtoMapperImpl.class,
            ProductDtoMapperImpl.class);

    ProductDeliveryDtoMapperImpl productDeliveryDtoMapper;

    ProductDeliveryIncomingDto productDeliveryIncomingDto = ProductDeliveryIncomingDto.builder()
            .title("Title")
            .productId(1)
            .amount(10)
            .build();

    ProductDelivery productDelivery = ProductDelivery.builder()
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
            .build();

    @BeforeEach
    void beforeEach() {
        productDeliveryDtoMapper = context.getBean(ProductDeliveryDtoMapperImpl.class);
    }

    @Test
    void productDeliveryDtoMapper_incomingMap() {
        ProductDelivery mappedDelivery = productDeliveryDtoMapper.incomingMap(productDeliveryIncomingDto);

        Assertions.assertEquals(productDeliveryIncomingDto.getTitle(), mappedDelivery.getTitle());
        Assertions.assertEquals(productDeliveryIncomingDto.getAmount(), mappedDelivery.getAmount());
        Assertions.assertEquals(productDeliveryIncomingDto.getProductId(), mappedDelivery.getProduct().getId());
    }

    @Test
    void productDeliveryDtoMapper_outcomingMap() {
        ProductDeliveryOutcomingDto mappedDelivery = productDeliveryDtoMapper.outcomingMap(productDelivery);

        Assertions.assertEquals(productDelivery.getId(), mappedDelivery.getId());
        Assertions.assertEquals(productDelivery.getTitle(), mappedDelivery.getTitle());
        Assertions.assertEquals(productDelivery.getAmount(), mappedDelivery.getAmount());
        Assertions.assertEquals(productDelivery.getProduct().getId(), mappedDelivery.getProduct().getId());
        Assertions.assertEquals(productDelivery.getProduct().getName(), mappedDelivery.getProduct().getName());
        Assertions.assertEquals(productDelivery.getProduct().getDescription(), mappedDelivery.getProduct().getDescription());
        Assertions.assertEquals(productDelivery.getProduct().getCost(), mappedDelivery.getProduct().getCost());
        Assertions.assertEquals(productDelivery.getProduct().getInStock(), mappedDelivery.getProduct().getInStock());
    }
}
