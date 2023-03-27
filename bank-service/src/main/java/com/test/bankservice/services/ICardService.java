package com.test.bankservice.services;

import com.test.bankservice.domain.Card;

import java.util.Optional;

public interface ICardService {

    Optional<Card> getCardByNumber(String cardNumber);
}
