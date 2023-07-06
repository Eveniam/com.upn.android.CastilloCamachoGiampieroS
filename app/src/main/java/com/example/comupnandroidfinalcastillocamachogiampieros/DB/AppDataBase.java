package com.example.comupnandroidfinalcastillocamachogiampieros.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.CartaRepository;
import com.example.comupnandroidfinalcastillocamachogiampieros.Repository.DuelistaRepository;

@Database(entities = {Duelista.class, Carta.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract DuelistaRepository duelistaRepository();
    public abstract CartaRepository cartaRepository();

    public static AppDataBase getInstance(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, "BDFINAL")
                .allowMainThreadQueries()
                .build();
    }

}
