package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.DuelistaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit mRetro;
    EditText edtName;
    Button btnGuardar, btnMostrar;
    DuelistaRepository duelistaRepository;
    DuelistaService duelistaService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetro = RetrofitBuilder.build();

        edtName = findViewById(R.id.edtName);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        duelistaRepository = db.duelistaRepository();
        duelistaService = mRetro.create(DuelistaService.class);

        if(isNetworkConnected()) {
            List<Duelista> dueli = duelistaRepository.getAllDuelista();
            for(Duelista duel: dueli){
                if(duel.sincD == false){
                    duel.sincD = true;
                    Call<Duelista> call = duelistaService.create(duel);
                    call.enqueue(new Callback<Duelista>() {
                        @Override
                        public void onResponse(Call<Duelista> call, Response<Duelista> response) {
                            Duelista data = response.body();
                            Toast.makeText(getBaseContext(), "DATOS SINCRONIZADOS", Toast.LENGTH_SHORT).show();
                            Log.i("MAIN_APP: BD", new Gson().toJson(data));
                        }

                        @Override
                        public void onFailure(Call<Duelista> call, Throwable t) {

                        }
                    });
                }
            }
        }

        btnGuardar.setOnClickListener(view -> {

            if (edtName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getBaseContext(), "DATOS?", Toast.LENGTH_SHORT).show();
            }else{
                Duelista duelista = new Duelista();
                duelista.name = edtName.getText().toString();
                if(isNetworkConnected()) duelista.sincD = true;
                duelistaRepository.create(duelista);
                edtName.setText("");
                Log.i("MAIN_APP: DB", new Gson().toJson(duelista));

                if(isNetworkConnected()){{
                    Call<Duelista> call = duelistaService.create(duelista);
                    call.enqueue(new Callback<Duelista>() {
                        @Override
                        public void onResponse(Call<Duelista> call, Response<Duelista> response) {
                            Duelista data = response.body();
                            Toast.makeText(getBaseContext(), "DATOS SINCRONIZADOS", Toast.LENGTH_SHORT).show();
                            Log.i("MAIN_APP: BD", new Gson().toJson(data));
                        }
                        @Override
                        public void onFailure(Call<Duelista> call, Throwable t) {
                        }
                    });
                }}
            }

        });

        btnMostrar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListaDuelista.class);
            startActivity(intent);
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}