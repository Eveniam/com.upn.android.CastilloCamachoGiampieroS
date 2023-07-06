package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comupnandroidfinalcastillocamachogiampieros.DB.AppDataBase;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.LocationData;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageRequest;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageResult;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.CamaraUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarCarta extends AppCompatActivity {

    Button btnFoto, btnGaleria, btnGC, btnMapa;
    EditText etNombreC, etAtaque, etDefensa;
    TextView tvLatitud, tvLongitud;
    ImageView ivCarta;

    private String imgURL = "";
    private String imgBase64 = "";
    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_carta);

        int idObtener;
        idObtener = getIntent().getIntExtra("id",0);
        Log.d("APP_MAIN: idRM", String.valueOf(idObtener));

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        CartaRepository cartaRepository = db.cartaRepository();

        etNombreC = findViewById(R.id.etNombreC);
        etAtaque = findViewById(R.id.etAtaque);
        etDefensa = findViewById(R.id.etDefensa);
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        btnFoto = findViewById(R.id.btnFoto);
        btnGaleria= findViewById(R.id.btnGaleria);
        btnGC = findViewById(R.id.btnGC);
        ivCarta = findViewById(R.id.ivCarta);
        btnMapa = this.findViewById(R.id.btnMapa);

        double Latitud = LocationData.getInstance().getLatitude();
        double Longitud = LocationData.getInstance().getLongitude();
        Log.d("MAIN_APP3-Lat", String.valueOf(Latitud));
        Log.d("MAIN_APP3-Long", String.valueOf(Longitud));

        btnMapa.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        });

        tvLatitud.setText(String.valueOf(Latitud));
        tvLongitud.setText(String.valueOf(Longitud));

        btnFoto.setOnClickListener(view -> CamaraUtils.setUpOpenCamera(RegistrarCarta.this));
        btnGaleria.setOnClickListener(view -> CamaraUtils.setUpOpenGallery(RegistrarCarta.this));

        btnGC.setOnClickListener(view -> {
            if (etAtaque.getText().toString().trim().isEmpty() || etDefensa.getText().toString().trim().isEmpty()) {
                Toast.makeText(getBaseContext(), "Llenar Datos", Toast.LENGTH_SHORT).show();
            }else{
                Carta carta = new Carta();
                carta.id = idObtener;
                carta.name = etNombreC.getText().toString();
                carta.puntosAtaque = Integer.parseInt(etAtaque.getText().toString());
                carta.puntosDefensa = Integer.parseInt(etDefensa.getText().toString());
                carta.latitud = String.valueOf(Latitud);
                carta.longitud = String.valueOf(Longitud);
                carta.imageURL = imgURL;
                carta.imagenBase64 = imgBase64;
                carta.sincC = false;

                cartaRepository.create(carta);

                Log.i("MAIN_APP: GuardaC en DB", new Gson().toJson(carta));
            }
            Intent intent = new Intent(getApplicationContext(), DetalleDuelista.class);
            intent.putExtra("id", idObtener);
            startActivity(intent);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;
        Bitmap photo = CamaraUtils.GetBitMap(this, requestCode, data);
        ivCarta.setImageBitmap(photo);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo-upn.bit2bittest.com/") // revisar
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImageService service = retrofit.create(ImageService.class);

        ImageRequest req = new ImageRequest();
        req.base64Image = imgBase64;

        Call<ImageResult> call = service.convert(req);
        call.enqueue(new Callback<ImageResult>() {
            @Override
            public void onResponse(Call<ImageResult> call, Response<ImageResult> response) {
                if(response.isSuccessful()) {
                    imgURL = response.body().url;
                    Log.i("MAIN_APP: URLIMAGEN", imgURL);
                }
            }
            @Override
            public void onFailure(Call<ImageResult> call, Throwable t) {

            }
        });


    }
}