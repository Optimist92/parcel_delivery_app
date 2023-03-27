package com.test.bankservice.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bankservice.services.ISimulationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payload.BankSimulationParams;
import payload.BankSimulationParams2;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/v1/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final ISimulationService simulationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(path="/update-params")
    public BankSimulationParams updateSimulationParams(@RequestBody BankSimulationParams2 params) {
        /*
        System.out.println("!!!body = ");
        System.out.println(body);
        BankSimulationParams params = null;
            //params = objectMapper.readValue(body, BankSimulationParams.class);
            params = SerializationUtils.deserialize(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));

         */
        return simulationService.updateSimulationParams(params);
    }

    @PostMapping(path="/read-params")
    public BankSimulationParams readSimulationParams() {

        return simulationService.readSimulationParams();
    }

}
