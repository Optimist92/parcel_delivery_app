package dto;

import enums.EOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class OrderQuoteDTO {
    private UUID id;

    private UUID publicId;

    private String title;

    private String content;

    private Date creationDate;

    private String locationFrom;

    private String locationTo;

    private BigDecimal cost;

}
