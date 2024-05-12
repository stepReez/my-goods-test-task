package org.goods.goods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.goods.goods.dto.ProductDeliveryIncomingDto;
import org.goods.goods.dto.ProductDeliveryOutcomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.mapper.ProductDeliveryDtoMapper;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductDelivery;
import org.goods.goods.service.ProductDeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductDeliveryController.class)
class ProductDeliveryControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductDeliveryService productDeliveryService;

    @MockBean
    private ProductDeliveryDtoMapper productDeliveryDtoMapper;

    @Autowired
    private MockMvc mvc;

    private final ProductDeliveryIncomingDto productDeliveryIncomingDto = ProductDeliveryIncomingDto.builder()
            .title("Title")
            .productId(1)
            .amount(10)
            .build();

    private final ProductDelivery productDelivery = ProductDelivery.builder()
            .id(1L)
            .title("Title")
            .product(Product.builder().id(1L).name("Name").build())
            .amount(10)
            .build();

    private final ProductDeliveryOutcomingDto productDeliveryOutcomingDto = ProductDeliveryOutcomingDto.builder()
            .id(1)
            .title("Title")
            .product(ProductOutcomingDto.builder().id(1L).name("Name").build())
            .amount(10)
            .build();


    @Test
    void createProductDeliveryTest() throws Exception {
        when(productDeliveryDtoMapper.incomingMap(any()))
                .thenReturn(productDelivery);

        when(productDeliveryService.createDelivery(any()))
                .thenReturn(productDelivery);

        when(productDeliveryDtoMapper.outcomingMap(any()))
                .thenReturn(productDeliveryOutcomingDto);

        mvc.perform(post("/delivery")
                .content(mapper.writeValueAsBytes(productDeliveryIncomingDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productDeliveryOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productDeliveryOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productDeliveryOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productDeliveryOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productDeliveryOutcomingDto.getAmount())));
    }

    @Test
    void updateProductDeliveryTest() throws Exception {
        when(productDeliveryDtoMapper.incomingMap(any()))
                .thenReturn(productDelivery);

        when(productDeliveryService.updateDelivery(any(), anyLong()))
                .thenReturn(productDelivery);

        when(productDeliveryDtoMapper.outcomingMap(any()))
                .thenReturn(productDeliveryOutcomingDto);

        mvc.perform(put("/delivery/1")
                        .content(mapper.writeValueAsBytes(productDeliveryIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productDeliveryOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productDeliveryOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productDeliveryOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productDeliveryOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productDeliveryOutcomingDto.getAmount())));
    }

    @Test
    void getProductDeliveryTest() throws Exception {
        when(productDeliveryService.getDelivery(anyLong()))
                .thenReturn(productDelivery);

        when(productDeliveryDtoMapper.outcomingMap(any()))
                .thenReturn(productDeliveryOutcomingDto);

        mvc.perform(get("/delivery/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productDeliveryOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productDeliveryOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productDeliveryOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productDeliveryOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productDeliveryOutcomingDto.getAmount())));
    }

    @Test
    void getAllDeliveryTest() throws Exception {
        when(productDeliveryService.getAllDelivery())
                .thenReturn(List.of(productDelivery));

        when(productDeliveryDtoMapper.outcomingMap(any()))
                .thenReturn(productDeliveryOutcomingDto);

        mvc.perform(get("/delivery")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(productDeliveryOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title", is(productDeliveryOutcomingDto.getTitle())))
                .andExpect(jsonPath("$[0].product.id", is(productDeliveryOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$[0].product.name", is(productDeliveryOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$[0].amount", is(productDeliveryOutcomingDto.getAmount())));
    }

    @Test
    void deleteDeliveryTest() throws Exception {
        doNothing()
                .when(productDeliveryService).deleteDelivery(anyLong());

        mvc.perform(delete("/delivery/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
