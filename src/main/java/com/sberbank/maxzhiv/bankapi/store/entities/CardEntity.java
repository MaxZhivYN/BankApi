package com.sberbank.maxzhiv.bankapi.store.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "Card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private String number;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
