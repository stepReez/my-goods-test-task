package org.goods.goods.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goods.goods.dto.ProductIncomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.dto.mapper.ProductDtoMapper;
import org.goods.goods.model.Product;
import org.goods.goods.service.ProductService;
import org.goods.goods.util.CostComparison;
import org.goods.goods.util.ProductSort;
import org.goods.goods.util.SortType;
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

    @GetMapping("/search")
    public List<ProductOutcomingDto> searchProducts(@RequestParam(value = "query", required = false) String query,
                                                    @RequestParam(value = "cost", required = false) Double cost,
                                                    @RequestParam(value = "costComparison", defaultValue = "EQUALS") CostComparison costComparison,
                                                    @RequestParam(value = "isInStock", defaultValue = "false") boolean isInStock,
                                                    @RequestParam(value = "sort", defaultValue = "NAME") ProductSort sort,
                                                    @RequestParam(value = "type", defaultValue = "ASC") SortType type,
                                                    @RequestParam(value = "from", defaultValue = "0") int from,
                                                    @RequestParam(value = "from", defaultValue = "10") int size) {
        List<Product> products = productService.searchProducts(query, cost, costComparison, isInStock, sort, type, from, size);
        return products.stream().map(productDtoMapper::outComingMap).toList();
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
    }
}
