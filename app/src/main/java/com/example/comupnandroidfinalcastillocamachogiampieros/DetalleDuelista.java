package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.LocationData;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.CartaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleDuelista extends AppCompatActivity {

    Button btnRM, btnMM, btnSincM;
    TextView tvIDD, tvNameD;

    Retrofit mRetro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

        Intent intent =  new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);

        mRetro = RetrofitBuilder.build();

        tvIDD = findViewById(R.id.tvIDD);
        tvNameD = findViewById(R.id.tvNameD);
        btnRM = findViewById(R.id.btnRM);
        btnMM = findViewById(R.id.btnMM);
        btnSincM = findViewById(R.id.btnSincM);

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        DuelistaRepository duelistaRepository = db.duelistaRepository();
        CartaRepository cartaRepository = db.cartaRepository();

        int idObtener;
        idObtener = getIntent().getIntExtra("id",0);
        Log.d("APP_MAIN: idRec", String.valueOf(idObtener));

        Duelista duelista = duelistaRepository.searchDuelistaID(idObtener);

        tvIDD.setText(String.valueOf(duelista.id));
        tvNameD.setText(duelista.name);

        btnRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarCarta.class);
                intent.putExtra("id", idObtener);
                Log.i("APP_MAIN: id", String.valueOf(idObtener));
                startActivity(intent);
            }
        });

        btnMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MostrarCarta.class);
                intent.putExtra("id2", idObtener);
                Log.i("APP_MAIN: id2", String.valueOf(idObtener));
                startActivity(intent);
            }
        });


        CartaService cartaService = mRetro.create(CartaService.class);

        btnSincM.setOnClickListener(view -> {
            if (isNetworkConnected()) {
                List<Carta> SinSincroCarta = cartaRepository.searchCarta(false);
                for (Carta carta : SinSincroCarta) {
                    Log.d("MAIN_APP: DB SSincro", new Gson().toJson(carta));
                    carta.sincC = true;

                    double Latitud = LocationData.getInstance().getLatitude();
                    double Longitud = LocationData.getInstance().getLongitude();
                    carta.latitud = String.valueOf(Latitud);
                    carta.longitud= String.valueOf(Longitud);

                    cartaRepository.updateMovimiento(carta);
                    //*****SINCRO*************************
                    Sincronizacion(cartaService,carta);
                }
                List<Carta> EliminarDB = cartaRepository.getAllCarta();
                WB(cartaService,cartaRepository,EliminarDB);
                Toast.makeText(getBaseContext(), "SINCRONIZADO", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getBaseContext(), "NO HAY INTERNET", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void Sincronizacion(CartaService cartaService, Carta carta){
        Call<Carta> call = cartaService.create(carta);
        call.enqueue(new Callback<Carta>() {
            @Override
            public void onResponse(Call<Carta> call, Response<Carta> response) {
                if(response.isSuccessful()){
                    Carta data = response.body();
                    Log.i("MAIN_APP: WS", new Gson().toJson(data));
                }
            }

            @Override
            public void onFailure(Call<Carta> call, Throwable t) {

            }
        });

    }

    private void WB(CartaService cartaService, CartaRepository cartaRepository, List<Carta> EliminarDB){
        //***Eleminar datos de BD
        cartaRepository.deleteList(EliminarDB);
        //Cargar datos de MockAPI
        Call<List<Carta>> call = cartaService.getAllCarta(6);
        call.enqueue(new Callback<List<Carta>>() {
            @Override
            public void onResponse(Call<List<Carta>> call, Response<List<Carta>> response) {
                List<Carta> data = response.body();
                Log.i("MAIN_APP", new Gson().toJson(data));

                for (Carta carta : data) {
                    cartaRepository.create(carta);
                }
            }
            @Override
            public void onFailure(Call<List<Carta>> call, Throwable t) {

            }
        });
    }
}