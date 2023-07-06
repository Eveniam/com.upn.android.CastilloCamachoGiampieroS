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
    Call<List<Carta>> getAllCarta(@Query("limit") int limit);

    @DELETE("Carta")
    Call<Void> deleteAllCartas();
}
