package org.goods.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductSaleOutcomingDto {

    private long id;

    private String title;

    private ProductOutcomingDto product;

    private int amount;

    private double totalCost;
}
