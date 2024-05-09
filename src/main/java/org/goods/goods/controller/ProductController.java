package org.goods.goods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goods.goods.dto.ProductIncomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.mapper.ProductDtoMapper;
import org.goods.goods.model.Product;
import org.goods.goods.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final ProductDtoMapper productDtoMapper;

    @PostMapping
    public ProductOutcomingDto createProduct(@Valid @RequestBody ProductIncomingDto productIncomingDto) {
        Product product = productDtoMapper.incomingMap(productIncomingDto);
        Product savedProduct = productService.createProduct(product);
        return productDtoMapper.outComingMap(savedProduct);
    }

    @PutMapping("/{id}")
    public ProductOutcomingDto updateProduct(@Valid @RequestBody ProductIncomingDto productIncomingDto,
                                             @PathVariable("id") long id) {
        Product product = productDtoMapper.incomingMap(productIncomingDto);
        Product savedProduct = productService.updateProduct(product, id);
        return productDtoMapper.outComingMap(savedProduct);
    }

    @GetMapping("/{id}")
    public ProductOutcomingDto getProductById(@PathVariable("id") long id) {
        Product product = productService.findProduct(id);
        return productDtoMapper.outComingMap(product);
    }

    @GetMapping
    public List<ProductOutcomingDto> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return products.stream().map(productDtoMapper::outComingMap).toList();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
    }
}
