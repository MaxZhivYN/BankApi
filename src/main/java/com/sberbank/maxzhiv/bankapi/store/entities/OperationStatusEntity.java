package com.sberbank.maxzhiv.bankapi.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "OperationStatus")
public class OperationStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "operation_status_id", referencedColumnName = "id")
    private List<OperationEntity> operations = new ArrayList<>();
}
