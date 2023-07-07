package com.example.comupnandroidfinalcastillocamachogiampieros.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Duelista")
public class Duelista {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "sincD")
    public boolean sincD;
}
