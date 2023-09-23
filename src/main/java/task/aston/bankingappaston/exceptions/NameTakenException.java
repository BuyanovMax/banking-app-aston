package task.aston.bankingappaston.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class NameTakenException extends RuntimeException {
    public NameTakenException(String name) {
        super("This name is already taken: " + name);
    }
}
