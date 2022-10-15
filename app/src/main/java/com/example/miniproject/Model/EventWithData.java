package com.example.miniproject.Model;

import java.util.Comparator;

public class EventWithData {

    private String event_name;
    private String event_description;
    private String event_venue;
    private String event_image;
    private String event_date;
    private String org_Image;
    private String org_name;
    private String org_dept;
    private String org_type;


    public EventWithData() {

    }

    public EventWithData(String event_name, String event_description, String event_venue, String event_image, String event_date, String org_Image, String org_name, String org_dept, String org_type) {
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_venue = event_venue;
        this.event_image = event_image;
        this.event_date = event_date;
        this.org_Image = org_Image;
        this.org_name = org_name;
        this.org_dept = org_dept;
        this.org_type = org_type;
    }

    public static Comparator<EventWithData> eventByDate = new Comparator<EventWithData>() {
        @Override
        public int compare(EventWithData e1, EventWithData e2) {
            return e1.getEvent_date().compareTo(e2.getEvent_date());
        }
    };

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

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
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
}
