package saifular.testcase.backend.ExceptionHandler;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
