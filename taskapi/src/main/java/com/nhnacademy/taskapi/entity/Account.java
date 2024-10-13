package com.nhnacademy.taskapi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id
    private Long accountId;

    @OneToMany(mappedBy = "manager")
    private List<Project> projects;


    public Account(Long accountId) {
        this.accountId = accountId;
    }
}
