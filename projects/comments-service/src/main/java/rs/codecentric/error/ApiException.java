package rs.codecentric.error;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable reason) {
        super(message, reason);
    }
}
