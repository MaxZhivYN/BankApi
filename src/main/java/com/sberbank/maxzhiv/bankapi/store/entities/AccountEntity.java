package com.sberbank.maxzhiv.bankapi.store.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "balance")
    private Double balance;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private CardEntity card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
