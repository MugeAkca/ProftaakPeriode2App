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
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.Category;

public class ActivityAdapter extends ListAdapter<Activity, ActivityAdapter.ActivityHolder> {

    private static final DiffUtil.ItemCallback<Activity> DIFF_CALLBACK = new DiffUtil.ItemCallback<Activity>() {
        @Override
        public boolean areItemsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
            return oldItem.getActivity_type_id() == newItem.getActivity_type_id() &&
                    oldItem.getStart_date().equals(newItem.getStart_date()) &&
                    oldItem.getEnd_date().equals(newItem.getEnd_date());
        }
    };

    //private List<Activity> activitys = new ArrayList<>();
    private OnItemClickListener listener;

    public ActivityAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ActivityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        Activity currentActivity = getItem(position);
        holder.textViewActivityType.setText(String.valueOf(currentActivity.getActivity_type_id()));
        holder.textViewTimeActivity.setText(String.valueOf(currentActivity.getStart_date()));
        holder.textViewSpeedActivity.setText(String.valueOf(currentActivity.getEnd_date()));
    }



    public Activity getActivityAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Activity activity);
    }

    class ActivityHolder extends RecyclerView.ViewHolder {
        private TextView textViewActivityType;
        private TextView textViewTimeActivity;
        private TextView textViewSpeedActivity;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);

            textViewActivityType = itemView.findViewById(R.id.text_view_activity_type);
            textViewTimeActivity = itemView.findViewById(R.id.start_date);
            textViewSpeedActivity = itemView.findViewById(R.id.end_date);

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
