package com.test.bankservice.domain;


import com.test.bankservice.domain.enums.EUserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@DiscriminatorColumn(name="role",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="role")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "users",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
//@NoArgsConstructor
//@AllArgsConstructor
@ToString
@Getter
@Setter
//@Builder
abstract public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "role",insertable=false, updatable=false)
    private EUserRole role;

/*
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }*/
}