package io.github.dorgyuk.omgo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.dorgyuk.omgo.auth.dto.SignupRequest;
import io.github.dorgyuk.omgo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // TODO: 명세의 응답(AuthResponse 본문 + refreshToken 쿠키)은 토큰 발급 구현 시 반영
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        userService.registerWithEmail(request.email(), request.password(), request.nickname());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
