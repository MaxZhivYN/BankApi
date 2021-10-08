package com.sberbank.maxzhiv.bankapi.store.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    private Integer id;

    private String name;

    private Double balance;
}
