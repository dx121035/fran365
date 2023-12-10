package com.example.fran365.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ResourceForm {

    @NotNull(message = "빈칸을 입력해주세요")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private Integer amount;
}
