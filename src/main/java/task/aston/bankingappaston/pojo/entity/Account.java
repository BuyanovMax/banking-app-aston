package task.aston.bankingappaston.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "balance")
    private long balance;

    public void deposit(long currencyValue) {
        balance += currencyValue;
    }

    public void withdraw(long currencyValue) {
        balance -= currencyValue;
    }
}
