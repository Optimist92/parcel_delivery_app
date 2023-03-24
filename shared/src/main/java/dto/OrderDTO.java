package dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import enums.EOrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private UUID id;

    private UUID publicId;

    private String title;

    private String content;

    private Date creationDate;

    private EOrderStatus status;

    //private String courierName;

    //private String customerName;

    private String locationFrom;

    private String locationTo;

    private BigDecimal cost;

    //private User customer;

    //private User courier;

}