package com.example.miniproject.Model;

public class UserModel {

    private String userImage, userName, userEmail, userYear, userDepartment, userJob, userType, userDescription, userBImage, userColor, userID;

    public UserModel(){

    }

    public UserModel(String userImage, String userName, String userEmail, String userYear, String userDepartment, String userJob, String userType, String userDescription, String userBImage, String userColor, String userID) {
        this.userImage = userImage;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userYear = userYear;
        this.userDepartment = userDepartment;
        this.userJob = userJob;
        this.userType = userType;
        this.userDescription = userDescription;
        this.userBImage = userBImage;
        this.userColor = userColor;
        this.userID = userID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserYear() {
        return userYear;
    }

    public void setUserYear(String userYear) {
        this.userYear = userYear;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserBImage() {
        return userBImage;
    }

    public void setUserBImage(String userBImage) {
        this.userBImage = userBImage;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
