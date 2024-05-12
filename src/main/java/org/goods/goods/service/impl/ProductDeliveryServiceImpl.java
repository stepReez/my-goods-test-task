package org.goods.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goods.goods.exception.BadRequestException;
import org.goods.goods.exception.NotFoundException;
import org.goods.goods.model.ProductDelivery;
import org.goods.goods.model.Product;
import org.goods.goods.repository.ProductDeliveryRepository;
import org.goods.goods.repository.ProductRepository;
import org.goods.goods.service.ProductDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductDeliveryServiceImpl implements ProductDeliveryService {
    private final ProductDeliveryRepository productDeliveryRepository;

    private final ProductRepository productRepository;

    @Override
    public ProductDelivery createDelivery(ProductDelivery productDelivery) {
        Product product = productRepository.findById(productDelivery.getProduct().getId()).orElseThrow(() ->
                new NotFoundException("Cant save delivery for a non-existent product"));
        product.setInStock(product.getInStock() + productDelivery.getAmount());
        productRepository.save(product);
        ProductDelivery newProductDelivery = productDeliveryRepository.save(productDelivery);
        newProductDelivery.setProduct(product);
        log.info("Delivery with id = {} saved", newProductDelivery.getId());
        return newProductDelivery;
    }

    @Override
    public ProductDelivery updateDelivery(ProductDelivery productDelivery, long id) {
        int oldAmount = productDeliveryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cant update non-existent delivery")).getAmount();

        Product product = productRepository.findById(productDelivery.getProduct().getId()).orElseThrow(() ->
                new NotFoundException("Cant save delivery for a non-existent product"));

        int newAmount = product.getInStock() - oldAmount + productDelivery.getAmount();
        if (newAmount < 0) {
            throw new BadRequestException("New amount of products cant be < 0");
        }
        product.setInStock(newAmount);
        productRepository.save(product);

        productDelivery.setId(id);
        ProductDelivery newProductDelivery = productDeliveryRepository.save(productDelivery);
        newProductDelivery.setProduct(product);
        log.info("Delivery with id = {} updated", newProductDelivery.getId());
        return newProductDelivery;
    }

    @Override
    public ProductDelivery getDelivery(long id) {
        ProductDelivery productDelivery = productDeliveryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Delivery with id = %d not found", id)));
        log.info("Delivery with id = {} found", productDelivery.getId());
        return productDelivery;
    }

    @Override
    public void deleteDelivery(long id) {
        productDeliveryRepository.deleteById(id);
        log.info("Delivery with id = {} deleted", id);
    }

    @Override
    public List<ProductDelivery> getAllDelivery() {
        List<ProductDelivery> productDeliveries = productDeliveryRepository.findAll();
        log.info("All deliveries found");
        return productDeliveries;
    }
}
