package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

//Bean Validation 적용
@Getter
public class UserRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 10, message = "이름은 10글자 이내로 입력해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "올바른 이메일 형식이 아닙니다")
    private String email;

    public User toEntity() {
        return new User(
                this.name,
                this.email
        );
    }
}
