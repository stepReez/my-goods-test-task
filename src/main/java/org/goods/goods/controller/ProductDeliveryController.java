package org.goods.goods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goods.goods.dto.ProductDeliveryIncomingDto;
import org.goods.goods.dto.ProductDeliveryOutcomingDto;
import org.goods.goods.dto.mapper.ProductDeliveryDtoMapper;
import org.goods.goods.model.ProductDelivery;
import org.goods.goods.service.ProductDeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class ProductDeliveryController {

    private final ProductDeliveryService productDeliveryService;

    private final ProductDeliveryDtoMapper productDeliveryDtoMapper;

    @PostMapping
    public ProductDeliveryOutcomingDto createDelivery(@Valid @RequestBody ProductDeliveryIncomingDto productDeliveryIncomingDto) {
        ProductDelivery productDelivery = productDeliveryDtoMapper.incomingMap(productDeliveryIncomingDto);
        ProductDelivery savedDelivery = productDeliveryService.createDelivery(productDelivery);
        return productDeliveryDtoMapper.outcomingMap(savedDelivery);
    }

    @PutMapping("/{id}")
    public ProductDeliveryOutcomingDto updateDelivery(@Valid @RequestBody ProductDeliveryIncomingDto productDeliveryIncomingDto,
                                                      @PathVariable("id") long id) {
        ProductDelivery productDelivery = productDeliveryDtoMapper.incomingMap(productDeliveryIncomingDto);
        ProductDelivery savedDelivery = productDeliveryService.updateDelivery(productDelivery, id);
        return productDeliveryDtoMapper.outcomingMap(savedDelivery);
    }

    @GetMapping("/{id}")
    public ProductDeliveryOutcomingDto getDelivery(@PathVariable("id") long id) {
        ProductDelivery productDelivery = productDeliveryService.getDelivery(id);
        return productDeliveryDtoMapper.outcomingMap(productDelivery);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable long id) {
        productDeliveryService.deleteDelivery(id);
    }
}
