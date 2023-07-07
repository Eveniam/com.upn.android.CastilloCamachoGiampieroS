package com.example.comupnandroidfinalcastillocamachogiampieros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.LocationData;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageRequest;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageResult;
import com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen.ImageService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.CartaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Service.DuelistaService;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.CamaraUtils;
import com.example.comupnandroidfinalcastillocamachogiampieros.Utils.RetrofitBuilder;
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

    Retrofit mRetro;
    CartaService cartaService;

    private String urlImage = "";
    private String imagenBase64 = "";
    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_carta);

        mRetro = RetrofitBuilder.build();

        etNombreC = findViewById(R.id.etNombreC);
        etAtaque = findViewById(R.id.etAtaque);
        etDefensa = findViewById(R.id.etDefensa);
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        btnFoto = findViewById(R.id.btnFoto);
        btnGaleria = findViewById(R.id.btnGaleria);
        btnGC = findViewById(R.id.btnGC);
        ivCarta = findViewById(R.id.ivCarta);

        int idObtener;
        idObtener = getIntent().getIntExtra("id", 0);
        Log.d("APP_MAIN: idRM", String.valueOf(idObtener));

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        CartaRepository cartaRepository = db.cartaRepository();
        cartaService = mRetro.create(CartaService.class);

        double Latitud = LocationData.getInstance().getLatitude();
        double Longitud = LocationData.getInstance().getLongitude();
        Log.d("MAIN_APP3-Lat", String.valueOf(Latitud));
        Log.d("MAIN_APP3-Long", String.valueOf(Longitud));

        tvLatitud.setText(String.valueOf(Latitud));
        tvLongitud.setText(String.valueOf(Longitud));

//        btnFoto.setOnClickListener(view -> CamaraUtils.setUpOpenCamera(RegistrarCarta.this));
//        btnGaleria.setOnClickListener(view -> CamaraUtils.setUpOpenGallery(RegistrarCarta.this));
//
//        CartaService cartaService = mRetro.create(CartaService.class);
//
//        btnGC.setOnClickListener(view -> {
//            if (etAtaque.getText().toString().trim().isEmpty() || etDefensa.getText().toString().trim().isEmpty()) {
//                Toast.makeText(getBaseContext(), "Llenar Datos", Toast.LENGTH_SHORT).show();
//            }else{
//                Carta carta = new Carta();
//                carta.id = idObtener;
//                carta.name = etNombreC.getText().toString();
//                carta.puntosAtaque = Integer.parseInt(etAtaque.getText().toString());
//                carta.puntosDefensa = Integer.parseInt(etDefensa.getText().toString());
//                carta.latitud = String.valueOf(Latitud);
//                carta.longitud = String.valueOf(Longitud);
//                carta.imageURL = imgURL;
//                carta.imagenBase64 = imgBase64;
//                carta.sincC = false;
//
//                cartaRepository.create(carta);
//
//                Call<Carta> call= cartaService.create(carta);
//                call.enqueue(new Callback<Carta>() {
//                    @Override
//                    public void onResponse(Call<Carta> call, Response<Carta> response) {
//                        if (response.isSuccessful()) {
//                            Carta data = response.body();
//                            Log.i("MAIN_APP: WS", new Gson().toJson(data));
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Carta> call, Throwable t) {
//
//                    }
//                });
//
//                Log.i("MAIN_APP: GuardaC en DB", new Gson().toJson(carta));
//            }
//            Intent intent = new Intent(getApplicationContext(), DetalleDuelista.class);
//            intent.putExtra("id", idObtener);
//            startActivity(intent);
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode != RESULT_OK) return;
//        Bitmap photo = CamaraUtils.GetBitMap(this, requestCode, data);
//        ivCarta.setImageBitmap(photo);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//        imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://demo-upn.bit2bittest.com/") // revisar
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ImageService service = retrofit.create(ImageService.class);
//
//        ImageRequest req = new ImageRequest();
//        req.base64Image = imgBase64;
//
//        Call<ImageResult> call = service.convert(req);
//        call.enqueue(new Callback<ImageResult>() {
//            @Override
//            public void onResponse(Call<ImageResult> call, Response<ImageResult> response) {
//                if(response.isSuccessful()) {
//                    imgURL = response.body().url;
//                    Log.i("MAIN_APP: URLIMAGEN", imgURL);
//                }
//            }
//            @Override
//            public void onFailure(Call<ImageResult> call, Throwable t) {
//
//            }
//        });
//
//
//    }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOpenCamera();
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAtaque.getText().toString().trim().isEmpty() || etDefensa.getText().toString().trim().isEmpty() ) {
                    Toast.makeText(getBaseContext(), "Llenar Datos", Toast.LENGTH_SHORT).show();
                } else {
                    Carta carta = new Carta();
                    carta.id = idObtener;
                    carta.puntosAtaque = Integer.parseInt(etAtaque.getText().toString());
                    carta.puntosDefensa = Integer.parseInt(etDefensa.getText().toString());
                    carta.name = etNombreC.getText().toString();
                    carta.latitud  = String.valueOf(Latitud);
                    carta.longitud = String.valueOf(Longitud);
                    carta.imagenBase64  = imagenBase64;
                    carta.imageURL = urlImage;
                    if(isNetworkConnected()) carta.sincC = true;
                    carta.sincC = false;
                    cartaRepository.create(carta);

                    if(isNetworkConnected()){{
                        Call<Carta> call = cartaService.create(carta);
                        call.enqueue(new Callback<Carta>() {
                            @Override
                            public void onResponse(Call<Carta> call, Response<Carta> response) {
                                Carta data = response.body();
                                Toast.makeText(getBaseContext(), "DATOS SINCRONIZADOS", Toast.LENGTH_SHORT).show();
                                Log.i("MAIN_APP: BD", new Gson().toJson(data));
                            }
                            @Override
                            public void onFailure(Call<Carta> call, Throwable t) {
                            }
                        });
                    }}

                    Log.i("MAIN_APP: GuardaM en DB", new Gson().toJson(carta));
                }
                Intent intent = new Intent(getApplicationContext(), DetalleDuelista.class);
                intent.putExtra("id", idObtener);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivCarta.setImageBitmap(photo);

            imagenBase64 = BitmaptoBase64(photo);

            if (isNetworkConnected()) {
                base64toLink(imagenBase64);

            }

        }
        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close(); // close cursor

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ivCarta.setImageBitmap(bitmap);

            imagenBase64 = BitmaptoBase64(bitmap);

            if (isNetworkConnected()) {
                base64toLink(imagenBase64);

            }

