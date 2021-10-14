package com.sberbank.maxzhiv.bankapi.integation;

import com.sberbank.maxzhiv.bankapi.api.controllers.CardController;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardController cardController;

    @Test
    public void cardControllerIsPresentTest() {
        Assertions.assertThat(cardController).isNotNull();
    }

    @Test
    public void getCardByAccountIdGoodTest() throws Exception {
        mockMvc.perform(get("/api/accounts/1/cards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"number\": \"1111111111111111\"\n" +
                        "}"));
    }

    @Test
    public void getCardByAccountIdBadTest() throws Exception {
        mockMvc.perform(get("/api/accounts/11111/cards"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMoneyBalanceGoodTest() throws Exception {
        mockMvc.perform(get("/api/cards/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"money\": 10000.0\n" +
                        "}"));
    }

    @Test
    public void getMoneyBalanceBadRequestTest() throws Exception {
        mockMvc.perform(get("/api/cards/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCardGoodTest() throws Exception {
        mockMvc.perform(post("/api/accounts/5/cards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 5,\n" +
                        "    \"number\": \"0000000000000001\",\n" +
                        "    \"status\": \"AWAITING\"\n" +
                        "}"));

        mockMvc.perform(delete("/api/accounts/5/cards"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createCardNotFoundTest() throws Exception {
        mockMvc.perform(post("/api/accounts/0/cards"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void transferMoneyTest() throws Exception {
        mockMvc.perform(patch("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"fromCardNumber\": \"1111111111111111\",\n" +
                                "    \"toCardNumber\": \"2222222222222222\",\n" +
                                "    \"money\": 1000\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"answer\": true\n" +
                        "}"));

        mockMvc.perform(patch("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fromCardNumber\": \"2222222222222222\",\n" +
                        "    \"toCardNumber\": \"1111111111111111\",\n" +
                        "    \"money\": 1000\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"answer\": true\n" +
                        "}"));
    }
}
