package ca.dal.Maverick.user.entity;

import ca.dal.Maverick.tasks.model.TaskModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String emailID;

    @Column(nullable = false, length = 64)
    private String password;

    private String question;

    private String answer;
    @ManyToMany(mappedBy = "task_users")
    Set<TaskModel> tasks;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UserModel(String name, String emailID, String password, String question, String answer) {
        this.name = name;
        this.emailID = emailID;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public UserModel(){

    }

    public UserModel (String emailID, String password){
        this.emailID = emailID;
        this.password = password;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
