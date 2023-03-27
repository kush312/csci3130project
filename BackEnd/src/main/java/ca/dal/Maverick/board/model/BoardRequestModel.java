package ca.dal.Maverick.board.model;

public class BoardRequestModel {
  private String board_name;
  private String workspaceID;
  private String description;

  public BoardRequestModel(String board_name, String description, String workspaceID) {
    this.board_name = board_name;
    this.description = description;
    this.workspaceID = workspaceID;
  }

  public BoardRequestModel() { }

  public String getBoard_name() {
    return board_name;
  }

  public void setBoard_name(String board_name) {
    this.board_name = board_name;
  }

  public String getWorkspaceID() {
    return workspaceID;
  }

  public void setWorkspaceID(String workspaceID) {
    this.workspaceID = workspaceID;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
