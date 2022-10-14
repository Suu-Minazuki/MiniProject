package com.example.miniproject.Model;

public class UserModel {

    private String userImage, userName, userEmail, userYear, userDepartment, userJob, userType, Description;

    public UserModel(){

    }

    public UserModel(String userImage, String userName, String userEmail, String userYear, String userDepartment, String userJob, String userType, String description) {
        this.userImage = userImage;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userYear = userYear;
        this.userDepartment = userDepartment;
        this.userJob = userJob;
        this.userType = userType;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
