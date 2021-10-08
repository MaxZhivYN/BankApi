package com.sberbank.maxzhiv.bankapi.store.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DBConfiguration {
    private final SessionFactory factory;

    DBConfiguration() {
        factory = new Configuration().configure().buildSessionFactory();
    }
}
