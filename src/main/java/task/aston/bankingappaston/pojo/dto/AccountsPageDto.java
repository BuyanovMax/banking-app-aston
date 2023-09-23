package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(description = "Contains total account number and a list of account name and balance")

public record AccountsPageDto(
        @Schema(description = "Used to make a deposit")
        long totalCount,
        @Schema(description = "Used to make a deposit")
        List<AccountNameBalanceDto> accounts) {
}
