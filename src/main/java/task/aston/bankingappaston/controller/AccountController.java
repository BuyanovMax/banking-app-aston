package task.aston.bankingappaston.controller;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import task.aston.bankingappaston.pojo.dto.*;
import task.aston.bankingappaston.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/account")
@Tag(name = "Account", description = "The Account API")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Create new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input / bad request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Name is already taken", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(schema = @Schema(hidden = true)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CreatedAccountDto createAccount(@Valid @RequestBody NewAccountDto newAccountDto) {
        return accountService.createAccount(newAccountDto);
    }

    @Operation(summary = "Get page of account name and balance, total account amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page was sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public AccountsPageDto accountsPage(@PositiveOrZero @RequestParam(name = "pageNumber") int pageNumber,
                                        @Positive @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return accountService.getAccounts(pageNumber, pageSize);
    }

    @Operation(summary = "Withdraw from account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawn successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input / Bad request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Wrong pin", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping(path = "/withdraw")
    public AccountNameBalanceDto transfer(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        return accountService.withdraw(withdrawRequest);
    }

    @Operation(summary = "Deposit to account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input / Bad request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping(path = "/deposit")
    public AccountNameBalanceDto transfer(@Valid @RequestBody DepositRequest depositRequest) {
        return accountService.deposit(depositRequest);
    }

    @Operation(summary = "Transfer between two accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferred successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input / Bad request"),
            @ApiResponse(responseCode = "401", description = "Wrong pin"),
            @ApiResponse(responseCode = "404", description = "At least on account not found"),
            @ApiResponse(responseCode = "409", description = "Source and goal id are equal"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping(path = "/transfer")
    public void transfer(@Valid @RequestBody TransferRequest transferRequest) {
        accountService.transfer(transferRequest);
    }

}
