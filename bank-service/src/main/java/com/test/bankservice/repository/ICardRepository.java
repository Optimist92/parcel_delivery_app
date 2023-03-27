package com.test.bankservice.repository;

import com.test.bankservice.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByNumber(String number);
}
