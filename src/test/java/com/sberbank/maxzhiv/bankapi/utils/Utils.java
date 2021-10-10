package com.sberbank.maxzhiv.bankapi.utils;

import com.sberbank.maxzhiv.bankapi.CardControllerTest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Utils {

    public String getJsonAsString(String jsonName) throws URISyntaxException, IOException {
        URL resource = CardControllerTest.class.getClassLoader().getResource(jsonName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);
    }
}
