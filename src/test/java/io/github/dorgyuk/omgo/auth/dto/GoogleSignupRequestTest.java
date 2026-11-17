package io.github.dorgyuk.omgo.auth.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoogleSignupRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void 검증기_준비() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void 검증기_정리() {
        factory.close();
    }

    @Test
    void 어노테이션_검증은_정상적인_한글_닉네임을_허용한다() {
        // 준비
        var request = new GoogleSignupRequest("바둑왕");

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertTrue(violations.isEmpty());
    }

    @Test
    void 어노테이션_검증은_정상적인_영문_닉네임을_허용한다() {
        // 준비
        var request = new GoogleSignupRequest("Player");

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest(name = "{0}글자 닉네임의 허용 여부는 {1}")
    @CsvSource({
        "1, false",
        "2, true",
        "12, true",
        "13, false"
    })
    void 어노테이션_검증은_닉네임_길이가_2에서_12일_때만_허용한다(int length, boolean expected) {
        // 준비
        String nickname = "a".repeat(length);
        var request = new GoogleSignupRequest(nickname);

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertEquals(expected, violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "바둑Player1"})
    void 어노테이션_검증은_숫자와_허용_문자가_혼합된_닉네임을_허용한다(String nickname) {
        // 준비
        var request = new GoogleSignupRequest(nickname);

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"바둑 왕", "바둑!", "ㄱㄴ"})
    void 어노테이션_검증은_허용하지_않는_문자가_포함된_닉네임을_거부한다(String nickname) {
        // 준비
        var request = new GoogleSignupRequest(nickname);

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 어노테이션_검증은_null과_빈_닉네임을_거부한다(String nickname) {
        // 준비
        var request = new GoogleSignupRequest(nickname);

        // 실행
        var violations = validator.validate(request);

        // 검증
        assertFalse(violations.isEmpty());
    }
}
