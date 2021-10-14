package com.sberbank.maxzhiv.bankapi;

import com.sberbank.maxzhiv.bankapi.integation.*;
import org.h2.engine.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountControllerTest.class,
        CardControllerTest.class,
        OperationControllerTest.class,
        PartnerControllerTest.class,
        UserControllerTest.class
})
public class BankApiApplicationTest {
    @Test
    public void contextLoads() {
    }
}
