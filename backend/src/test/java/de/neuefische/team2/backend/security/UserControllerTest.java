package de.neuefische.team2.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
@Autowired
    MockMvc mockMvc;

    @Test
    void getCurrentUserTest_whenUserWithoutLogin() throws Exception{
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("anonymousUser"));
    }
    @Test
    void getCurrentUserTest_whenUserLogin() throws Exception{
        mockMvc.perform(get("/api/users/me")
                .with(oidcLogin()
                        .userInfoToken(token ->
                                token.claim("login", "test-user"))))
                .andExpect(status().isOk())
                .andExpect(content().string("test-user"));
    }
}