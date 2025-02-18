package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class MockEntities {

    private PasswordEncoder encoder;
    private IAppointmentRepository IAppointmentRepository;
    private IUserRepository IUserRepository;

    public MockEntities(PasswordEncoder passwordEncoder, IAppointmentRepository IAppointmentRepository, IUserRepository IUserRepository) {

        this.encoder = passwordEncoder;
        this.IAppointmentRepository = IAppointmentRepository;
        this.IUserRepository = IUserRepository;
    }

    private String getCPF(){
        StringBuilder cpf = new StringBuilder();
        Random random = new Random();

        for(int number=1; number<=11; number++){
            cpf.append(random.nextInt(10));
        }
        return cpf.toString();
    }
    private String getEmail(){
        StringBuilder email = new StringBuilder();
        Random random = new Random();

        for(int letter=1; letter<=5; letter++){
            email.append(random.nextInt(10));
        }
        email.append("@email.com");
        return email.toString();
    }


    public User getUser(){
        var user = new User();
        String email =getEmail();
        user.setEmail(email);
        user.setName(email.replace("@email.com",""));
        user.setPassword(encoder.encode("123"));
        user.setBirthDate(LocalDate.now());
        user.setCpf(getCPF());

        return user;
    }

    public Patient getPatient(){
        return  new Patient(getUser());
    }

    public Employee getManager(){
        Employee employee = new Employee(getUser());
        employee.setAcessLevel(AcessLevel.MANAGER);
        return employee;
    }


   /* public Doctor getDoctor(){
        Doctor doctor = new Doctor(getUser());
        doctor.setCRM("CRM-here");
        doctor.setSpeciality(Speciality.CARDIOLOGISTA);
        return doctor;
    }
    public Appointment getAppointment(){
        var ap = new Appointment();
        ap.setDate(LocalDateTime.now().plusDays(1).plusHours(2));
        ap.setEmployee(getManager());
        ap.setDoctor(getDoctor());
        ap.setPatient(getPatient());
        return ap;
    }

    public Appointment saveAppointment(){
        var appointment = getAppointment();
        var patient = appointment.getPatient();
        var employee = appointment.getEmployee();
        var doctor = appointment.getDoctor();
        saveUsers(List.of(patient,employee,doctor));

        IAppointmentRepository.save(appointment);
        return appointment;
    }
    public void saveAppointment(Appointment appointment){
        var patient = appointment.getPatient();
        var employee = appointment.getEmployee();
        var doctor = appointment.getDoctor();
        saveUsers(List.of(patient,employee,doctor));

        IAppointmentRepository.save(appointment);
    }
    private void saveUsers(List<User> users){
        IUserRepository.saveAll(users);
    }*/
}
