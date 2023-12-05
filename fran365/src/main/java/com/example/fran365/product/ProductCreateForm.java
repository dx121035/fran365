package com.example.fran365.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCreateForm {

    @NotBlank(message = "제품명을 입력해주세요.")
    private String name;

    @Min(value = 1, message = "가격은 0원보다 커야합니다.")
    private int price;




}
