package task.aston.bankingappaston.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class UnexpectedIdMatchingException extends RuntimeException {
    public UnexpectedIdMatchingException(String message) {
        super(message);
    }
}
