package org.goods.goods.dto.mapper;

import org.goods.goods.dto.ProductIncomingDto;
import org.goods.goods.dto.ProductOutcomingDto;
import org.goods.goods.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    ProductOutcomingDto outComingMap(Product product);

    Product incomingMap(ProductIncomingDto productIncomingDto);
}
