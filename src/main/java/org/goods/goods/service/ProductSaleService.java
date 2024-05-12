package org.goods.goods.service;

import org.goods.goods.model.ProductSale;

import java.util.List;

public interface ProductSaleService {

    ProductSale createSale(ProductSale productSale);

    ProductSale updateSale(ProductSale productSale, long id);

    ProductSale getSale(long id);

    void deleteSale(long id);

    List<ProductSale> getAllSale();
}
