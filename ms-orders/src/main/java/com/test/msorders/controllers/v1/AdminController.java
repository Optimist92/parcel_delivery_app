package com.test.msorders.controllers.v1;

import com.test.msorders.services.IOrderService;
import com.test.msorders.services.mappers.IOrderMapper;
import dto.OrderDTO;
import dto.OrderStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IOrderService orderService;

    private final IOrderMapper orderMapper;


    @Operation(summary = "Gets all orders", tags = "admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)))
                    }),
    })
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        return ResponseEntity.ok().body(orderService.findAll().stream().map(orderMapper::entityToDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Change order status", tags = "admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status changed by publicId",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderStatusDTO.class)))
                    }),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "This status is not available for ordering")
    })
    @PostMapping("/orders/change_order_status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().body(orderMapper.entityToDto(orderService.updateOrderStatusByAdmin(dto)));
    }

    @Operation(summary = "Assign order to courier", tags = "admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order assigned to courier",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderStatusDTO.class)))
                    }),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Courier not available")
    })
    @PostMapping("/orders/assign")
    public ResponseEntity<OrderDTO> assignOrderToCourier(@RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().body(orderMapper.entityToDto(orderService.assignOrderToCourier(dto)));
    }
}
