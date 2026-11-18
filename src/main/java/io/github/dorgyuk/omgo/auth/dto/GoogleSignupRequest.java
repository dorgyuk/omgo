package io.github.dorgyuk.omgo.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GoogleSignupRequest(
// @formatter:off
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 12, message = "닉네임은 2~12자여야 합니다.")
	@Pattern(
		regexp = "[가-힣a-zA-Z0-9]+",
		message = "닉네임은 한글 완성형, 영문, 숫자만 사용할 수 있습니다."
	)
	String nickname
	// @formatter:on
) {
}
