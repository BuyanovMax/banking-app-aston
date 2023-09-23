package task.aston.bankingappaston.repository;
import task.aston.bankingappaston.pojo.dto.AccountNameBalanceDto;
import task.aston.bankingappaston.pojo.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {
    boolean existsByName(String name);

    /**
     * for immediately pulling out the required fields without mapping
     */
    @Query("select new task.aston.bankingappaston.pojo.dto.AccountNameBalanceDto(name, balance) from Account")
    List<AccountNameBalanceDto> getPageOfNameBalanceDto(Pageable page);
}
