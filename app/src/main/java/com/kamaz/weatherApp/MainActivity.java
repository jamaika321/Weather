package com.kamaz.weatherApp;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

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

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static String AppId = "2a307726d5dc127ac10aaf0246d36280";
    public static String metric = "metric";

    public String cityToAdd = "";
    //private TextView weatherData;
    private RecyclerView recyclerView;

    private CityWeatherAdapter adapter;
    private ArrayList<WeatherResponse> cities = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    CitiesDao citiesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //weatherData = findViewById(R.id.textView);

        citiesDao = App.getInstance().getDatabase().citiesDao();

        recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();

        getCities();

    }

    public void showAlertAddCity(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_city, null);
        builder.setView(view);
        final TextView editTextAddCityName = view.findViewById(R.id.editTextAddCityName);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        builder.setPositiveButton("Add", (dialog, which) -> {
            cityToAdd = editTextAddCityName.getText().toString();
            getCity(cityToAdd);
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();
        });
        builder.create().show();
    }

    void getCity(String cityToAdd) {

        disposable.add(ApiService.service.getCurrentWeatherData(cityToAdd, AppId, metric)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    adapter.addItem(response);
                    recyclerView.scrollToPosition(cities.size());

                    citiesDao.insert(response);

                }, error -> {
                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                }));
    }

    void getCities() {
        disposable.add(
                citiesDao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(citiesDaoAll -> {
                                    cities.addAll(citiesDaoAll);
                                    adapter.setItems(citiesDaoAll);

                                    if (citiesDaoAll.size() > 0) {
                                        return ApiService.service.getCurrentWeathersForCities(getCityIdsFromList(citiesDaoAll), AppId)
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
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this,clickView,
                        "weatherCardTransition");

                intent.putExtra("CityId", cityWeather.id);
                startActivity(intent,options.toBundle());
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.param_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                showAlertAddCity("Добавить город", "Введите название города");
                return true;
            case R.id.action_update:
                getCities();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}