package com.example.comupnandroidfinalcastillocamachogiampieros.Repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;

import java.util.List;

@Dao
public interface DuelistaRepository {

    @Query("SELECT * FROM Duelista")
    List<Duelista> getAllDuelista();

    @Insert
    void create(Duelista duelista);
}
