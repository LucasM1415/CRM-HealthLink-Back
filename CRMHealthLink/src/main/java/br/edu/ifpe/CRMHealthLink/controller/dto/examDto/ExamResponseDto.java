package br.edu.ifpe.CRMHealthLink.controller.dto.examDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponseDto {

    private LocalDateTime date;
    private String description;
    private String namePatient;
    private String nameDoctor;
    private String descriptionAppointment;
}
