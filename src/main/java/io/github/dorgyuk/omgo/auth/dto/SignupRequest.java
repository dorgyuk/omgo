package io.github.dorgyuk.omgo.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest (
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    String email,
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).*$",
        message = "비밀번호는 영문과 숫자를 각각 하나 이상 포함해야 합니다."
    )
    String password,

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 12, message = "닉네임은 2~12자여야 합니다.")
    @Pattern(
            regexp = "[가-힣a-zA-Z0-9]+",
            message = "닉네임은 한글 완성형, 영문, 숫자만 사용할 수 있습니다."
    )
    String nickname
){}
