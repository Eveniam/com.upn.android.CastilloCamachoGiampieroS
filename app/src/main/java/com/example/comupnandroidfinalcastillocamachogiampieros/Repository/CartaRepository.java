package com.example.comupnandroidfinalcastillocamachogiampieros.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update
    void  updateMovimiento(Carta carta);

    @Insert
    void create(Carta carta);

    @Delete
    void deleteList(List<Carta> cartas);
}
