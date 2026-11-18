package io.github.dorgyuk.omgo.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void 사용자를_저장하고_Google_식별자로_조회한다() {
		// 준비: 기존 데이터와 충돌하지 않는 테스트용 식별자를 사용한다.
		String nickname = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
		String googleId = "test-" + UUID.randomUUID();
		User user = new User(nickname, googleId);

		// 실행: SQL을 DB에 반영하고, 1차 캐시를 비운 뒤 다시 조회한다.
		User savedUser = userRepository.saveAndFlush(user);
		Long savedId = savedUser.getId();
		entityManager.clear();

		User foundUser = userRepository.findByGoogleId(googleId).orElseThrow();

		// 검증: 필드 값과 자동 생성 값이 DB에 저장되어 있어야 한다.
		assertNotNull(savedId);
		assertEquals(savedId, foundUser.getId());
		assertEquals(nickname, foundUser.getNickname());
		assertEquals(googleId, foundUser.getGoogleId());
		assertNotNull(foundUser.getCreatedAt());
	}
}
