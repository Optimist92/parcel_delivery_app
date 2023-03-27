package dto;

import enums.EOrderStatus;
import lombok.Data;


@Data
public class OrderStatusDTO {
    private String publicId;
    private EOrderStatus status;
    private Long courierId;
}
