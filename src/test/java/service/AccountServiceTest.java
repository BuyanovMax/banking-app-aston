package service;

import task.aston.bankingappaston.exceptions.AccountNotFoundException;
import task.aston.bankingappaston.exceptions.NameTakenException;
import task.aston.bankingappaston.exceptions.NotEnoughFundsException;
import task.aston.bankingappaston.exceptions.UnexpectedIdMatchingException;
import task.aston.bankingappaston.mapper.Mapper;
import task.aston.bankingappaston.pojo.dto.AccountNameBalanceDto;
import task.aston.bankingappaston.pojo.dto.AccountsPageDto;
import task.aston.bankingappaston.pojo.dto.TransferRequest;
import task.aston.bankingappaston.pojo.entity.Account;
import task.aston.bankingappaston.repository.AccountRepository;
import task.aston.bankingappaston.service.AccountService;
import task.aston.bankingappaston.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static data_preparation.PreparedData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    Mapper mapper;
    @Mock
    SecurityService securityService;

    @InjectMocks
    AccountService out;

    @Test
    void createAccount_success() {
        Account account = new Account();
        account.setId(1);
        account.setPin(NEW_ACCOUNT_DTO.getPin());
        account.setName(NEW_ACCOUNT_DTO.getName());
        when(accountRepository.existsByName(anyString()))
                .thenReturn(false);
        when(mapper.toAccount(NEW_ACCOUNT_DTO))
                .thenReturn(account);
        when(securityService.encodePin(account.getPin()))
                .thenReturn("encoded");
        when(accountRepository.save(account))
                .thenReturn(account);
        when(mapper.createdFromAccount(account))
                .thenReturn(CREATED_ACCOUNT_DTO);
        assertEquals(out.createAccount(NEW_ACCOUNT_DTO), CREATED_ACCOUNT_DTO);
    }

    @Test
    void getAccounts_success() {
        long totalCount = NAME_BALANCE_DTOS.size();
        int pageNumber = 0;
        int pageSize = 10;
        List<AccountNameBalanceDto> list = getPageOfNameBalanceDto(pageNumber, pageSize);
        AccountsPageDto result = new AccountsPageDto(totalCount, list);
        when(accountRepository.count())
                .thenReturn(totalCount);
        when(accountRepository.getPageOfNameBalanceDto(any()))
                .thenReturn(list);
        assertEquals(out.getAccounts(pageNumber, pageSize), result);
    }

    @Test
    void deposit_success() {
        AccountNameBalanceDto result =
                new AccountNameBalanceDto(ACCOUNT.getName(),
                        ACCOUNT.getBalance() + DEPOSIT_REQUEST.getCurrencyAmount());
        when(accountRepository.findById(ACCOUNT.getId()))
                .thenReturn(Optional.of(ACCOUNT));
        when(accountRepository.save(ACCOUNT))
                .thenReturn(ACCOUNT);
        when(mapper.nameBalanceFromAccount(ACCOUNT))
                .thenReturn(result);

        assertEquals(out.deposit(DEPOSIT_REQUEST), result);
    }

    @Test
    void withdraw_success() {
        AccountNameBalanceDto result =
                new AccountNameBalanceDto(ACCOUNT.getName(),
                        ACCOUNT.getBalance() - WITHDRAW_REQUEST.getCurrencyAmount());
        when(accountRepository.findById(ACCOUNT.getId()))
                .thenReturn(Optional.of(ACCOUNT));
        when(accountRepository.save(ACCOUNT))
                .thenReturn(ACCOUNT);
        when(mapper.nameBalanceFromAccount(ACCOUNT))
                .thenReturn(result);
        assertEquals(out.withdraw(WITHDRAW_REQUEST), result);
        verify(securityService, only()).verifyPin(anyString(), anyString());
    }

    @Test
    void not_found_exception() {
        when(accountRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> out.withdraw(WITHDRAW_REQUEST));
        assertThrows(AccountNotFoundException.class, () -> out.deposit(DEPOSIT_REQUEST));
    }

    @Test
    void name_already_taken_exception() {
        when(accountRepository.existsByName(anyString()))
                .thenReturn(true);
        assertThrows(NameTakenException.class, () -> out.createAccount(NEW_ACCOUNT_DTO));
    }

    @Test
    void not_enough_funds_exception() {
        Account poorAccount = new Account();
        poorAccount.setBalance(0);
        when(accountRepository.findById(poorAccount.getId()))
                .thenReturn(Optional.of(poorAccount));
        assertThrows(NotEnoughFundsException.class, () -> out.withdraw(WITHDRAW_REQUEST));
    }

    @Test
    void id_conflict_on_transfer_exception() {
        assertThrows(UnexpectedIdMatchingException.class,
                () -> out.transfer(new TransferRequest(1,1, "1234", 100)));
    }

}