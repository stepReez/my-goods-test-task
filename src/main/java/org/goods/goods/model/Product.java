package org.goods.goods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class Product {
    private Long id;

    private String name;

    private String description;

    private double cost;

    private boolean inStock;
}
