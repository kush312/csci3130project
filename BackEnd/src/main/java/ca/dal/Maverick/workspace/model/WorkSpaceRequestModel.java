package ca.dal.Maverick.workspace.model;

public class WorkSpaceRequestModel {
  private String name;
  private String description;
  private Long userID;

  public WorkSpaceRequestModel(String name, String description, Long userID) {
    this.name = name;
    this.description = description;
    this.userID = userID;
  }

  public Long getUserID() {
    return userID;
  }

  public void setUserID(Long userID) {
    this.userID = userID;
  }

  public WorkSpaceRequestModel() { }

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

  public boolean isEmpty(){
    return (this.name.isEmpty() || this.description.isEmpty());
  }
}
