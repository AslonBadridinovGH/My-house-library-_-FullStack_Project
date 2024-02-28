package de.neuefische.team2.backend.security;

import de.neuefische.team2.backend.models.Message;
import de.neuefische.team2.backend.models.User;
import de.neuefische.team2.backend.repos.MessageRepo;
import de.neuefische.team2.backend.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;

    @Test
    void getCurrentUserTest_whenUserWithoutLogin() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("anonymousUser"));
    }

    @Test
    void getCurrentUserTest_whenUserLogin() throws Exception {
        userRepo.save(new User("user", "Name", new ArrayList<>(List.of("1", "2"))));
        mockMvc.perform(get("/api/users/me")
                        .with(oidcLogin()
                                .userInfoToken(token ->
                                        token.claim("id", "user").claim("login", "Name"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Name"));
    }

    @DirtiesContext
    @Test
    void updateFavorites_shouldReturnUser_whenFavoritesUpdated() throws Exception {
        //GIVEN
        String bookId = "3";
        String gitHubId = "user";
        userRepo.save(new User("user", "Name", new ArrayList<>(List.of("1", "2"))));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3")
                        .with(oidcLogin()
                                .userInfoToken(token ->
                                        token.claim("id", "user"))))

                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                         {
                              "id":"user",
                              "name":"Name",
                              "favorites": ["1","2","3"]
                              }
                        """))
                .andReturn();
    }
}