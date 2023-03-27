package com.test.bankservice.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.bankservice.domain.enums.EOperationType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "operations", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@OperationId")
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @PrePersist
    public void prePersist() {
        publicId = UUID.randomUUID();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_from", nullable = false)
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_to")
    private Account accountTo;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EOperationType type;
}
