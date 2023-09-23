package task.aston.bankingappaston.mapper;

import task.aston.bankingappaston.pojo.dto.*;
import task.aston.bankingappaston.pojo.entity.Account;
import org.mapstruct.MappingConstants;


@org.mapstruct.Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface Mapper {
    Account toAccount(NewAccountDto newAccountDto);
    AccountNameBalanceDto nameBalanceFromAccount(Account account);
    CreatedAccountDto createdFromAccount(Account account);
    WithdrawRequest fromTransferToWithdraw(TransferRequest transferRequest);
    DepositRequest fromTransferToDeposit(TransferRequest depositRequest);
}
