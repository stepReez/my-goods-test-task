package org.goods.goods.dto;

import jakarta.validation.constraints.*;
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
public class ProductIncomingDto {
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 4096)
    private String description;

    @PositiveOrZero
    private double cost = 0;

    private int inStock;
}
