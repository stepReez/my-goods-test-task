package org.goods.goods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goods.goods.dto.ProductSaleIncomingDto;
import org.goods.goods.dto.ProductSaleOutcomingDto;
import org.goods.goods.dto.mapper.ProductSaleDtoMapper;
import org.goods.goods.model.ProductSale;
import org.goods.goods.service.ProductSaleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class ProductSaleController {

    private final ProductSaleService productSaleService;

    private final ProductSaleDtoMapper productSaleDtoMapper;

    @PostMapping
    public ProductSaleOutcomingDto createDelivery(@Valid @RequestBody ProductSaleIncomingDto productSaleIncomingDto) {
        ProductSale productSale = productSaleDtoMapper.incomingMap(productSaleIncomingDto);
        ProductSale savedSale = productSaleService.createSale(productSale);
        return productSaleDtoMapper.outcomingMap(savedSale);
    }

    @PutMapping("/{id}")
    public ProductSaleOutcomingDto updateDelivery(@Valid @RequestBody ProductSaleIncomingDto productSaleIncomingDto,
                                                      @PathVariable("id") long id) {
        ProductSale productSale = productSaleDtoMapper.incomingMap(productSaleIncomingDto);
        ProductSale savedSale = productSaleService.updateSale(productSale, id);
        return productSaleDtoMapper.outcomingMap(savedSale);
    }

    @GetMapping("/{id}")
    public ProductSaleOutcomingDto getDelivery(@PathVariable("id") long id) {
        ProductSale productSale = productSaleService.getSale(id);
        return productSaleDtoMapper.outcomingMap(productSale);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable long id) {
        productSaleService.deleteSale(id);
    }
}
