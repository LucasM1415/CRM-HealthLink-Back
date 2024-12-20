package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {


    @NotNull
    private String email_patient;

    @NotNull
    private String email_doctor;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime inicio;
    @NotNull
    private Speciality speciality;
    @NotNull
    private LocalTime fim;
    public Appointment toEntity(){
        return new Appointment(speciality, date, inicio,fim);
    }
}
