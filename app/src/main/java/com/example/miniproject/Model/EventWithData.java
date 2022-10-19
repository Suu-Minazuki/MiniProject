package com.example.miniproject.Model;

import java.util.Comparator;

public class EventWithData {

    private String event_name;
    private String event_description;
    private String event_venue;
    private String event_image;
    private String event_day;
    private String event_month;
    private String event_Year;
    private String event_add;
    private String org_Image;
    private String org_name;
    private String org_dept;
    private String org_type;
    private String org_email;
    private String event_time;
    private String event_ID;

    public EventWithData() {

    }

    public EventWithData(String event_name, String event_description, String event_venue, String event_image, String event_day, String event_month, String event_Year, String event_add, String org_Image, String org_name, String org_dept, String org_type, String org_email, String event_time, String event_ID) {
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_venue = event_venue;
        this.event_image = event_image;
        this.event_day = event_day;
        this.event_month = event_month;
        this.event_Year = event_Year;
        this.event_add = event_add;
        this.org_Image = org_Image;
        this.org_name = org_name;
        this.org_dept = org_dept;
        this.org_type = org_type;
        this.org_email = org_email;
        this.event_time = event_time;
        this.event_ID = event_ID;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_day() {
        return event_day;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public String getEvent_month() {
        return event_month;
    }

    public void setEvent_month(String event_month) {
        this.event_month = event_month;
    }

    public String getEvent_Year() {
        return event_Year;
    }

    public void setEvent_Year(String event_Year) {
        this.event_Year = event_Year;
    }

    public String getEvent_add() {
        return event_add;
    }

    public void setEvent_add(String event_add) {
        this.event_add = event_add;
    }

    public String getOrg_Image() {
        return org_Image;
    }

    public void setOrg_Image(String org_Image) {
        this.org_Image = org_Image;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_dept() {
        return org_dept;
    }

    public void setOrg_dept(String org_dept) {
        this.org_dept = org_dept;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getOrg_email() {
        return org_email;
    }

    public void setOrg_email(String org_email) {
        this.org_email = org_email;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(String event_ID) {
        this.event_ID = event_ID;
    }
}
