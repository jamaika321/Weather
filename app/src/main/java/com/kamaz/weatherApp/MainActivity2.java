package com.kamaz.weatherApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.TextView;

import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.kamaz.weatherApp.pojo.WeatherResponseParent;
import com.kamaz.weatherApp.room.CitiesDao;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.kamaz.weatherApp.MainActivity.AppId;

public class MainActivity2 extends AppCompatActivity {

    TextView textViewCurrentTemp;
    TextView textViewCityName;
    TextView textViewWindSpeed;
    TextView textViewHumidity;
    CardView cardViewWeather;

    private CityWeatherAdapter adapter;
    private ArrayList<WeatherResponse> cities = new ArrayList<>();
    private WeatherResponse cityWeather;
    public int name;

    private CompositeDisposable disposable = new CompositeDisposable();
    CitiesDao citiesDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle arguments = getIntent().getExtras();
        name = (Integer) arguments.get("CityId");

        holder();
        getCity();
    }

    void holder(){
        cardViewWeather = findViewById(R.id.cardViewWeatherCard);
        textViewCityName = findViewById(R.id.textViewCardCityName);
        textViewCurrentTemp = findViewById(R.id.textViewCardCurrentTemp);
        textViewWindSpeed = findViewById(R.id.TextViewWindSpeed);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewCurrentTemp = findViewById(R.id.textViewCardCurrentTemp);

        textViewCurrentTemp.setText(name);
    }

    void getCity() {
        disposable.add(
                citiesDao.getById(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(citiesDaoAll -> {
                            cities.addAll(citiesDao);
                            adapter.setItems(citiesDao);
                                    if (citiesDaoAll.size() > 0) {
                                        return ApiService.service.getCurrentWeathersForCities(name, AppId)
                                                .map(cities -> {
                                                    adapter.setItems(cities.list);
                                                    return cities;
                                                });
                                    } else return Single.just(new WeatherResponseParent());
                                }
                        )
                        .subscribe(weatherResponseParent -> {

                        }, error -> {

                        })
        );
    }


    public void bind( ){

    }
}