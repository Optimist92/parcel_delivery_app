package com.test.mscouriers.repository;

import com.test.mscouriers.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, UUID> {
}
