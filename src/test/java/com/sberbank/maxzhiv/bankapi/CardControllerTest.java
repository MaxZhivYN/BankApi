package com.sberbank.maxzhiv.bankapi;

import com.sberbank.maxzhiv.bankapi.api.controllers.CardController;
import com.sberbank.maxzhiv.bankapi.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@FixMethodOrder(MethodSorters.JVM)
@TestPropertySource("/application-test.properties")
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardController cardController;

    @Autowired
    private Utils utils;

    @Test
    public void cardControllerIsPresentTest() {
        Assertions.assertThat(cardController).isNotNull();
    }

    @Test
    public void getCardByAccountIdGoodTest() throws Exception {
        mockMvc.perform(get("/api/accounts/1/cards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(utils.getJsonAsString("response/GetCardByAccountIdGood.json")));
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
                .andExpect(content().json(utils.getJsonAsString("response/GetMoneyBalanceGood.json")));
    }

    @Test
    public void getMoneyBalanceBadRequestTest() throws Exception {
        mockMvc.perform(get("/api/cards/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCardGoodTest() throws Exception {
        mockMvc.perform(post("/api/accounts/1/cards")
                        .queryParam("name", "Valera's card"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(utils.getJsonAsString("response/CreateCardGood.json")));
    }

    @Test
    public void createCardNotFoundTest() throws Exception {
        mockMvc.perform(post("/api/accounts/0/cards")
                        .queryParam("name", "Valera's card"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCardBadRequestTest() throws Exception {
        mockMvc.perform(post("/api/accounts/0/cards")
                        .queryParam("name1", "Valera's card"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void pushMoneyToCardGoodTest() throws Exception {
        mockMvc.perform(patch("/api/cards/1")
                        .queryParam("money", "1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(utils.getJsonAsString("response/PushMoneyToCardGood.json")));
    }

    @Test
    public void pushMoneyToCardNotFoundTest() throws Exception {
        mockMvc.perform(patch("/api/cards/0")
                        .queryParam("money", "1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void pushMoneyToCardBadRequestTest() throws Exception {
        mockMvc.perform(patch("/api/cards/0")
                        .queryParam("money1", "1000"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
