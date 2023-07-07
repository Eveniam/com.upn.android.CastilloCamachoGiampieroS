package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.Adapter.DuelistaAdapter;
import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.google.gson.Gson;

import java.util.List;

public class ListaDuelista extends AppCompatActivity {

    RecyclerView rvListaDuelista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_duelista);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaDuelista = findViewById(R.id.rvListaDuelista);
        rvListaDuelista.setLayoutManager(layoutManager);
        mostrarBD();

    }

    private void mostrarBD(){
        AppDataBase db = AppDataBase.getInstance(this);
        DuelistaRepository repository = db.duelistaRepository();
        List<Duelista> mdata = repository.getAllDuelista();

        DuelistaAdapter mAdapter = new DuelistaAdapter(mdata);
        rvListaDuelista.setAdapter(mAdapter);
        Log.i("MAIN_APP: DB", new Gson().toJson(mdata));
        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();
    }
}