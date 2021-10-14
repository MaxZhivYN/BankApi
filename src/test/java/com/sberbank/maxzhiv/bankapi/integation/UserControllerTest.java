package com.sberbank.maxzhiv.bankapi.integation;


import com.sberbank.maxzhiv.bankapi.api.controllers.PartnerController;
import com.sberbank.maxzhiv.bankapi.api.controllers.UserController;
import com.sberbank.maxzhiv.bankapi.api.dto.UserDto;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    public void userControllerIsPresentTest() {
        Assertions.assertThat(userController).isNotNull();
    }

    @Test
    public void createGoodTest() throws Exception {
        mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"username\": \"username3\",\n" +
                            "    \"firstname\": \"firstname3\",\n" +
                            "    \"lastname\": \"lastnam3e\",\n" +
                            "    \"email\": \"email3\"\n" +
                            "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"username\": \"username3\",\n" +
                        "    \"firstname\": \"firstname3\",\n" +
                        "    \"lastname\": \"lastnam3e\",\n" +
                        "    \"email\": \"email3\"\n" +
                        "}"));
    }

    @Test
    public void createUserAlreadyExistsTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"username1\",\n" +
                        "    \"firstname\": \"firstname3\",\n" +
                        "    \"lastname\": \"lastnam3e\",\n" +
                        "    \"email\": \"email3\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("This user already exists", result.getResolvedException().getMessage()));

    }
 }