//            base64toLink(base64);
        }
    }



    private void handleOpenCamera() {
        if(checkSelfPermission(android.Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED)
        {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara");
            openCamara();
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando");
            String[] permissions = new String[] {Manifest.permission.CAMERA};
            requestPermissions(permissions, 1001);
        }
    }

    private void openCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST);
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }


    private String BitmaptoBase64 (Bitmap imagenBitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagenBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String Resulbase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.i("APP_MAIN", Resulbase64);
        return Resulbase64;
    }

    private Bitmap Base64toBitmap(String imagenBase64){
        byte[] decodedBytes = Base64.decode(imagenBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);

    }
    private void base64toLink(String base64) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://demo-upn.bit2bittest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartaService service = retrofit1.create(CartaService.class);
        Call<CartaService.ImagenResponse> call = service.guardarImage(new CartaService.ImagenToSave(base64));
        call.enqueue(new Callback<CartaService.ImagenResponse>() {
            @Override
            public void onResponse(Call<CartaService.ImagenResponse> call, Response<CartaService.ImagenResponse> response) {
                if (response.isSuccessful()) {
                    CartaService.ImagenResponse imageResponse = response.body();
                    Log.i("Respues", response.toString());
                    urlImage = "https://demo-upn.bit2bittest.com/" + imageResponse.getUrl();
                    //tvUrlImagenMov.setText(urlImage);
                    Toast.makeText(getBaseContext(), "Link GENERADO", Toast.LENGTH_SHORT).show();
                    Log.i("Imagen url:", urlImage);

                } else {

                    Log.e("Error cargar imagen",response.toString());
                }
            }

            @Override
            public void onFailure(Call<CartaService.ImagenResponse> call, Throwable t) {

            }
        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}