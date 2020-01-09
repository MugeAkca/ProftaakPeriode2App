package com.example.runningapp.database.entity;

public class ActivityCategory extends Activity {
    public ActivityCategory(String activity_type_id, String start_date, String end_date) {
        super(activity_type_id, start_date, end_date);
    }

    public ActivityCategory(int activity_type_id, String start_date, String end_date, String categoryName) {
        super(activity_type_id, start_date, end_date);
        this.categoryName = categoryName;
    }

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
