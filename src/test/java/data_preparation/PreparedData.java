package data_preparation;

import task.aston.bankingappaston.pojo.dto.*;
import task.aston.bankingappaston.pojo.entity.Account;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;


import java.util.List;
import java.util.stream.Stream;

public class PreparedData {
    private static final Faker faker = new Faker();

    public static final List<AccountNameBalanceDto> NAME_BALANCE_DTOS = getNameBalanceDtoList();

    public static final Account ACCOUNT = prepareAccount();
    public static final WithdrawRequest WITHDRAW_REQUEST = prepareWithdraw();
    public static final DepositRequest DEPOSIT_REQUEST = prepareDeposit();
    public static final NewAccountDto NEW_ACCOUNT_DTO = new NewAccountDto("5555", "name");
    public static final CreatedAccountDto CREATED_ACCOUNT_DTO = new CreatedAccountDto("name", 1);


    public static List<AccountNameBalanceDto> getPageOfNameBalanceDto(int pageNumber, int pageSize) {
        return NAME_BALANCE_DTOS.stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .toList();
    }
    private static List<AccountNameBalanceDto> getNameBalanceDtoList() {
        return Stream.generate(faker::name)
                .map(Name::fullName)
                .distinct()
                .limit(50)
                .map(name -> new AccountNameBalanceDto(name, faker.random().nextInt(1000, 10000)))
                .toList();
    }
    private static Account prepareAccount() {
        Account account = new Account();
        account.setId(0);
        account.setPin("encoded");
        account.setName(NAME_BALANCE_DTOS.get(0).getName());
        account.setBalance(NAME_BALANCE_DTOS.get(0).getBalance());
        return account;
    }

    private static WithdrawRequest prepareWithdraw() {
        return new WithdrawRequest(ACCOUNT.getPin(), ACCOUNT.getId(), 100);
    }
    private static DepositRequest prepareDeposit() {
        return new DepositRequest(ACCOUNT.getId(), 100);
    }

}
