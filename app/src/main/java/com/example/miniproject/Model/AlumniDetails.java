package com.example.miniproject.Model;

public class AlumniDetails {

    private String alumniName;
    private String alumniDepartment;
    private int alumniYear;
    private String alumniImage;

    public AlumniDetails(){

    }

    public AlumniDetails(String alumniName, String alumniDepartment, int alumniYear, String alumniImage) {
        this.alumniName = alumniName;
        this.alumniDepartment = alumniDepartment;
        this.alumniYear = alumniYear;
        this.alumniImage = alumniImage;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getAlumniDepartment() {
        return alumniDepartment;
    }

    public void setAlumniDepartment(String alumniDepartment) {
        this.alumniDepartment = alumniDepartment;
    }

    public int getAlumniYear() {
        return alumniYear;
    }

    public void setAlumniYear(int alumniYear) {
        this.alumniYear = alumniYear;
    }

    public String getAlumniImage() {
        return alumniImage;
    }

    public void setAlumniImage(String alumniImage) {
        this.alumniImage = alumniImage;
    }
}
