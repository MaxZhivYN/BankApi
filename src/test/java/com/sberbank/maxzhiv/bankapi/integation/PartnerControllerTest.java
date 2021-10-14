package com.sberbank.maxzhiv.bankapi.integation;

import com.sberbank.maxzhiv.bankapi.api.controllers.AccountController;
import com.sberbank.maxzhiv.bankapi.api.controllers.PartnerController;
import com.sberbank.maxzhiv.bankapi.api.exceptions.BadRequestException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NoMoneyException;
import com.sberbank.maxzhiv.bankapi.api.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class PartnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartnerController partnerController;

    @Test public void partnerControllerIsPresentTest() {
        Assertions.assertThat(partnerController).isNotNull();
    }

    @Test
    public void createGoodTest() throws Exception {
        mockMvc.perform(post("/api/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstname\": \"Vova\",\n" +
                        "    \"lastname\": \"Volunov\",\n" +
                        "    \"email\": \"vv@mail.ru\",\n" +
                        "    \"bankName\": \"Tinkoff\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createBadBankTest() throws Exception {
        mockMvc.perform(post("/api/partners")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"firstname\": \"Vova\",\n" +
                            "    \"lastname\": \"Volunov\",\n" +
                            "    \"email\": \"vv@mail.ru\",\n" +
                            "    \"bankName\": \"asdasdasdasdasdas\"\n" +
                            "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/api/partners"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void pushMoneyToPartnerGoodTest() throws Exception {
        mockMvc.perform(patch("/api/partners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"cardFromNumber\": \"1111111111111111\",\n" +
                                "    \"money\": 1000\n" +
                                "}"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\n" +
                            "    \"answer\": true,\n" +
                            "    \"description\": \"success\"\n" +
                            "}"));
    }

    @Test
    public void pushMoneyToPartnerPartnerNotFoundTest() throws Exception {
        mockMvc.perform(patch("/api/partners/0")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"cardFromNumber\": \"1111111111111111\",\n" +
                            "    \"money\": 1000\n" +
                            "}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Partner with id 0 not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void pushMoneyToPartnerCardNotFoundTest() throws Exception {
        mockMvc.perform(patch("/api/partners/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"cardFromNumber\": \"1111111111111112\",\n" +
                        "    \"money\": 1000\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Card with number 1111111111111112 not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void pushMoneyToPartnerLowMoneyTest() throws Exception {
        mockMvc.perform(patch("/api/partners/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"cardFromNumber\": \"1111111111111111\",\n" +
                        "    \"money\": -1000\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("'money' need to be > 0", result.getResolvedException().getMessage()));
    }

    @Test
    public void pushMoneyToPartnerNoMoneyOnCardTest() throws Exception {
        mockMvc.perform(patch("/api/partners/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"cardFromNumber\": \"1111111111111111\",\n" +
                        "    \"money\": 10000000\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isPaymentRequired())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoMoneyException))
                .andExpect(result -> assertEquals("No money enough on card '1111111111111111'", result.getResolvedException().getMessage()));
    }
}
