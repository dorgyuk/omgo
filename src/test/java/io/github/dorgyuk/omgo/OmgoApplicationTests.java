package io.github.dorgyuk.omgo;

import java.time.Clock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.dorgyuk.omgo.global.config.PasswordEncoderConfig;
import io.github.dorgyuk.omgo.global.exception.GlobalExceptionHandler;
import io.github.dorgyuk.omgo.auth.AuthController;
import io.github.dorgyuk.omgo.auth.AuthService;
import io.github.dorgyuk.omgo.auth.token.JwtProvider;
import io.github.dorgyuk.omgo.user.UserRepository;
import io.github.dorgyuk.omgo.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
	// .env에 키가 없어도 컨텍스트가 뜨도록 테스트용 키를 지정한다.
	"jwt.secret=test-secret-key-for-omgo-application-tests-0123"
})
class OmgoApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void 애플리케이션의_주요_빈이_하나씩_등록된다() {
		assertEquals(1, context.getBeansOfType(AuthController.class).size());
		assertEquals(1, context.getBeansOfType(UserService.class).size());
		assertEquals(1, context.getBeansOfType(UserRepository.class).size());
		assertEquals(1, context.getBeansOfType(PasswordEncoderConfig.class).size());
		assertEquals(1, context.getBeansOfType(PasswordEncoder.class).size());
		assertEquals(1, context.getBeansOfType(GlobalExceptionHandler.class).size());
	}

	@Test
	void 컨트롤러에_스프링이_관리하는_서비스가_주입된다() {
		AuthController controller = context.getBean(AuthController.class);
		AuthService service = context.getBean(AuthService.class);

		assertSame(service, ReflectionTestUtils.getField(controller, "authService"));
	}

	@Test
	void 등록된_인코더_빈으로_Argon2id_해싱과_검증이_동작한다() {
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		String password = "abcd1234!";

		String hash = encoder.encode(password);

		assertTrue(hash.startsWith("$argon2id$"));
		assertTrue(encoder.matches(password, hash));
		assertFalse(encoder.matches("wrong1234!", hash));
	}

	@Test
	void JwtProvider에_스프링이_관리하는_Clock이_주입된다() {
		JwtProvider jwtProvider = context.getBean(JwtProvider.class);

		assertSame(context.getBean(Clock.class),
			ReflectionTestUtils.getField(jwtProvider, "clock"));
	}

	@Test
	void 등록된_JwtProvider_빈으로_액세스_토큰을_발급하고_읽을_수_있다() {
		JwtProvider jwtProvider = context.getBean(JwtProvider.class);

		String token = jwtProvider.createAccessToken(42L);

		assertEquals(42L, jwtProvider.parse(token).userId());
	}

}
