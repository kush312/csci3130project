package ca.dal.Maverick.notifications;

import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.tasks.service.TasksService;
import ca.dal.Maverick.user.entity.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {EmailService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailServiceTest {
  private final String email1 = "t1@gmail.com";
  private final String userName = "test";
  private final String password = "testPass";
  private final String question = "question";
  private final String answer = "answer";
  private final String subject1 = "Test subject1";
  private final String description1 = "Test description 1";
  private final EmailModel emailModel = new EmailModel(email1, subject1, description1);
  private final TaskModel taskModelTomorrow = new TaskModel();
  private final UserModel userModel = new UserModel(userName,email1,password, question, answer);

  @MockBean
  TasksService tasksService;

  @Autowired
  EmailService emailService;

  @MockBean
  JavaMailSender mockEmailSender;

  @Test
  public void testSimpleMessage() {
    ArgumentCaptor<SimpleMailMessage> valueCapture = ArgumentCaptor.forClass(SimpleMailMessage.class);
    doNothing().when(mockEmailSender).send(valueCapture.capture());

    emailService.sendSimpleMessage(emailModel);

    assertEquals(description1, valueCapture.getValue().getText());
    assertEquals(subject1, valueCapture.getValue().getSubject());
  }

  @Test
  public void testSendReminderEmails() {
    Set<UserModel> taskUsers = new HashSet<>();
    taskUsers.add(userModel);

    taskModelTomorrow.setTask_users(taskUsers);
    List<TaskModel> tasksDueTomorrow = new ArrayList<>();
    tasksDueTomorrow.add(taskModelTomorrow);

    when(tasksService.getTasksDueBy(any())).thenReturn(tasksDueTomorrow);

    ArgumentCaptor<SimpleMailMessage> valueCapture = ArgumentCaptor.forClass(SimpleMailMessage.class);
    doNothing().when(mockEmailSender).send(valueCapture.capture());

    emailService.sendReminderEmails();

    String[] emailsTo = {email1};

    assertArrayEquals(valueCapture.getValue().getTo(), emailsTo);
  }
}
