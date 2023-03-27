package com.test.bankservice.domain;

import com.test.bankservice.domain.enums.EAccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "available_amount")
    private BigDecimal availableAmount;

    @Column(name = "reserved_amount")
    private BigDecimal reservedAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EAccountStatus Status;

    @Transient
    private final Object locker = new Object();

    /*
    public Account reserveAmount(BigDecimal delta) {
        synchronized (locker) {
            if (this.getAvailableAmount().compareTo(delta) < 0)
                throw new RuntimeException("not enough funds on " + this.getNumber());

            var currentAvailableAmount = this.getAvailableAmount();
            var currentReservedAmount = this.getReservedAmount();
            var newAvailableAmount = currentAvailableAmount.subtract(delta);
            var newReservedAmount = currentReservedAmount.add(delta);
            this.setAvailableAmount(newAvailableAmount);
            this.setReservedAmount(newReservedAmount);
            return this;
        }
    }*/
}