package com.kamaz.weatherApp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.kamaz.weatherApp.pojo.WeatherResponseParent;
import com.kamaz.weatherApp.room.CitiesDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String BaseUrl = "https://api.openweathermap.org/";
    public static String AppId = "2a307726d5dc127ac10aaf0246d36280";

    public String cityToAdd = "";
    //private TextView weatherData;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddCity;

    private CityWeatherAdapter adapter;
    private ArrayList<WeatherResponse> cities = new ArrayList<>();

    CitiesDao citiesDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //weatherData = findViewById(R.id.textView);

        citiesDao = App.getInstance().getDatabase().citiesDao();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCurrentData();
                getCities();
            }
        });

        fabAddCity = findViewById(R.id.fabAddCity);
        recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();

        fabAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertAddCity("Добавить город", "Введите название города");
            }
        });

        getCities();
    }

    public void showAlertAddCity(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(title!=null) builder.setTitle(title);
        if(message!=null) builder.setMessage(message);
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_city,null);
        builder.setView(view);
        final TextView editTextAddCityName = view.findViewById(R.id.editTextAddCityName);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        builder.setPositiveButton("Add", (dialog, which) -> {
            cityToAdd = editTextAddCityName.getText().toString();
            getCity(cityToAdd);
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,0);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,0);
            Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_LONG).show();
        });
        builder.create().show();
    }

    void getCity(String cityToAdd) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory
                        (GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(cityToAdd, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    WeatherResponse cityWeather = response.body();
                    adapter.addItem(cityWeather);
                    recyclerView.scrollToPosition(cities.size()-1);

                    citiesDao.insert(cityWeather);

                } else {
                    Toast.makeText(MainActivity.this,"Город не найден!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this,"Ошибка",Toast.LENGTH_LONG).show();
            }
        });
    }

    void getCities() {

        List<WeatherResponse> citiesDaoAll = citiesDao.getAll();
        cities.addAll(citiesDaoAll);
        adapter.setItems(citiesDaoAll);

        if(citiesDaoAll.size() > 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory
                            (GsonConverterFactory.create())
                    .build();
            WeatherService service = retrofit.create(WeatherService.class);
            Call<WeatherResponseParent> call = service.getCurrentWeathersForCities(getCityIdsFromList(citiesDaoAll), AppId);
            call.enqueue(new Callback<WeatherResponseParent>() {
                @Override
                public void onResponse(@NonNull Call<WeatherResponseParent> call, @NonNull Response<WeatherResponseParent> response) {
                    if (response.code() == 200) {
                        WeatherResponseParent weatherResponse = response.body();
                        assert weatherResponse != null;

                        adapter.setItems(weatherResponse.list);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WeatherResponseParent> call, @NonNull Throwable t) {
                    Log.d("fail", t.getMessage());
                }
            });
        }
    }

    String getCityIdsFromList(List<WeatherResponse> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).id + ",");
        }

        return stringBuilder.toString();
    }

    void initRecyclerView() {
        adapter = new CityWeatherAdapter(cities, R.layout.weather_card, this, new CityWeatherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WeatherResponse cityWeather, int position, View clickView) {
                /*Intent intent = new Intent(MainActivity.this, WeatherDetails.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this,clickView,
                        "weatherCardTransition");

                intent.putExtra("city",  cityWeather);
                startActivity(intent,options.toBundle());*/
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy >0) {
                    // Scroll Down
                    if (fabAddCity.isShown()) {
                        fabAddCity.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fabAddCity.isShown()) {
                        fabAddCity.show();
                    }
                }
            }
        });


        fabAddCity.setOnClickListener(view -> {

        });
    }

}