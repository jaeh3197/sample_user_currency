package com.sparta.currency_user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    //고객이 삭제될 경우 환전 요청이 모두 삭제 되도록 구현
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCurrency> exchanges = new ArrayList<UserCurrency>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }
}