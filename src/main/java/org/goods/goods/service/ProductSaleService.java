package org.goods.goods.service;

import org.goods.goods.model.ProductSale;

public interface ProductSaleService {

    ProductSale createSale(ProductSale productSale);

    ProductSale updateSale(ProductSale productSale, long id);

    ProductSale getSale(long id);

    void deleteSale(long id);
}
