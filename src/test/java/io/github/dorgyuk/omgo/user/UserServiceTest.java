package io.github.dorgyuk.omgo.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import io.github.dorgyuk.omgo.user.exception.NicknameAlreadyExistsException;

import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserService.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 사용하지_않는_닉네임으로_등록하면_사용자가_저장된다() {
        // 준비
        String nickname = "Player" + UUID.randomUUID().toString().substring(0, 6);
        String googleId = "test-" + UUID.randomUUID();

        // 실행
        User registeredUser = userService.registerWithGoogle(nickname, googleId);
        Long registeredId = registeredUser.getId();
        entityManager.flush();
        entityManager.clear();

        // 검증: 메모리의 객체가 아닌 DB에 저장된 결과를 확인한다.
        User foundUser = userRepository.findByGoogleId(googleId).orElseThrow();
        assertNotNull(registeredId);
        assertEquals(registeredId, foundUser.getId());
        assertEquals(nickname, foundUser.getNickname());
        assertEquals(googleId, foundUser.getGoogleId());
        assertNotNull(foundUser.getCreatedAt());
    }

    @ParameterizedTest(name = "영문 대소문자 변경 여부: {0}")
    @ValueSource(booleans = {false, true})
    void 중복_닉네임으로_등록하면_예외가_발생하고_사용자가_저장되지_않는다(boolean changeCase) {
        // 준비: 동일 표기와 대소문자만 다른 표기를 각각 검증한다.
        String nickname = "Player" + UUID.randomUUID().toString().substring(0, 6);
        String existingGoogleId = "test-" + UUID.randomUUID();
        userRepository.saveAndFlush(new User(nickname, existingGoogleId));
        entityManager.clear();

        String requestedNickname = changeCase ? nickname.toLowerCase(Locale.ROOT) : nickname;
        String newGoogleId = "test-" + UUID.randomUUID();
        long countBefore = userRepository.count();

        // 실행 및 검증
        assertThrows(NicknameAlreadyExistsException.class,
                () -> userService.registerWithGoogle(requestedNickname, newGoogleId));

        entityManager.flush();
        entityManager.clear();

        assertTrue(userRepository.findByGoogleId(newGoogleId).isEmpty());
        assertEquals(countBefore, userRepository.count());
        assertEquals(nickname,
                userRepository.findByGoogleId(existingGoogleId).orElseThrow().getNickname());
    }
}
