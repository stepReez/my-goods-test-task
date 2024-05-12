package org.goods.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.ProductSale;
import org.goods.goods.model.Product;
import org.goods.goods.repository.ProductSaleRepository;
import org.goods.goods.repository.ProductRepository;
import org.goods.goods.service.ProductSaleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSaleServiceImpl implements ProductSaleService {
    private final ProductSaleRepository productSaleRepository;

    private final ProductRepository productRepository;

    @Override
    public ProductSale createSale(ProductSale productSale) {
        Product product = productRepository.findById(productSale.getProduct().getId()).orElseThrow(() ->
                new NotFoundException("Cant save sale for a non-existent product"));
        int newAmount = product.getInStock() - productSale.getAmount();
        if (newAmount < 0) {
            throw new BadRequestException("New amount of products cant be < 0");
        }
        product.setInStock(newAmount);

        productSale.setTotalCost(productSale.getAmount() * product.getCost());

        productRepository.save(product);

        ProductSale newProductSale = productSaleRepository.save(productSale);
        newProductSale.setProduct(product);
        log.info("Sale with id = {} saved", newProductSale.getId());
        return newProductSale;
    }

    @Override
    public ProductSale updateSale(ProductSale productSale, long id) {
        int oldAmount = productSaleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cant update non-existent sale")).getAmount();

        Product product = productRepository.findById(productSale.getProduct().getId()).orElseThrow(() ->
                new NotFoundException("Cant save sale for a non-existent product"));

        int newAmount = product.getInStock() + oldAmount - productSale.getAmount();
        if (newAmount < 0) {
            throw new BadRequestException("New amount of products cant be < 0");
        }

        product.setInStock(newAmount);
        productRepository.save(product);

        productSale.setTotalCost(productSale.getAmount() * product.getCost());
        productSale.setId(id);
        ProductSale newProductSale = productSaleRepository.save(productSale);
        newProductSale.setProduct(product);
        log.info("Sale with id = {} updated", newProductSale.getId());
        return newProductSale;
    }

    @Override
    public ProductSale getSale(long id) {
        ProductSale productSale = productSaleRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Sale with id = %d not found", id)));
        log.info("Sale with id = {} found", productSale.getId());
        return productSale;
    }

    @Override
    public void deleteSale(long id) {
        productSaleRepository.deleteById(id);
        log.info("Sale with id = {} deleted", id);
    }

    @Override
    public List<ProductSale> getAllSale() {
        List<ProductSale> productSales = productSaleRepository.findAll();
        log.info("All sales found");
        return productSales;
    }
}
