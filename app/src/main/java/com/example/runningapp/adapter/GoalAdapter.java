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
import com.example.runningapp.database.entity.GoalActivitySubType;

// <> recyclerview knows which viewholders we want to use
public class GoalAdapter extends ListAdapter<GoalActivitySubType, GoalAdapter.GoalActivitySubTypeHolder> {

    private static final DiffUtil.ItemCallback<GoalActivitySubType> DIFF_CALLBACK = new DiffUtil.ItemCallback<GoalActivitySubType>() {
        @Override
        public boolean areItemsTheSame(@NonNull GoalActivitySubType oldItem, @NonNull GoalActivitySubType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull GoalActivitySubType oldItem, @NonNull GoalActivitySubType newItem) {
            return oldItem.getActivity_type_id() == newItem.getActivity_type_id() &&
                    oldItem.getTime_goal().equals(newItem.getTime_goal()) &&
                    oldItem.getSpeed_goal() == newItem.getSpeed_goal();
        }
    };

    //    private List<GoalActivitySubType> goals = new ArrayList<>();
    private OnItemClickListener listener;

    public GoalAdapter() {
        super(DIFF_CALLBACK);
    }

    // create and return goalholder
    @NonNull
    @Override
    public GoalActivitySubTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // parerent = recyclerview -> context mainactivity
        // -> layout, context, false
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goal_item, parent, false);
        return new GoalActivitySubTypeHolder(itemView);
    }

    // Get data from single object java object into views of viewholder
    // cant parse int directly to textview
    @Override
    public void onBindViewHolder(@NonNull GoalActivitySubTypeHolder holder, int position) {
        GoalActivitySubType currentGoalActivitySubType = getItem(position);
        holder.textViewTimeGoalActivitySubType.setText(String.valueOf(currentGoalActivitySubType.getName()));
    }

    public GoalActivitySubType getGoalActivitySubTypeAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(GoalActivitySubType goalActivitySubType);
    }

    // Hold views in single recyclerview items
    class GoalActivitySubTypeHolder extends RecyclerView.ViewHolder {
        private TextView textViewTimeGoalActivitySubType;

        // assign textviews
        GoalActivitySubTypeHolder(@NonNull View itemView) {
            super(itemView);
            textViewTimeGoalActivitySubType = itemView.findViewById(R.id.text_view_time_goal);

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
