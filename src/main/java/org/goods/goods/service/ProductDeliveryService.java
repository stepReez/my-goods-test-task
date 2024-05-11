package org.goods.goods.service;

import org.goods.goods.model.ProductDelivery;

public interface ProductDeliveryService {

    ProductDelivery createDelivery(ProductDelivery productDelivery);

    ProductDelivery updateDelivery(ProductDelivery productDelivery, long id);

    ProductDelivery getDelivery(long id);

    void deleteDelivery(long id);
}
