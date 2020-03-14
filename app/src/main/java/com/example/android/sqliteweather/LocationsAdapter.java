package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sqliteweather.data.ForecastLocation;

import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {

    private List<ForecastLocation> mForecastItems;
    private OnLocationItemClickListener mLocationItemClickListener;

    public interface OnLocationItemClickListener {
        void onForecastItemClick(ForecastLocation forecastItem);
    }

    public LocationsAdapter(OnLocationItemClickListener clickListener) {
        mLocationItemClickListener = clickListener;
    }

    public void updateForecastItems(List<ForecastLocation> forecastItems) {
        mForecastItems = forecastItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mForecastItems != null) {
            return mForecastItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.navigation_location_item, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.bind(mForecastItems.get(position));
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mLocation;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            mLocation = itemView.findViewById(R.id.tv_location_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ForecastLocation locItem = mForecastItems.get(getAdapterPosition());
            mLocationItemClickListener.onForecastItemClick(locItem);
        }

        public void bind(ForecastLocation forecastLocation) {
            mLocation.setText(forecastLocation.loc_name);
        }
    }
}
