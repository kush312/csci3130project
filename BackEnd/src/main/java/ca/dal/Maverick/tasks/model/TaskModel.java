package ca.dal.Maverick.tasks.model;

import ca.dal.Maverick.user.entity.UserModel;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private String taskDescription;

    @ManyToMany
    @JoinTable(name = "tasks_users",
        joinColumns = {@JoinColumn(name="id")},
        inverseJoinColumns = {@JoinColumn(name = "userID")})
    private Set<UserModel> task_users;

    private String boardID;

    private String status;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dueDate;

    public TaskModel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskTitle) {
        this.taskName = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public Set<UserModel> getTask_users() {
        return task_users;
    }

    public void setTask_users(Set<UserModel> task_users) {
        this.task_users = task_users;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
