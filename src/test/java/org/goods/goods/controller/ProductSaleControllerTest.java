package org.goods.goods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.ProductSaleIncomingDto;
import org.goods.goods.dto.ProductSaleOutcomingDto;
import org.goods.goods.dto.mapper.ProductSaleDtoMapper;
import org.goods.goods.model.Product;
import org.goods.goods.model.ProductSale;
import org.goods.goods.service.ProductSaleService;
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

@WebMvcTest(controllers = ProductSaleController.class)
class ProductSaleControllerTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductSaleService productSaleService;

    @MockBean
    private ProductSaleDtoMapper productSaleDtoMapper;

    @Autowired
    private MockMvc mvc;

    private final ProductSaleIncomingDto productSaleIncomingDto = ProductSaleIncomingDto.builder()
            .title("Title")
            .productId(1)
            .amount(10)
            .build();

    private final ProductSale productSale = ProductSale.builder()
            .id(1L)
            .title("Title")
            .product(Product.builder().id(1L).name("Name").build())
            .amount(10)
            .build();

    private final ProductSaleOutcomingDto productSaleOutcomingDto = ProductSaleOutcomingDto.builder()
            .id(1)
            .title("Title")
            .product(ProductOutcomingDto.builder().id(1L).name("Name").build())
            .amount(10)
            .build();

    @Test
    void createSaleTest() throws Exception {
        when(productSaleDtoMapper.incomingMap(any()))
                .thenReturn(productSale);

        when(productSaleService.createSale(any()))
                .thenReturn(productSale);

        when(productSaleDtoMapper.outcomingMap(any()))
                .thenReturn(productSaleOutcomingDto);

        mvc.perform(post("/sale")
                        .content(mapper.writeValueAsBytes(productSaleIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productSaleOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productSaleOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productSaleOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productSaleOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productSaleOutcomingDto.getAmount())));
    }

    @Test
    void updateSaleTest() throws Exception {
        when(productSaleDtoMapper.incomingMap(any()))
                .thenReturn(productSale);

        when(productSaleService.updateSale(any(), anyLong()))
                .thenReturn(productSale);

        when(productSaleDtoMapper.outcomingMap(any()))
                .thenReturn(productSaleOutcomingDto);

        mvc.perform(put("/sale/1")
                        .content(mapper.writeValueAsBytes(productSaleIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productSaleOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productSaleOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productSaleOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productSaleOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productSaleOutcomingDto.getAmount())));
    }

    @Test
    void getSaleTest() throws Exception {
        when(productSaleService.getSale(anyLong()))
                .thenReturn(productSale);

        when(productSaleDtoMapper.outcomingMap(any()))
                .thenReturn(productSaleOutcomingDto);

        mvc.perform(get("/sale/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productSaleOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(productSaleOutcomingDto.getTitle())))
                .andExpect(jsonPath("$.product.id", is(productSaleOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.product.name", is(productSaleOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$.amount", is(productSaleOutcomingDto.getAmount())));
    }

    @Test
    void getAllSaleTest() throws Exception {
        when(productSaleService.getAllSale())
                .thenReturn(List.of(productSale));

        when(productSaleDtoMapper.outcomingMap(any()))
                .thenReturn(productSaleOutcomingDto);

        mvc.perform(get("/sale")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(productSaleOutcomingDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title", is(productSaleOutcomingDto.getTitle())))
                .andExpect(jsonPath("$[0].product.id", is(productSaleOutcomingDto.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$[0].product.name", is(productSaleOutcomingDto.getProduct().getName())))
                .andExpect(jsonPath("$[0].amount", is(productSaleOutcomingDto.getAmount())));
    }

    @Test
    void deleteSaleTest() throws Exception {
        doNothing()
                .when(productSaleService).deleteSale(anyLong());

        mvc.perform(delete("/sale/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
