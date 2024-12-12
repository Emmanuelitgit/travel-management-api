package travel_management_system.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> exceptionHandler(NotFoundException notFoundException){
        Exception exception = new Exception(
                notFoundException.getCause(),
                notFoundException.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity(exception, HttpStatus.NOT_FOUND);
    }
}
