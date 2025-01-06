package saifular.testcase.backend.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception){
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                exception.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );
        return new ResponseEntity<>(apiExceptionResponse, conflict);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception){
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                exception.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );
        return new ResponseEntity<>(apiExceptionResponse, conflict);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundExeption(UserNotFoundException exception){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                exception.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );
        return new ResponseEntity<>(apiExceptionResponse, notFound);
    }

}
