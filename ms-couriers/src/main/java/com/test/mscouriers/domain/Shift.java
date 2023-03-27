package com.test.mscouriers.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "shifts",
        schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    /*@ManyToOne
    @JoinColumn(name="courier_work_number", nullable=false)
    private Courier courier;*/

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;
}
