package com.example.fran365.member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberForm {

    @NotEmpty(message="빈칸을 입력해주세요")
    @Size(max=20)
    @Pattern(regexp = "^(?!\\s*$).+", message = "아이디는 공백만으로 구성될 수 없습니다.")
    private String username;

    @NotEmpty(message = "빈칸을 입력해주세요")
    @Size(max = 20, min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "비밀번호는 공백만으로 구성될 수 없습니다.")
    private String password;


    @NotEmpty(message = "빈칸을 입력해주세요")
    @Size(max = 20, min = 8, message = "비밀번호 확인은 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "비밀번호는 공백만으로 구성될 수 없습니다.")
    private String confirmPassword; // 비밀번호 확인 필드 추가


}
