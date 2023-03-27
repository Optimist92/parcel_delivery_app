package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Card;
import com.test.bankservice.repository.ICardRepository;
import com.test.bankservice.services.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class CardService implements ICardService {

    private final ICardRepository cardRepository;

    public Optional<Card> getCardByNumber(String cardNumber) {
        return cardRepository.findByNumber(cardNumber);
    }
}
