package de.neuefische.team2.backend.controller;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.repos.MessageRepo;
import de.neuefische.team2.backend.models.Message;
import de.neuefische.team2.backend.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MessageRepo messageRepo;

    @DirtiesContext
    @Test
    void getMessagesTest_whenOneMessageWasSavedInRepository_thenReturnListWithOneMessage() throws Exception {
        //GIVEN
        messageRepo.save(new Message("1", "Name", "mail", "Text", false));
        //WHEN
        mvc.perform(MockMvcRequestBuilders.get("/api/messages"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                        "id":"1",
                        "name":"Name",
                        "mail":"mail",
                        "message":"Text",
                        "read": false
                        }]
                        """));
    }

    @Test
    void deleteMessageByIdTest_whenMessageWasDeletedFromRepo_thenReturnMessage() throws Exception {
        //GIVEN
        messageRepo.save(new Message("1", "Name", "mail", "Text", false));
        //WHEN
        mvc.perform(MockMvcRequestBuilders.delete("/api/messages/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "id":"1",
                        "name":"Name",
                        "mail":"mail",
                        "message":"Text",
                        "read": false
                        }
                        """));
    }

    @Test
    void addMessageTest_whenMessageWasSavedInRepo_thenReturnMessage() throws Exception {
        //GIVEN
        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Name",
                                "mail":"mail",
                                "message":"Text",
                                "read": false
                                }
                                """)
                .with(oidcLogin()
                        .userInfoToken(token ->
                                token.claim("login", "test-user"))))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "name":"Name",
                        "mail":"mail",
                        "message":"Text",
                        "read": false
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }
    @DirtiesContext
    @Test
    void updateMessageTest_shouldReturnMessageWithUpdates_whenMessageWithUpdatesSent() throws Exception {
        //GIVEN
        messageRepo.save(new Message("1", "TestTitle", "email","TestText", true));

        //WHEN
        mvc.perform(MockMvcRequestBuilders.post("/api/messages/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                              {
                              "id":"1",
                              "name":"TestTitle",
                              "mail":"email",
                              "message":"TestText",
                              "read": false
                              }
                              """)
                .with(oidcLogin()
                        .userInfoToken(token ->
                                token.claim("login", "test-user"))))

                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                              "id":"1",
                              "name":"TestTitle",
                              "mail":"email",
                              "message":"TestText",
                              "read": false
                              }
                        """))
                .andReturn();

    }
}