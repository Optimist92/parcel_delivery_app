package dto;

import enums.EUserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long publicId;
    private String username;
    private String email;
    private EUserRole role;
}
