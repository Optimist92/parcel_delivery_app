package com.test.simulationservice.controllers;

import com.test.simulationservice.payload.SimulationParams;
import com.test.simulationservice.payload.SimulationParams2;
import com.test.simulationservice.services.ISimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/simulation/")
@RestController
@RequiredArgsConstructor
public class SimulationController {

    private final ISimulationService simulationService;

    @PostMapping("/update-params")
    public SimulationParams updateSimulationParams(@RequestBody SimulationParams2 params) {
        return simulationService.updateSimulationParams(params);
    }

    @GetMapping("/read-params")
    public SimulationParams readSimulationParams() {
        return simulationService.readSimulationParams();
    }

    @GetMapping("/read-state")
    public Boolean readCustomerSimulationState() {
        return simulationService.readSimulationState();
    }

    @GetMapping("/start")
    public SimulationParams startSimulation() {
        return simulationService.startSimulation();
    }

    @GetMapping("/stop")
    public SimulationParams stopSimulation() {
        return simulationService.stopSimulation();
    }


}
