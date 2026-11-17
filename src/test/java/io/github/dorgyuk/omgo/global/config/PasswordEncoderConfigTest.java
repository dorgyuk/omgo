package io.github.dorgyuk.omgo.global.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderConfigTest {

    private final PasswordEncoder passwordEncoder = new PasswordEncoderConfig().passwordEncoder();

    @Test
    void 비밀번호를_Argon2id로_해싱하고_원문으로_검증할_수_있다() {
        // 준비
        String password = "abcd1234!";

        // 실행
        String hashedPassword = passwordEncoder.encode(password);

        // 검증
        assertNotEquals(password, hashedPassword);
        assertTrue(hashedPassword.startsWith("$argon2id$"));
        assertTrue(passwordEncoder.matches(password, hashedPassword));
    }

    @Test
    void 다른_비밀번호는_해시와_일치하지_않는다() {
        // 준비
        String hashedPassword = passwordEncoder.encode("abcd1234!");

        // 실행
        boolean matches = passwordEncoder.matches("wrong1234!", hashedPassword);

        // 검증
        assertFalse(matches);
    }

    @Test
    void 같은_비밀번호도_매번_다른_해시가_생성된다() {
        // 준비
        String password = "abcd1234!";

        // 실행
        String firstHash = passwordEncoder.encode(password);
        String secondHash = passwordEncoder.encode(password);

        // 검증
        assertNotEquals(firstHash, secondHash);
        assertTrue(passwordEncoder.matches(password, firstHash));
        assertTrue(passwordEncoder.matches(password, secondHash));
    }
}
