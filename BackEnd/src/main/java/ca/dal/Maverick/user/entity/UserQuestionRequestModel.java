package ca.dal.Maverick.user.entity;

public class UserQuestionRequestModel {
    private String emailID;
    private String question;
    private String answer;

    public UserQuestionRequestModel(String emailID, String question, String answer) {
        this.emailID = emailID;
        this.question = question;
        this.answer = answer;
    }

    public UserQuestionRequestModel(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

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
}
