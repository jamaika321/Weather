package com.kamaz.weatherApp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.ViewHolder> {
    private List<WeatherResponse> cities;
    private int layoutReference;
    private OnItemClickListener onItemClickListener;
    private Activity activity;
    private View parentView;
    private mOnItemClickListener mItemClickListener;

    public CityWeatherAdapter(List<WeatherResponse> cities, int layoutReference, Activity activity, OnItemClickListener onItemClickListener) {
        this.cities = cities;
        this.layoutReference = layoutReference;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public CityWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutReference, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityWeatherAdapter.ViewHolder holder, int position) {
        holder.bind(cities.get(position), onItemClickListener);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mItemClickListener != null) {
//                    mItemClickListener.onClick(position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    /*@Override
    public void onItemDelete(final int position) {
        WeatherResponse tempCity = cities.get(position);
        cities.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, "Removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> {
                    addItem(position, tempCity);
                    //new MainActivity().recyclerScrollTo(position);
                }).show();

    }*/

    public void addItem(WeatherResponse city) {
        cities.add(city);
        notifyDataSetChanged();
    }

    public void setItems(List<WeatherResponse> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCityName;
        TextView textViewWeatherDescription;
        TextView textViewCurrentTemp;
        TextView textViewMaxTemp;
        TextView textViewMinTemp;
        ImageView imageViewWeatherIcon;
        CardView cardViewWeather;

        public ViewHolder(View itemView) {
            super(itemView);

            cardViewWeather = itemView.findViewById(R.id.cardViewWeatherCard);
            textViewCityName = itemView.findViewById(R.id.textViewCardCityName);
            textViewWeatherDescription = itemView.findViewById(R.id.textViewCardWeatherDescription);
            textViewCurrentTemp = itemView.findViewById(R.id.textViewCardCurrentTemp);
            textViewMaxTemp = itemView.findViewById(R.id.textViewCardMaxTemp);
            textViewMinTemp = itemView.findViewById(R.id.textViewCardMinTemp);
            imageViewWeatherIcon = itemView.findViewById(R.id.imageViewCardWeatherIcon);
        }

        public void bind(final WeatherResponse cityWeather, final OnItemClickListener onItemClickListener) {
            textViewCityName.setText(cityWeather.name);
            textViewWeatherDescription.setText(cityWeather.weather.get(0).description);
            textViewCurrentTemp.setText((int) cityWeather.main.temp + "°");
            textViewMaxTemp.setText((int) cityWeather.main.temp_max + "°");
            textViewMinTemp.setText((int) cityWeather.main.temp_min + "°");
            String weatherDescription = cityWeather.weather.get(0).description;
            Picasso.get().load(cityWeather.weather.get(0).icon).into(imageViewWeatherIcon);

            cardViewWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(cityWeather, getAdapterPosition(), cardViewWeather);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(WeatherResponse cityWeather, int position, View view);
    }

    public void SetOnItemClickListener(final mOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
