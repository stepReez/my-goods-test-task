package org.goods.goods.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class ProductDeliveryIncomingDto {

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    private long productId;

    @NotNull
    @Positive
    private int amount;
}
