package io.github.dorgyuk.omgo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.dorgyuk.omgo.global.config.PasswordEncoderConfig;
import io.github.dorgyuk.omgo.global.exception.GlobalExceptionHandler;
import io.github.dorgyuk.omgo.auth.AuthController;
import io.github.dorgyuk.omgo.user.UserRepository;
import io.github.dorgyuk.omgo.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:bean-injection;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
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
        UserService service = context.getBean(UserService.class);

        assertSame(service, ReflectionTestUtils.getField(controller, "userService"));
    }

    @Test
    void 서비스에_스프링이_관리하는_리포지토리와_인코더가_주입된다() {
        UserService service = context.getBean(UserService.class);

        assertSame(context.getBean(UserRepository.class),
                ReflectionTestUtils.getField(service, "userRepository"));
        assertSame(context.getBean(PasswordEncoder.class),
                ReflectionTestUtils.getField(service, "passwordEncoder"));
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

}
