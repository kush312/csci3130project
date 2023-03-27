package ca.dal.Maverick.user.entity;

public class UserRequestModel {
  private String name;
  private String password;
  private String emailID;


  public UserRequestModel(String emailID, String name, String password) {
    this.name = name;
    this.password = password;
    this.emailID = emailID;
  }
  public UserRequestModel(){

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmailID() {
    return emailID;
  }

  public void setEmailID(String emailID) {
    this.emailID = emailID;
  }
}
