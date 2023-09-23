package task.aston.bankingappaston.service;

import task.aston.bankingappaston.exceptions.AccountNotFoundException;
import task.aston.bankingappaston.exceptions.NameTakenException;
import task.aston.bankingappaston.exceptions.NotEnoughFundsException;
import task.aston.bankingappaston.exceptions.UnexpectedIdMatchingException;
import task.aston.bankingappaston.mapper.Mapper;
import task.aston.bankingappaston.pojo.entity.Account;
import task.aston.bankingappaston.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.aston.bankingappaston.pojo.dto.*;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final Mapper mapper;
    private final SecurityService securityService;

    @Transactional
    public CreatedAccountDto createAccount(NewAccountDto newAccountDto) {
        String name = newAccountDto.getName();
        if (accountRepository.existsByName(name)) {
            throw new NameTakenException(name);
        }
        String pin = newAccountDto.getPin();
        newAccountDto.setPin(securityService.encodePin(pin));
        Account account = mapper.toAccount(newAccountDto);
        accountRepository.save(account);
        return mapper.createdFromAccount(account);
    }

    @Transactional(readOnly = true)
    public AccountsPageDto getAccounts(int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        long totalAmount = accountRepository.count();
        List<AccountNameBalanceDto> list = accountRepository.getPageOfNameBalanceDto(page);
        return new AccountsPageDto(totalAmount, list);
    }

    @Transactional
    public AccountNameBalanceDto deposit(DepositRequest depositRequest) {
        long toAccountId = depositRequest.getToAccountId();
        Account account = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException(depositRequest.getToAccountId()));
        account.deposit(depositRequest.getCurrencyAmount());
        accountRepository.save(account);
        return mapper.nameBalanceFromAccount(account);
    }

    @Transactional
    public AccountNameBalanceDto withdraw(WithdrawRequest withdrawRequest) {
        Account account = accountRepository.findById(withdrawRequest.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException(withdrawRequest.getFromAccountId()));
        securityService.verifyPin(withdrawRequest.getPin(), account.getPin());
        long currencyAmount = withdrawRequest.getCurrencyAmount();
        long balance = account.getBalance();
        if (balance < currencyAmount) {
            throw new NotEnoughFundsException(balance, currencyAmount);
        }
        account.withdraw(currencyAmount);
        accountRepository.save(account);
        return mapper.nameBalanceFromAccount(account);
    }

    @Transactional
    public void transfer(TransferRequest transferRequest) {
        if (transferRequest.getFromAccountId() == transferRequest.getToAccountId()) {
            throw new UnexpectedIdMatchingException("Source and goal accounts can't match");
        }
        withdraw(mapper.fromTransferToWithdraw(transferRequest));
        deposit(mapper.fromTransferToDeposit(transferRequest));
    }
}
