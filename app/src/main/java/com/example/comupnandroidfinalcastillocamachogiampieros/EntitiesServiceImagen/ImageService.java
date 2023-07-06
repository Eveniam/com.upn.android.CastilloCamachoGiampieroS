package com.example.comupnandroidfinalcastillocamachogiampieros.EntitiesServiceImagen;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageService {
    @POST("image")
    Call<ImageResult> convert(@Body ImageRequest request);
}
