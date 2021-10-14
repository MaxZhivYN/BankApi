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
@Table(name = "Operation")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    private OperationTypeEntity operationType;

    @ManyToOne
    @JoinColumn(name = "operation_status_id", referencedColumnName = "id")
    private OperationStatusEntity operationStatus;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private CardEntity card;
}
