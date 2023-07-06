package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.CartaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Retrofit;

public class DetalleDuelista extends AppCompatActivity {

    Button btnRM, btnMM, btnSincM;
    TextView tvIDD, tvNameD;

    Retrofit mRetro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_duelista);

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

        btnRM.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), MovimientoRegistrarActivity.class);
//            intent.putExtra("id", idObtener);
//            Log.i("APP_MAIN: id", String.valueOf(idObtener));
//            startActivity(intent);
        });

        btnMM.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), ListMovimientoActivity.class);
//            intent.putExtra("id2", idObtener);
//            Log.i("APP_MAIN: id2", String.valueOf(idObtener));
//            startActivity(intent);
        });


        CartaService serviceM = mRetro.create(CartaService.class);

        btnSincM.setOnClickListener(view -> {
            if (isNetworkConnected()) {
                List<Carta> SinSincroCarta = cartaRepository.searchCarta(false);
                for (Carta carta : SinSincroCarta) {
                    Log.d("MAIN_APP: DB SSincro", new Gson().toJson(carta));
                    carta.sincC = true;

                    double Latitud = LocationData.getInstance().getLatitude();
                    double Longitud = LocationData.getInstance().getLongitude();
                    movimientos.latitud = String.valueOf(Latitud);
                    movimientos.longitud= String.valueOf(Longitud);

                    cartaRepository.updateMovimiento(movimientos);
                    //*****SINCRO*************************
                    SincronizacionMovimientos(serviceM,movimientos);
                }
                List<Movimientos> EliminarDBMovimiento = cartaRepository.getAllMovimientos();
                downloadingMockAPIMovimientos(serviceM,cartaRepository,EliminarDBMovimiento);
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
}