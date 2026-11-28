package io.github.dorgyuk.omgo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.dorgyuk.omgo.auth.dto.AuthResponse;
import io.github.dorgyuk.omgo.auth.dto.SignupRequest;
import io.github.dorgyuk.omgo.auth.token.JwtProvider;
import io.github.dorgyuk.omgo.auth.token.RefreshTokenService;
import io.github.dorgyuk.omgo.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
		AuthResult result = authService.signup(request.email(), request.password(), request.nickname());

		User user = result.user();
		String accessToken = result.accessToken();
		String refreshToken = result.refreshToken();

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.httpOnly(true)
			.secure(true)
			.sameSite("Lax")
			.path("/api/auth")
			.maxAge(RefreshTokenService.REFRESH_TOKEN_VALIDITY)
			.build();

		AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(user.getId(), user.getNickname());
		AuthResponse authResponse = new AuthResponse(accessToken, "Bearer",
			JwtProvider.ACCESS_TOKEN_VALIDITY.toSeconds(),
			userInfo);

		return ResponseEntity.status(HttpStatus.CREATED)
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(authResponse);
	}
}
