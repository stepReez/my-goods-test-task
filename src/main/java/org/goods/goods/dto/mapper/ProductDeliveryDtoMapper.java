package org.goods.goods.dto.mapper;

import org.goods.goods.dto.ProductDeliveryIncomingDto;
import org.goods.goods.dto.ProductDeliveryOutcomingDto;
import org.goods.goods.model.ProductDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductDtoMapper.class)
public interface ProductDeliveryDtoMapper {
    @Mapping(source = "productId", target = "product.id")
    ProductDelivery incomingMap(ProductDeliveryIncomingDto productDeliveryIncomingDto);

    ProductDeliveryOutcomingDto outcomingMap(ProductDelivery productDelivery);
}
