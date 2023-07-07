package com.example.comupnandroidfinalcastillocamachogiampieros.Service;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartaService {
    @GET("Carta")
    Call<List<Carta>> getAllCarta(@Query("limit") int limit);

    @DELETE("Carta")
    Call<Void> deleteAllCartas();

    @POST("Carta")
    Call<Carta> create(@Body Carta carta);

    @POST ("image")
    Call<ImagenResponse> guardarImage (@Body ImagenToSave imagen);

    @PUT("Carta")
    Call<List<Carta>> update(@Body List<Carta> cartas);


    class  ImagenResponse{
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }

    class ImagenToSave{
        String base64Image;

        public ImagenToSave(String base64Image){
            this.base64Image = base64Image;
        }
    }
}
