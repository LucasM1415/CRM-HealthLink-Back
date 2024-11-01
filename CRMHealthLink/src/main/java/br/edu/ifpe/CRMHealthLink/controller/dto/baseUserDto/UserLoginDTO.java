package br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserLoginDTO {
    @NotBlank
    String email;

    @NotBlank
    String password;
}
