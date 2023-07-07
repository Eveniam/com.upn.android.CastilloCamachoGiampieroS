package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.Adapter.CartaAdapter;
import com.example.comupnandroidfinalcastillocamachogiampieros.Adapter.DuelistaAdapter;
import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.CartaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.DuelistaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MostrarCarta extends AppCompatActivity {

    RecyclerView rvListaCarta;
    Retrofit mRetro;
    List<Carta> mdata = new ArrayList<>();
    CartaAdapter mAdapter = new CartaAdapter(mdata);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_carta);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaCarta = findViewById(R.id.rvListaCarta);
        rvListaCarta.setLayoutManager(layoutManager);

        rvListaCarta.setAdapter(mAdapter);
        mRetro = RetrofitBuilder.build();

        CartaService service = mRetro.create(CartaService.class);

        AppDataBase db = AppDataBase.getInstance(this);
        CartaRepository repository = db.cartaRepository();
        List<Carta> cartaM = repository.getAllCarta();

        if(isNetworkConnected()){
            Call<List<Carta>> call = service.update(cartaM);
            call.enqueue(new Callback<List<Carta>>() {
                @Override
                public void onResponse(Call<List<Carta>> call, Response<List<Carta>> response) {
                    if (response.isSuccessful()) {
                        List<Carta> updatedCartas = response.body();
                        mdata.clear();
                        mdata.addAll(updatedCartas);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Carta>> call, Throwable t) {
                    // Manejar el caso de fallo en la llamada a la API
                }
            });
        }

        mdata.clear();
        mdata.addAll(cartaM);

        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();
        Log.i("MAIN_APP: DB", new Gson().toJson(mdata));
        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}