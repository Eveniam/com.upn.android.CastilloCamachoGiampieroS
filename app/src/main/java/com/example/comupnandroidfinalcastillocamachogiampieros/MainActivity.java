package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    Button btnGuardar, btnMostrar, btnSincronizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        });

        btnSincronizar.setOnClickListener(view -> {

        });
    }
}