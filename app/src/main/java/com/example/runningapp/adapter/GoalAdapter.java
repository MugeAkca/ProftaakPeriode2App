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
import com.example.runningapp.database.entity.Goal;

// <> recyclerview knows which viewholders we want to use
public class GoalAdapter extends ListAdapter<Goal, GoalAdapter.GoalHolder> {

    private static final DiffUtil.ItemCallback<Goal> DIFF_CALLBACK = new DiffUtil.ItemCallback<Goal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Goal oldItem, @NonNull Goal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Goal oldItem, @NonNull Goal newItem) {
            return oldItem.getActivity_type_id() == newItem.getActivity_type_id() &&
                    oldItem.getTime_goal().equals (newItem.getTime_goal()) &&
                    oldItem.getSpeed_goal() == newItem.getSpeed_goal();
        }
    };

//    private List<Goal> goals = new ArrayList<>();
    private OnItemClickListener listener;

    public GoalAdapter() {
        super(DIFF_CALLBACK);
    }

    // create and return goalholder
    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // parerent = recyclerview -> context mainactivity
        // -> layout, context, false
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goal_item, parent, false);
        return new GoalHolder(itemView);
    }

    // Get data from single object java object into views of viewholder
    // cant parse int directly to textview
    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        Goal currentGoal = getItem(position);
        holder.textViewTimeGoal.setText(String.valueOf(currentGoal.getTime_goal()));
    }

    public Goal getGoalAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Goal goal);
    }

    // Hold views in single recyclerview items
    class GoalHolder extends RecyclerView.ViewHolder {
        private TextView textViewTimeGoal;

        // assign textviews
        GoalHolder(@NonNull View itemView) {
            super(itemView);
            textViewTimeGoal = itemView.findViewById(R.id.text_view_time_goal);

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
