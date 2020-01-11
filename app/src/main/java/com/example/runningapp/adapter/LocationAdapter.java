package com.example.runningapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Location;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationHolder> {

    private static final DiffUtil.ItemCallback<Location> DIFF_CALLBACK = new DiffUtil.ItemCallback<Location>() {
        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getLocation_id() == newItem.getLocation_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getLongitude().equals(newItem.getLongitude()) &&
                    oldItem.getLatitude().equals(newItem.getLatitude()) &&
                    oldItem.getActivity_id() == (newItem.getActivity_id());
        }
    };

    //private List<Location> locations = new ArrayList<>();
    private OnItemClickListener listener;

    public LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_location_item, parent, false);
        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        Location currentLocation = getItem(position);
        holder.textViewLocationType.setText(String.valueOf(currentLocation.getActivity_id()));
    }


    public Location getLocationAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }

    class LocationHolder extends RecyclerView.ViewHolder {
        private TextView textViewLocationType;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);

            textViewLocationType = itemView.findViewById(R.id.text_view_time_goal);

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
