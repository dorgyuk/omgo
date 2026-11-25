package io.github.dorgyuk.omgo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 저장과 식별자 및 닉네임 기반 조회를 제공한다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByGoogleId(String googleId);

	Optional<User> findByNicknameIgnoreCase(String nickname);

	boolean existsByNicknameIgnoreCase(String nickname);

	boolean existsByEmailIgnoreCase(String email);

}
