package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingDoctorResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SchedulingMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Scheduling toScheduling(SchedulingCreateDTO createDTO){
        return modelMapper.map(createDTO, Scheduling.class);
    }
    public static SchedulingResponseDTO toSchedulingResponseDTO(Scheduling scheduling){
        return modelMapper.map(scheduling, SchedulingResponseDTO.class);
    }

    public static List<SchedulingResponseDTO> toDtoSchedulings(List<Scheduling> schedulingList){
        return schedulingList.stream()
                .map(SchedulingMapper::toSchedulingResponseDTO)
                .collect(Collectors.toList());
    }
    public static List<Scheduling> toDtoSchedulingCreateDTOs(List<SchedulingCreateDTO> schedulingList) {
        return schedulingList.stream()
                .map(SchedulingMapper::toScheduling)
                .collect(Collectors.toList());
    }

    public static SchedulingDoctorResponseDTO toSchedulingDoctorResponseDTO(Scheduling scheduling){

        return new SchedulingDoctorResponseDTO(scheduling.getDate(),scheduling.getHomeTime(),scheduling.getEndTime(),
                scheduling.getSpecialityType(),scheduling.getDoctor().getCRM(),scheduling.getDoctor().getName(),scheduling.getTipoAgendamento(),scheduling.getDoctor().getName(),scheduling.getDoctor().getEmail());

    }

}
