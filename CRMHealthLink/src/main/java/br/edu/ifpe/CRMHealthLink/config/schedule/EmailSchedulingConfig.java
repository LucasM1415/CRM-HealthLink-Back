package br.edu.ifpe.CRMHealthLink.config.schedule;

import br.edu.ifpe.CRMHealthLink.controller.dto.email.Email;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.service.EmailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class EmailSchedulingConfig {
    private static final long oneHour = 1000 * 60 * 60;
    private static final long fixed_delay = oneHour * 24;

    private IAppointmentRepository IAppointmentRepository;

    private EmailService emailService;

    public EmailSchedulingConfig(IAppointmentRepository IAppointmentRepository,
                                 EmailService emailService
                                 ) {
        this.IAppointmentRepository = IAppointmentRepository;
        this.emailService = emailService;
    }

    //@Scheduled(fixedDelay = fixed_delay)
    @Scheduled(cron = "0 0 0,6,12,18 * * *")
    @Scheduled(cron = "0 59 23 * * *")
    public void notifyTomorrowAppointment(){

        LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        LocalDateTime end = start.plusDays(1);
        List<Appointment>  appointments = IAppointmentRepository.findByDateBetweenAndNotifiedIsFalse(start,end);

        for(Appointment appointment : appointments){
            var email = new Email(appointment.getPatient().getEmail(),
                    "Your Appointment",
                    "YOU'RE RECEIVING THIS EMAIL BECAUSE...");
            //emailService.send(email);
            appointment.setNotified(true);
            System.out.println("Email sent to: "+email.to());
        }
        IAppointmentRepository.saveAll(appointments);
    }

}
