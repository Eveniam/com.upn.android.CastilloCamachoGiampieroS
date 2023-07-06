package com.example.comupnandroidfinalcastillocamachogiampieros.Repository;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;

import java.util.List;

@Dao
public interface CartaRepository {

    @Query("SELECT * FROM Carta")
    List<Carta> getAllCarta();
}
