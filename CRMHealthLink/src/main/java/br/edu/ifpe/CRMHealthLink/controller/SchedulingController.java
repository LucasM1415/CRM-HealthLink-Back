package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.AssociateDoctorDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingDoctorResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.SchedulingMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/calendario")
@Tag(name = "calendario API", description = "API para gestão do calendário dos médicos")
public class SchedulingController {
    @Autowired
    private final SchedulingService schedulingService;


    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento para um médico.")
    public ResponseEntity<SchedulingResponseDTO> create(@RequestBody @Valid SchedulingCreateDTO schedulingCreateDTO) {
        Scheduling schedulingtemp = schedulingService.findByHomeDateTimeAndEndDateTimeAndScheduling(
                schedulingCreateDTO.getDate(),
                schedulingCreateDTO.getHomeTime(),
                schedulingCreateDTO.getSpecialityType()
        );

        if (schedulingtemp != null || schedulingCreateDTO.getEndTime().isBefore(schedulingCreateDTO.getHomeTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Scheduling scheduling = SchedulingMapper.toScheduling(schedulingCreateDTO);
        Scheduling schedulingSave = schedulingService.save(scheduling);

        SchedulingResponseDTO responseDTO = new SchedulingResponseDTO();
        responseDTO.setDate(schedulingSave.getDate());
        responseDTO.setHomeTime(schedulingSave.getHomeTime());
        responseDTO.setSpecialityType(schedulingSave.getSpecialityType());
        responseDTO.setEndTime(schedulingSave.getEndTime());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/savaList")
    public List<SchedulingResponseDTO> createListScheduling(@RequestBody @Valid List<SchedulingCreateDTO> schedulingCreateDTOList) {
        List<Scheduling> schedulings = SchedulingMapper.toDtoSchedulingCreateDTOs(schedulingCreateDTOList);
        List<Scheduling> schedulingList = schedulingService.saveAll(schedulings);
        List<SchedulingResponseDTO> responseDTOList = SchedulingMapper.toDtoSchedulings(schedulingList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTOList).getBody();

    }

    @GetMapping
    @Operation(summary = "Listar todos os agendamentos", description = "Retorna uma lista de todos os agendamentos.")
    public ResponseEntity<List<SchedulingResponseDTO>> getAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        List<SchedulingResponseDTO> schedulingResponseDTOS = SchedulingMapper.toDtoSchedulings(schedulings);
        return ResponseEntity.status(HttpStatus.OK).body(schedulingResponseDTOS);
    }

    @GetMapping("/specialty")
    @Operation(summary = "Listar agendamentos por especialidade, mês e ano",
            description = "Retorna uma lista de agendamentos filtrados por especialidade, mês e ano.")
    public ResponseEntity<List<SchedulingResponseDTO>> getBySpecialtyAndMonthYear(
            @Parameter(description = "Tipo de especialidade a ser filtrada") @RequestParam Speciality speciality,
            @Parameter(description = "Mês para filtrar os agendamentos (1-12)") @RequestParam int month,
            @Parameter(description = "Ano para filtrar os agendamentos") @RequestParam int year) {
        List<Scheduling> schedulings = schedulingService.getSchedulesBySpecialtyAndMonthYear(speciality, month, year);
        List<SchedulingResponseDTO> schedulingResponseDTOS = SchedulingMapper.toDtoSchedulings(schedulings);
        return ResponseEntity.status(HttpStatus.OK).body(schedulingResponseDTOS);
    }


    @PostMapping("/associateDoctor")
    public ResponseEntity<SchedulingDoctorResponseDTO> associateDoctor(@RequestBody @Valid AssociateDoctorDTO associateDoctorDTO){

        var scheduling = schedulingService.scheduleDoctor(associateDoctorDTO.getDate(),associateDoctorDTO.getHomeTime(),
                            associateDoctorDTO.getSpecialityType(),associateDoctorDTO.getCrm());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SchedulingMapper.toSchedulingDoctorResponseDTO(scheduling));
    }
    @GetMapping("/getAllTimeSlots")
    public ResponseEntity<Map<Speciality,Long>> getTimeSlots(){
        return ResponseEntity.ok(schedulingService.getSpecialityTimeSlots());
    }
}