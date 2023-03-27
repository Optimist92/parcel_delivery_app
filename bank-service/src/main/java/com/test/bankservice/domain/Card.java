package com.test.bankservice.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.bankservice.domain.enums.ECardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cards", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@courseId")
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "number")
    private String number;

    @Column(name = "holder")
    private String holder;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ECardStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account")
    private Account account;
}
