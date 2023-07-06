package com.example.comupnandroidfinalcastillocamachogiampieros.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;

import java.util.List;

@Dao
public interface DuelistaRepository {

    @Query("SELECT * FROM Duelista")
    List<Duelista> getAllDuelista();

    @Query("SELECT * FROM Duelista WHERE sincD LIKE :searchSincro")
    List<Duelista> searchDuelista(boolean searchSincro);

    @Update
    void  update(Duelista cuenta);

    @Insert
    void create(Duelista duelista);

    @Delete
    void deleteList(List<Duelista> duelistas);
}
