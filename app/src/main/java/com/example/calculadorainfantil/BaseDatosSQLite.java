package com.example.calculadorainfantil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatosSQLite extends SQLiteOpenHelper{

    public BaseDatosSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("create table puntuacionesPracticaFacil(nombre text, puntos real, nivel text)");
        BD.execSQL("create table puntuacionesPracticaNormal(nombre text , puntos real, nivel text)");
        BD.execSQL("create table puntuacionesPracticaDificil(nombre text, puntos real, nivel text)");
        BD.execSQL("create table puntuacionesAventura(nombre text, puntos real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
