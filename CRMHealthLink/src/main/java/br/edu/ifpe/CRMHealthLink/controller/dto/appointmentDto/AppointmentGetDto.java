package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGetDto {

    @NotNull
    private String emailPatient;

    @NotNull
    private String emailDoctor;

    @NotNull
    private LocalDate date;
}
