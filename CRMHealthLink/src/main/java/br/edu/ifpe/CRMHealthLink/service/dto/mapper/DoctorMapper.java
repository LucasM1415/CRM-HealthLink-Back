package br.edu.ifpe.CRMHealthLink.service.dto.mapper;

import br.edu.ifpe.CRMHealthLink.service.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Doctor toDoctorEntity(DoctorCreateDto createDto) {
        return modelMapper.map(createDto, Doctor.class);
    }

    public static DoctorResponseDto toDtoDoctor(Doctor doctor) {
        return modelMapper.map(doctor, DoctorResponseDto.class);
    }

    public static List<DoctorResponseDto> toDtoDoctors(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorMapper::toDtoDoctor)
                .collect(Collectors.toList());
    }
}