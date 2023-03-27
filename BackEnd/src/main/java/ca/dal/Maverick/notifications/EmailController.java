package ca.dal.Maverick.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@Configuration
@EnableScheduling
@RestController
public class EmailController {

  @Autowired
  EmailService emailService;

  @Scheduled(cron = "0 0 6 ? * *")
  public void sendReminderEmails() {
    emailService.sendReminderEmails();
  }
}
