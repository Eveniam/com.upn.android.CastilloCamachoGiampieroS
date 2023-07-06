package com.example.comupnandroidfinalcastillocamachogiampieros.Repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;

import java.util.List;

@Dao
public interface CartaRepository {

    @Query("SELECT * FROM Carta")
    List<Carta> getAllCarta();

    @Query("SELECT * FROM Carta WHERE sincC LIKE :searchSincro")
    List<Carta> searchCarta(boolean searchSincro);

    @Query("SELECT * FROM Carta WHERE createdAt LIKE :id")
    List<Carta> searchCartaID(int id);

    @Insert
    void create(Carta carta);
}
