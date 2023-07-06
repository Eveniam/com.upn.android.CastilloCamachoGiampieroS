package com.example.comupnandroidfinalcastillocamachogiampieros.Service;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;

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
    Call<List<Carta>> getAllCarta(@Query("limit") int limit, @Query("page") int page);

    @GET("Carta/{id}")
    Call<Carta> findUser(@Path("id") int id);

    @POST("Carta")
    Call<Carta> create(@Body Carta carta);

    @PUT("Carta")
    Call<List<Carta>> updateWEB(@Body List<Carta> cartas);

    @DELETE("Carta")
    Call<Void> deleteAllCartas();
}
