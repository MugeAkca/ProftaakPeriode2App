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
import com.example.runningapp.database.entity.ActivityActivitySubType;

public class ActivityAdapter extends ListAdapter<ActivityActivitySubType, ActivityAdapter.ActivitySubTypeHolder> {
    private View itemView;
    private ActivityActivitySubType currentActivityActivitySubType;

    private static final DiffUtil.ItemCallback<ActivityActivitySubType> DIFF_CALLBACK = new DiffUtil.ItemCallback<ActivityActivitySubType>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityActivitySubType oldItem, @NonNull ActivityActivitySubType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActivityActivitySubType oldItem, @NonNull ActivityActivitySubType newItem) {
            return oldItem.getActivity_type_id() == newItem.getActivity_type_id() &&
                    oldItem.getStart_date().equals(newItem.getStart_date()) &&
                    oldItem.getEnd_date().equals(newItem.getEnd_date());
        }
    };

    private OnItemClickListener listener;

    public ActivityAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ActivitySubTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ActivitySubTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitySubTypeHolder holder, int position) {
        currentActivityActivitySubType = getItem(position);
        holder.textViewActivitySubTypeType.setText("");
        holder.textViewTimeActivitySubType.setText(String.valueOf(currentActivityActivitySubType.getName()));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ActivityActivitySubType activityActivitySubType);
    }

    class ActivitySubTypeHolder extends RecyclerView.ViewHolder {
        private TextView textViewActivitySubTypeType;
        private TextView textViewTimeActivitySubType;
        private TextView textViewSpeedActivitySubType;

        public ActivitySubTypeHolder(@NonNull View itemView) {
            super(itemView);

            textViewActivitySubTypeType = itemView.findViewById(R.id.text_view_activity_type);
            textViewTimeActivitySubType = itemView.findViewById(R.id.start_date);
            textViewSpeedActivitySubType = itemView.findViewById(R.id.end_date);

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
