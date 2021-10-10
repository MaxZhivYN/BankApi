package com.sberbank.maxzhiv.bankapi.store.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CardEntity {
    private Integer id;

    private String name;

    private Double balance;

    private AccountEntity account;
}
