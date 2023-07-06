package com.example.comupnandroidfinalcastillocamachogiampieros.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Carta")
public class Carta {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int createdAt;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "puntosAtaque")
    public int puntosAtaque;

    @ColumnInfo(name = "puntosDefensa")
    public int puntosDefensa;

    @ColumnInfo(name = "imageURL")
    public String imageURL;

    @ColumnInfo(name = "sincC")
    public boolean sincC;
}
