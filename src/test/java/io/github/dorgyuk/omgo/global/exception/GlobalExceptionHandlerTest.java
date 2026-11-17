package io.github.dorgyuk.omgo.global.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.dorgyuk.omgo.auth.AuthController;
import io.github.dorgyuk.omgo.user.UserService;

@WebMvcTest(AuthController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void 요청_값_검증에_실패하면_400과_INVALID_REQUEST_형식으로_응답한다() throws Exception {
        // 준비: 이메일 형식이 아닌 값
        String body = """
                {"email": "not-an-email", "password": "abcd1234", "nickname": "오목유저"}
                """;

        // 실행 & 검증
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID_REQUEST"))
                .andExpect(jsonPath("$.message").value("올바른 이메일 형식을 입력해주세요."));
    }
}
