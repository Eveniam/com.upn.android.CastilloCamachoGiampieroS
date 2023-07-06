package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.gson.Gson;

import java.util.List;

public class MostrarCarta extends AppCompatActivity {

    RecyclerView rvListaCarta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_carta);

//        int idObtener;
//        idObtener = getIntent().getIntExtra("id2",0);
//        Log.d("APP_MAIN: idListC", String.valueOf(idObtener));
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        rvListaCarta = findViewById(R.id.rvListaCarta);
//        rvListaCarta.setLayoutManager(layoutManager);
//
//        AppDataBase db = AppDataBase.getInstance(this);
//        CartaRepository cartaRepository = db.cartaRepository();
//        List<Carta> mDataCarta = cartaRepository.searchCartaID(idObtener);
//
//        CartaAdapter cartaAdapter = new CartaAdapter(mDataCarta);
//        rvListaCarta.setAdapter(cartaAdapter);
//        Log.i("MAIN_APP: DB", new Gson().toJson(mDataCarta));
//        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaCarta = findViewById(R.id.rvListaCarta);
        rvListaCarta.setLayoutManager(layoutManager);

        AppDataBase db = AppDataBase.getInstance(this);
        CartaRepository cartaRepository = db.cartaRepository();
        List<Carta> mdata = cartaRepository.getAllCarta();

        CartaAdapter mAdapter = new CartaAdapter(mdata);
        rvListaCarta.setAdapter(mAdapter);
        Log.i("MAIN_APP: DB", new Gson().toJson(mdata));
        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();
    }
}