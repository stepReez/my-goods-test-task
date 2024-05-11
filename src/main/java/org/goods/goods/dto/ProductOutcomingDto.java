package org.goods.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class ProductOutcomingDto {
    private Long id;

    private String name;

    private String description;

    private double cost;

    private int inStock;
}
