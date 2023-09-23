package task.aston.bankingappaston.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Used to create accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountDto {

    @Schema(description = "Must be 4 digits", example = "1234")
    @Pattern(regexp = "\\d{4}", message = "the pin must consist of 4 digits")
    private String pin;

    @Schema(description = "Must be unique", minLength = 3, maxLength = 50, example = "my_account")
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
}
