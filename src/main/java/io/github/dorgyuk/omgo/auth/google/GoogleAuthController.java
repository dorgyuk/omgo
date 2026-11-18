package io.github.dorgyuk.omgo.auth.google;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.dorgyuk.omgo.auth.dto.GoogleSignupRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

	// TODO: Google 인증 흐름(시작·콜백·signupToken 검증) 구현 시 완성
	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody GoogleSignupRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}
