package exception;


public class JwtTokenMalformedException extends RuntimeException {
    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}
