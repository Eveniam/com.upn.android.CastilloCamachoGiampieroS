package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.Adapter.DuelistaAdapter;
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

public class ListaDuelista extends AppCompatActivity {

    RecyclerView rvListaDuelista;

    Retrofit mRetro;

    List<Duelista> mdata = new ArrayList<>();

    List<Duelista> bD = new ArrayList<>();
    DuelistaAdapter mAdapter = new DuelistaAdapter(mdata);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_duelista);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaDuelista = findViewById(R.id.rvListaDuelista);
        rvListaDuelista.setLayoutManager(layoutManager);

        rvListaDuelista.setAdapter(mAdapter);

        mRetro = RetrofitBuilder.build();
        DuelistaService service = mRetro.create(DuelistaService.class);

        AppDataBase db = AppDataBase.getInstance(this);
        DuelistaRepository repository = db.duelistaRepository();

        List<Duelista> duel = repository.getAllDuelista();

        if(isNetworkConnected()){
            Call<List<Duelista>> call = service.getAllUser();
            call.enqueue(new Callback<List<Duelista>>() {
                @Override
                public void onResponse(Call<List<Duelista>> call, Response<List<Duelista>> response) {
                    Toast.makeText(getBaseContext(), "RESPUESTA DEL WEB SERVICE", Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        List<Duelista> duelistas = response.body();
                        repository.createWB(duelistas);

                        mdata.clear();
                        mdata.addAll(duelistas);

                        bD = new ArrayList<>(duelistas); // Actualizar la lista bD con los duelistas obtenidos

                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Duelista>> call, Throwable t) {
                    // Manejar el caso de fallo en la llamada a la API
                }
            });
        }

        mdata.clear();
        mdata.addAll(duel);

        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}