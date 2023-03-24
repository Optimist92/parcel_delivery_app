package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ViolationResponse {

    private final LocalDateTime timestamp;
    private final String statusCode;
    private final String error;
    private final String message;
}
