package dto;

import lombok.Data;

@Data
public class CourierDTO {
    private Long workNumber;

    private Long userPublicId;

    private Boolean isAvailable;
}
