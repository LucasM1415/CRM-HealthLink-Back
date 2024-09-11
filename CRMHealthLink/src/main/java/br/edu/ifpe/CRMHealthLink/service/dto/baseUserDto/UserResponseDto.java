package br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String email;

    private AcessLevel acessLevel;
}