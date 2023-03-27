package com.test.mscouriers.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "couriers",
        schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Courier {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", nullable = false)
        private UUID id;

        @Column(name = "work_number", columnDefinition = "serial", insertable = false, updatable = false)
        private Long workNumber;

        @Column(name = "user_public_id")
        private Long userPublicId;

        @Column(name = "is_available")
        private Boolean isAvailable;

        /*@OneToMany(mappedBy="courier")
        private Set<Shift> shifts;*/
}
