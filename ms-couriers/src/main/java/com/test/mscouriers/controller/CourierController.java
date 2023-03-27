package com.test.mscouriers.controller;

import com.test.mscouriers.mapper.CourierMapper;
import com.test.mscouriers.service.ICourierService;
import dto.CourierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final ICourierService courierService;

    private final CourierMapper courierMapper;


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
    })*/
    @GetMapping()
    public ResponseEntity<List<CourierDTO>> findAllCouriers() {
        return ResponseEntity.ok().body(courierService.findAll().stream().map(courierMapper::entityToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{public_id}")
    public ResponseEntity<CourierDTO> findByPublicId(@PathVariable Long public_id) {
        return ResponseEntity.ok().body(courierMapper.entityToDto(courierService.findByPublicId(public_id)));
    }

    @PostMapping()
    public ResponseEntity<CourierDTO> createCourier(@RequestBody CourierDTO courierDTO) {
        return ResponseEntity.ok().body(courierMapper.entityToDto(courierService.createCourier(courierMapper.dtoToEntity(courierDTO))));
    }
}