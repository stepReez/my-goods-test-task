package org.goods.goods.dto.mapper;

import org.goods.goods.dto.ProductSaleIncomingDto;
import org.goods.goods.dto.ProductSaleOutcomingDto;
import org.goods.goods.model.ProductSale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductDtoMapper.class)
public interface ProductSaleDtoMapper {
    @Mapping(source = "productId", target = "product.id")
    ProductSale incomingMap(ProductSaleIncomingDto productSaleIncomingDto);

    ProductSaleOutcomingDto outcomingMap(ProductSale productSale);
}
