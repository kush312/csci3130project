package ca.dal.Maverick.workspace.model;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.utils.UUIDUtils;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "workspace")
public class  WorkSpaceModel {
  @Id
  private String id;
  private String name;
  private String description;
  private OffsetDateTime dateCreated;
  private OffsetDateTime lastUpdated;
  @OneToMany
  @JoinColumn(name = "workspaceID", referencedColumnName = "id")
  private List<BoardModel> boards;
  @ManyToMany
  @JoinTable(name = "workspace_users",
    joinColumns = {@JoinColumn(name="workspaceID")},
    inverseJoinColumns = {@JoinColumn(name = "userID")})
  private Set<UserModel> users;



  public WorkSpaceModel(String name, String description, UserModel user) {
    this.id = UUIDUtils.generateUUID();
    this.name = name;
    this.description = description;
    this.dateCreated = OffsetDateTime.now();
    this.lastUpdated = dateCreated;
    this.users = new HashSet<>();
    this.users.add(user);
  }
  public WorkSpaceModel() {  }

  public static WorkSpaceModel create(WorkSpaceRequestModel requestModel, UserModel userModel) {
    return new WorkSpaceModel(requestModel.getName(), requestModel.getDescription(), userModel);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<BoardModel> getBoards() {
    return boards;
  }

  public void setBoards(List<BoardModel> boards) {
    this.boards = boards;
  }

  public Set<UserModel> getUsers() {
    return users;
  }

  public void setUsers(Set<UserModel> users) {
    this.users = users;
  }

  public OffsetDateTime getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(OffsetDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void addUser(UserModel userModel) {
    this.users.add(userModel);
  }

  public OffsetDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(OffsetDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

}
