package com.test.mscouriers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

public class CourierController {

    /*@Operation(summary = "Gets all couriers", tags = "admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Couriers found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Courier.class)))
                    }),
    })
    @GetMapping("/couriers")
    public ResponseEntity<List<CourierDTO>> findAllCouriers() {
        return ResponseEntity.ok().body(userService.findUsersByRole(EUserRole.ROLE_COURIER).stream().map(user -> courierMapper.entityToDto((Courier) user)).collect(Collectors.toList()));
    }*/
}
