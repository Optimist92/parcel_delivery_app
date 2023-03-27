package com.test.simulationservice.services.impl;

import com.test.simulationservice.services.IBankSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import payload.BankSimulationParams2;

@Service
@RequiredArgsConstructor
public class BankSimulationService implements IBankSimulationService {

    private final RestTemplateBuilder restTemplateBuilder;

    private final RestTemplate restTemplate;

    @Override
    public BankSimulationParams2 updateBankSimulationParams(BankSimulationParams2 params) {

        var response = restTemplate.postForEntity("http://localhost:8090/v1/simulation/update-params", params, BankSimulationParams2.class);

        return response.getBody();
    }

    @Override
    public BankSimulationParams2 readBankSimulationParams() {

        var response = restTemplate.getForObject("http://localhost:8090/v1/simulation/read-params", BankSimulationParams2.class);

        return response;
    }

}
