package com.test.msorders.domain;

import enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @Column(name = "customer_public_id")
    private Long customerPublicId;

    @Column(name="courier_public_id")
    private Long courierPublicId;

    @Column(name = "location_from")
    private String locationFrom;

    @Column(name = "location_to")
    private String locationTo;

    @Column(name = "cost")
    private BigDecimal cost;


    /*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "courses_modules",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Module> modules;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;
     */
}
