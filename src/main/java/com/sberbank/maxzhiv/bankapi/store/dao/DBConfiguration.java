package com.sberbank.maxzhiv.bankapi.store.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.sberbank.maxzhiv.bankapi.store.dao.interfaces.ICardDAO;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedSet;

@Getter
@Component
public class DBConfiguration {
    private final SessionFactory factory;
    
    DBConfiguration() {
        factory = new Configuration().configure().buildSessionFactory();
    }
}
