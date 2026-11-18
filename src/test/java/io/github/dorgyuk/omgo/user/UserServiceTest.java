package io.github.dorgyuk.omgo.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserService.class, UserServiceTest.PasswordEncoderConfig.class})
class UserServiceTest {

	@TestConfiguration(proxyBeanMethods = false)
	static class PasswordEncoderConfig {

		@Bean
		PasswordEncoder passwordEncoder() {
			return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		}
	}

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

}
