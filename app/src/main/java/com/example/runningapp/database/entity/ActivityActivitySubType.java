package com.example.runningapp.database.entity;

public class ActivityActivitySubType extends Activity {

    private String name;

    public ActivityActivitySubType(int activity_type_id, String start_date, String end_date, String name) {
        super(activity_type_id, start_date, end_date);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
