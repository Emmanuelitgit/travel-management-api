package travel_management_system.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception {

    Throwable throwable;
    String message;
    HttpStatus httpStatus;

    public Exception(Throwable throwable, String message, HttpStatus httpStatus) {
        this.throwable = throwable;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
