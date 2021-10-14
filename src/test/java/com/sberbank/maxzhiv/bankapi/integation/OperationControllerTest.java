package com.sberbank.maxzhiv.bankapi.integation;

import com.sberbank.maxzhiv.bankapi.api.controllers.OperationController;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OperationController operationController;

    @Test
    public void operationControllerIsPresentTest() {
        Assertions.assertThat(operationController).isNotNull();
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/api/operations"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAwaitingTest() throws Exception {
        mockMvc.perform(get("/api/operations/awaiting"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getSuccessTest() throws Exception {
        mockMvc.perform(get("/api/operations/success"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void changeStatusTest() throws Exception {
        mockMvc.perform(patch("/api/operations/4")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"newStatus\": \"SUCCESS\"\n" +
                            "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 4,\n" +
                        "    \"operationType\": \"CREATE\",\n" +
                        "    \"operationStatus\": \"SUCCESS\",\n" +
                        "    \"cardNumber\": \"4444444444444444\"\n" +
                        "}"));
    }
}
