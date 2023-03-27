package ca.dal.Maverick.board.model;

import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.utils.UUIDUtils;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "boards")
public class BoardModel {

  @Id
  private String id;
  private String workspaceID;
  private String board_name;
  private String description;
  @OneToMany
  @JoinColumn(name = "boardID", referencedColumnName = "id")
  private List<TaskModel> tasks;
  private OffsetDateTime dateCreated;
  private OffsetDateTime lastUpdated;

  public BoardModel(String name, String description, String workspaceID) {
    this.id = UUIDUtils.generateUUID();
    this.workspaceID = workspaceID;
    this.board_name = name;
    this.description = description;
    this.dateCreated = OffsetDateTime.now();
    this.lastUpdated = dateCreated;
  }

  public static BoardModel create(BoardRequestModel boardRequest) {
    return new BoardModel(boardRequest.getBoard_name(), boardRequest.getDescription(), boardRequest.getWorkspaceID());
  }

  public BoardModel() { }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWorkspaceID() {
    return workspaceID;
  }

  public void setWorkspaceID(String workspaceID) {
    this.workspaceID = workspaceID;
  }

  public String getBoard_name() {
    return board_name;
  }

  public void setBoard_name(String board_name) {
    this.board_name = board_name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OffsetDateTime getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(OffsetDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public OffsetDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(OffsetDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
}
