//package com.example.runningapp.adapter;
//
//import android.content.Context;
//import android.widget.ArrayAdapter;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.runningapp.database.entity.ActivityType;
//
//import java.util.List;
//
//public class ActivityTypeSpinnerAdapter extends ArrayAdapter<ActivityType> {
//
//    private final List<ActivityType> activityTypesSpinner;
//
//    public ActivityTypeSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ActivityType> objects) {
//        super(context, resource, objects);
//        this.activityTypesSpinner = objects;
//    }
//
//    @Override
//    public int getCount(){return super.getCount() + 1;}
//
//    @Nullable
//    @Override
//    public ActivityType getItem(int position) {
//        if(position == 0){
//            ActivityType activityType = new ActivityType();
//            activityType.setType_id(0);
//            activityType.setName("Create new category");
//            return activityType;
//        }
//        return super.getItem(position - 1);
//    }
//
//    public int getActivityTypePosition(@Nullable Long activityTypeId) {
//        if (activityTypeId != null) {
//            for (int i = 0; i < activityTypesSpinner.size(); i++) {
//                if (activityTypeId == activityTypesSpinner.get(i).getType_id()) {
//                    return i + 1;
//                }
//            }
//        }
//        return -1;
//    }
//}
//
