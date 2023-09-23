package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Used to transfer currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class
TransferRequest {

    @Schema(description = "Must be positive", example = "1")
    @Positive
    private long fromAccountId;

    @Schema(description = "Must be positive", example = "2")
    @Positive
    private long toAccountId;

    @Schema(description = "Must be 4 digits", example = "1234")
    @Pattern(regexp = "\\d{4}", message = "the pin must consist of 4 digits")
    private String pin;

    @Schema(description = "Must be positive. Can't exceed the current balance of source account", example = "50.00")
    @Positive
    private long currencyAmount;
}
