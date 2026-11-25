package io.github.dorgyuk.omgo.auth;

import io.github.dorgyuk.omgo.user.User;

/**
 * 가입·로그인에 성공한 사용자와 발급한 토큰.
 *
 * @param user 인증된 사용자
 * @param accessToken 액세스 토큰(JWT)
 * @param refreshToken 리프레시 토큰 원문. 쿠키로만 전달한다.
 */
public record AuthResult(
// @formatter:off
	User user,
	String accessToken,
	String refreshToken
	// @formatter:on
) {
}
