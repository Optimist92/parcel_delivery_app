package com.test.bankservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@DiscriminatorColumn(name="role",discriminatorType= DiscriminatorType.STRING)
@DiscriminatorValue(value= "ROLE_CUSTOMER")
public class Customer extends User{

    @Column(name="customer_id")
    private String customerId = "xxx";
}
