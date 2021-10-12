package com.sberbank.maxzhiv.bankapi.store.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
