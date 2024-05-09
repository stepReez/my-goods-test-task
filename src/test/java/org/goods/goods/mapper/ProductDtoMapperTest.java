package org.goods.goods.mapper;

import org.goods.goods.dto.ProductIncomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.mapper.ProductDtoMapper;
import org.goods.goods.dto.mapper.ProductDtoMapperImpl;
import org.goods.goods.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductDtoMapperTest {
     ProductDtoMapper productDtoMapper = new ProductDtoMapperImpl();

     private final ProductIncomingDto productIncomingDto = ProductIncomingDto.builder()
             .name("name")
             .description("description")
             .cost(135.1)
             .inStock(true)
             .build();

     private final Product product = Product.builder()
             .id(1L)
             .name("name")
             .description("description")
             .cost(135.1)
             .inStock(true)
             .build();

     @Test
     void productDtoMapper_incomingMapTest() {
          Product mappedProduct = productDtoMapper.incomingMap(productIncomingDto);

          Assertions.assertEquals(productIncomingDto.getName(), mappedProduct.getName());
          Assertions.assertEquals(productIncomingDto.getDescription(), mappedProduct.getDescription());
          Assertions.assertEquals(productIncomingDto.getCost(), mappedProduct.getCost());
          Assertions.assertEquals(productIncomingDto.isInStock(), mappedProduct.isInStock());
     }

     @Test
     void productDtoMapper_outComingMapTest() {
          ProductOutcomingDto productOutcomingDto = productDtoMapper.outComingMap(product);

          Assertions.assertEquals(product.getId(), productOutcomingDto.getId());
          Assertions.assertEquals(product.getName(), productOutcomingDto.getName());
          Assertions.assertEquals(product.getDescription(), productOutcomingDto.getDescription());
          Assertions.assertEquals(product.getCost(), productOutcomingDto.getCost());
          Assertions.assertEquals(product.isInStock(), productOutcomingDto.isInStock());
     }
}
