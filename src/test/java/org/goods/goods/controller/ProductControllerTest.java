package org.goods.goods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.goods.goods.dto.ProductIncomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.mapper.ProductDtoMapper;
import org.goods.goods.model.Product;
import org.goods.goods.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private MockMvc mvc;

    private final ProductIncomingDto productIncomingDto = ProductIncomingDto.builder()
            .name("name")
            .description("description")
            .cost(135.1)
            .inStock(true)
            .build();

    private final Product product = Product.builder()
            .name("name")
            .description("description")
            .cost(135.1)
            .inStock(true)
            .build();

    private final ProductOutcomingDto productOutcomingDto = ProductOutcomingDto.builder()
            .id(1L)
            .name("name")
            .description("description")
            .cost(135.1)
            .inStock(true)
            .build();

    @Test
    void createProductTest() throws Exception {
        when(productDtoMapper.incomingMap(any()))
                .thenReturn(product);

        when(productService.createProduct(any()))
                .thenReturn(product);

        when(productDtoMapper.outComingMap(any()))
                .thenReturn(productOutcomingDto);

        mvc.perform(post("/product")
                .content(mapper.writeValueAsBytes(productIncomingDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(productOutcomingDto.getName())))
                .andExpect(jsonPath("$.description", is(productOutcomingDto.getDescription())))
                .andExpect(jsonPath("$.cost", is(productOutcomingDto.getCost()), Double.class))
                .andExpect(jsonPath("$.inStock", is(productOutcomingDto.isInStock())));
    }

    @Test
    void updateProductTest() throws Exception {
        when(productDtoMapper.incomingMap(any()))
                .thenReturn(product);

        when(productService.updateProduct(any(), anyLong()))
                .thenReturn(product);

        when(productDtoMapper.outComingMap(any()))
                .thenReturn(productOutcomingDto);

        mvc.perform(put("/product/1")
                        .content(mapper.writeValueAsBytes(productIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(productOutcomingDto.getName())))
                .andExpect(jsonPath("$.description", is(productOutcomingDto.getDescription())))
                .andExpect(jsonPath("$.cost", is(productOutcomingDto.getCost()), Double.class))
                .andExpect(jsonPath("$.inStock", is(productOutcomingDto.isInStock())));
    }

    @Test
    void getProductByIdTest() throws Exception {
        when(productService.findProduct(anyLong()))
                .thenReturn(product);

        when(productDtoMapper.outComingMap(any()))
                .thenReturn(productOutcomingDto);

        mvc.perform(get("/product/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(productOutcomingDto.getName())))
                .andExpect(jsonPath("$.description", is(productOutcomingDto.getDescription())))
                .andExpect(jsonPath("$.cost", is(productOutcomingDto.getCost()), Double.class))
                .andExpect(jsonPath("$.inStock", is(productOutcomingDto.isInStock())));
    }

    @Test
    void getAllProductsTest() throws Exception {
        when(productService.findAllProducts())
                .thenReturn(List.of(product));

        when(productDtoMapper.outComingMap(any()))
                .thenReturn(productOutcomingDto);

        mvc.perform(get("/product")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(productOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(productOutcomingDto.getName())))
                .andExpect(jsonPath("$[0].description", is(productOutcomingDto.getDescription())))
                .andExpect(jsonPath("$[0].cost", is(productOutcomingDto.getCost()), Double.class))
                .andExpect(jsonPath("$[0].inStock", is(productOutcomingDto.isInStock())));
    }

    @Test
    void deleteProductTest() throws Exception {
        doNothing()
                .when(productService).deleteProduct(anyLong());

        mvc.perform(delete("/product/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
