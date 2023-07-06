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
    Call<List<Duelista>> getAllUser(@Query("limit") int limit, @Query("page") int page);

    @GET("cuenta/{id}")
    Call<Duelista> findUser(@Path("id") int id);

    @POST("cuenta")
    Call<Duelista> create(@Body Duelista duelista);

    @PUT("cuenta")
    Call<List<Duelista>> updateWEB(@Body List<Duelista> duelistas);

    @DELETE("cuenta")
    Call<Void> deleteAllDeulistas();
}
