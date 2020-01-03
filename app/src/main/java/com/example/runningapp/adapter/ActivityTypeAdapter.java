package com.example.runningapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.ActivityType;

public class ActivityTypeAdapter extends ListAdapter<ActivityType, ActivityTypeAdapter.ActivityTypeHolder> {

    private static final DiffUtil.ItemCallback<ActivityType> DIFF_CALLBACK = new DiffUtil.ItemCallback<ActivityType>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityType oldItem, @NonNull ActivityType newItem) {
            return oldItem.getType_id() == newItem.getType_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActivityType oldItem, @NonNull ActivityType newItem) {
            return oldItem.getType_id() == newItem.getType_id() &&
                    oldItem.getName().equals(newItem.getName());
        }
    };

    //private List<ActivityType> activityTypes = new ArrayList<>();
    private OnItemClickListener listener;

    public ActivityTypeAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ActivityTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_type_item, parent, false);
        return new ActivityTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityTypeHolder holder, int position) {
        ActivityType currentActivityType = getItem(position);
        holder.textViewActivityTypeName.setText(String.valueOf(currentActivityType.getName()));
    }

    public ActivityType getActivityTypeAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ActivityType activityType);
    }

    class ActivityTypeHolder extends RecyclerView.ViewHolder {
        private TextView textViewActivityTypeName;

        public ActivityTypeHolder(@NonNull View itemView) {
            super(itemView);
            textViewActivityTypeName = itemView.findViewById(R.id.text_view_activity_type_name);

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
