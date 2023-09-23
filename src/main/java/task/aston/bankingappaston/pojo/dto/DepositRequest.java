package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Used to make a deposit")
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class DepositRequest {

    @Schema(description = "Must be positive", example = "1")
    @Positive
    private long toAccountId;

    @Schema(description = "Must be positive", example = "100")
    @Positive
    private long currencyAmount;
}
