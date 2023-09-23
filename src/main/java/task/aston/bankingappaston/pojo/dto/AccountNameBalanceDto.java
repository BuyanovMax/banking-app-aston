package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Contains actual name and balance of account. Sent by server")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNameBalanceDto {
    private String name;
    private long balance;
}
