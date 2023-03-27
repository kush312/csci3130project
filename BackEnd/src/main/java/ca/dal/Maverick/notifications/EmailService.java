package ca.dal.Maverick.notifications;

import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.tasks.service.TasksService;
import ca.dal.Maverick.user.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  @Autowired
  private TasksService tasksService;

  public void sendSimpleMessage(EmailModel emailModel) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom("test@maverick.com");
    message.setTo(emailModel.getTo());
    message.setSubject(emailModel.getSubject());
    message.setText(emailModel.getText());
    emailSender.send(message);
  }

  public void sendReminderEmails() {
    OffsetDateTime tomorrow = OffsetDateTime.of(LocalDate.now(), LocalTime.MAX, OffsetDateTime.now().getOffset()).plusDays(1);
    List<TaskModel> tasksDueTomorrow = tasksService.getTasksDueBy(tomorrow);
    HashMap<UserModel, List<TaskModel>> userTaskMap = new HashMap<>();
    ArrayList<UserModel> usersToEmail = new ArrayList<>();
    String subject = "Maverick: Tasks due tomorrow";

    for (TaskModel task : tasksDueTomorrow) {
      Set<UserModel> users = task.getTask_users();

      for (UserModel user : users) {
        if (userTaskMap.containsKey(user)) {
          userTaskMap.get(user).add(task);
        } else {
          ArrayList<TaskModel> tasksForUser = new ArrayList<>();
          tasksForUser.add(task);
          userTaskMap.put(user, tasksForUser);
        }

        usersToEmail.add(user);
      }
    }

    for (UserModel user : usersToEmail) {
      String body = generateBody(userTaskMap, user);

      EmailModel email = new EmailModel(user.getEmailID(), subject, body);

      sendSimpleMessage(email);
    }
  }

  private String generateBody(HashMap<UserModel, List<TaskModel>> userTaskMap, UserModel user) {
    List<TaskModel> tasksToDo = userTaskMap.get(user);
    StringBuilder body = new StringBuilder();

    body.append("Hi ").append(user.getName()).append(":\n\n");
    body.append("Here are your tasks due tomorrow:\n");

    for (TaskModel task : tasksToDo) {
      body.append(task.getTaskName()).append("\n\t");
      body.append(task.getTaskDescription()).append("\n");
    }
    return body.toString();
  }
}
