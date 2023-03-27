package payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderPayload implements Serializable {

    private String content;


    private String cardNumber;

    @NonNull
    private String locationFrom;

    @NonNull
    private String locationTo;
}
