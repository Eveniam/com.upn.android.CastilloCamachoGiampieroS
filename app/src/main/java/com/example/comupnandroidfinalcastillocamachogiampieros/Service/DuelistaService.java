package com.example.comupnandroidfinalcastillocamachogiampieros.Service;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DuelistaService {
    @GET("Duelista")
    Call<List<Duelista>> getAllUser();

    @POST("Duelista")
    Call<Duelista> create(@Body Duelista duelista);

    @PUT("Duelista")
    Call<List<Duelista>> update(@Body List<Duelista> duelistas);

    @DELETE("Duelista")
    Call<Void> deleteAll();
}
