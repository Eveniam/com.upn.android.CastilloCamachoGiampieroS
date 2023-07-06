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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit mRetro;
    EditText edtName;
    Button btnGuardar, btnMostrar, btnSincronizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetro = RetrofitBuilder.build();

        edtName = findViewById(R.id.edtName);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);
        btnSincronizar = findViewById(R.id.btnSincronizar);

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        DuelistaRepository duelistaRepository = db.duelistaRepository();

        btnGuardar.setOnClickListener(view -> {

            if (edtName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getBaseContext(), "DATOS?", Toast.LENGTH_SHORT).show();
            }else{
                Duelista duelista = new Duelista();
                duelista.name = edtName.getText().toString();
                duelistaRepository.create(duelista);
                edtName.setText("");
                Log.i("MAIN_APP: Guarda en DB", new Gson().toJson(duelista));
            }
        });

        btnMostrar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListaDuelista.class);
            startActivity(intent);
        });

        DuelistaService duelistaService = mRetro.create(DuelistaService.class);

        btnSincronizar.setOnClickListener(view -> {

            if (!isNetworkConnected()){
                Toast.makeText(getBaseContext(), "NO HAY CONEXION A INTERNET", Toast.LENGTH_SHORT).show();
            }else{
                List<Duelista> Sduelistas = duelistaRepository.searchDuelista(false);
                for (Duelista duelista :Sduelistas) {
                    Log.d("MAIN_APP: DBDuel", new Gson().toJson(duelista));
                    duelista.sincD = true;
                    duelistaRepository.update(duelista);
                    Sincronizacion(duelistaService,duelista);
                }
                List<Duelista> DeleteDB =duelistaRepository.getAllDuelista();
                WS(duelistaService,duelistaRepository,DeleteDB);

            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void Sincronizacion(DuelistaService duelistaService, Duelista duelista){
        Call<Duelista> call= duelistaService.create(duelista);
        call.enqueue(new Callback<Duelista>() {
            @Override
            public void onResponse(Call<Duelista> call, Response<Duelista> response) {
                if (response.isSuccessful()) {
                    Duelista data = response.body();
                    Log.i("MAIN_APP: WS", new Gson().toJson(data));
                }
            }
            @Override
            public void onFailure(Call<Duelista> call, Throwable t) {

            }
        });
    }

    private void WS(DuelistaService duelistaService,DuelistaRepository duelistaRepository , List<Duelista> DeleteD){
        duelistaRepository.deleteList(DeleteD);//DELETE

        Call<List<Duelista>> call = duelistaService.getAllUser();//CARGAR
        call.enqueue(new Callback<List<Duelista>>() {
            @Override
            public void onResponse(Call<List<Duelista>> call, Response<List<Duelista>> response) {
                List<Duelista> data = response.body();
                Log.i("MAIN_APP", new Gson().toJson(data));

                for (Duelista duelista : data) {
                    duelistaRepository.create(duelista);
                }
            }
            @Override
            public void onFailure(Call<List<Duelista>> call, Throwable t) {
            }
        });

    }
}