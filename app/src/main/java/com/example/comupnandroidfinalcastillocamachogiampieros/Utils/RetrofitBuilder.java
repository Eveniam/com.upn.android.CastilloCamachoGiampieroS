package com.example.comupnandroidfinalcastillocamachogiampieros.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl("https://64774bd09233e82dd53b64be.mockapi.io/") // revisar
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
