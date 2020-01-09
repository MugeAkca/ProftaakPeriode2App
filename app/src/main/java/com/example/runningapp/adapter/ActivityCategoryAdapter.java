package com.example.runningapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.ActivityCategory;
import com.example.runningapp.database.entity.Category;

public class ActivityCategoryAdapter extends ListAdapter<ActivityCategory, ActivityCategoryAdapter.ActivityCategoryHolder> {

    private static final DiffUtil.ItemCallback<ActivityCategory> DIFF_CALLBACK = new DiffUtil.ItemCallback<ActivityCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityCategory oldItem, @NonNull ActivityCategory newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActivityCategory oldItem, @NonNull ActivityCategory newItem) {
            return oldItem.getActivity_type_id() == newItem.getActivity_type_id() &&
                    oldItem.getStart_date().equals(newItem.getStart_date()) &&
                    oldItem.getEnd_date().equals(newItem.getEnd_date());
        }
    };

    //private List<ActivityCategory> activityCategorys = new ArrayList<>();
    private OnItemClickListener listener;

    public ActivityCategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ActivityCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ActivityCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityCategoryHolder holder, int position) {
        ActivityCategory currentActivityCategory = getItem(position);
        holder.textViewActivityCategoryType.setText(String.valueOf(currentActivityCategory.getActivity_type_id()));
        holder.textViewTimeActivityCategory.setText(String.valueOf(currentActivityCategory.getStart_date()));
        holder.textViewSpeedActivityCategory.setText(String.valueOf(currentActivityCategory.getEnd_date()));
    }



    public ActivityCategory getActivityCategoryAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    public interface OnItemClickListener {
        void onItemClick(ActivityCategory activityCategory);
    }

    class ActivityCategoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewActivityCategoryType;
        private TextView textViewTimeActivityCategory;
        private TextView textViewSpeedActivityCategory;

        public ActivityCategoryHolder(@NonNull View itemView) {
            super(itemView);

            textViewActivityCategoryType = itemView.findViewById(R.id.text_view_activity_type);
            textViewTimeActivityCategory = itemView.findViewById(R.id.start_date);
            textViewSpeedActivityCategory = itemView.findViewById(R.id.end_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
}
