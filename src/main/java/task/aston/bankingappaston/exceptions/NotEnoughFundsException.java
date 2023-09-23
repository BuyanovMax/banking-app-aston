package task.aston.bankingappaston.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(long balance, long currencyAmount) {
        super("Current balance is: " + balance + ". Can't withdraw " + currencyAmount);
    }
}
