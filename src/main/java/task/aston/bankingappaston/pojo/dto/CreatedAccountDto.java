package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Contains name and id of created account. Sent by server")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAccountDto {
    private String name;
    private long id;
}
