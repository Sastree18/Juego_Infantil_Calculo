package com.example.calculadorainfantil;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.Fade;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public class PuntuacionesActivity extends AppCompatActivity {

    private TextView tvPuntuacionesAventura;
    private TextView tvPuntuacionesNivelFacil;
    private TextView tvPuntuacionesNivelNormal;
    private TextView tvPuntuacionesNivelDificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(MainActivity.DURACION);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        tvPuntuacionesAventura = findViewById(R.id.tvPuntuacionesAventura);
        tvPuntuacionesAventura.setMovementMethod( new ScrollingMovementMethod());
        tvPuntuacionesNivelFacil = findViewById(R.id.tvPuntuacionesNivelFacil);
        tvPuntuacionesNivelFacil.setMovementMethod( new ScrollingMovementMethod());
        tvPuntuacionesNivelNormal = findViewById(R.id.tvPuntuacionesNivelNormal);
        tvPuntuacionesNivelNormal.setMovementMethod( new ScrollingMovementMethod());
        tvPuntuacionesNivelDificil = findViewById(R.id.tvPuntuacionesNivelDificil);
        tvPuntuacionesNivelDificil.setMovementMethod( new ScrollingMovementMethod());

        //Tabla modo practica Facil
        BaseDatosSQLite bd = new BaseDatosSQLite(this,"juego", null, 1);
        SQLiteDatabase database = bd.getWritableDatabase();

        Cursor consulta = database.rawQuery("select * from puntuacionesPracticaFacil where puntos = (select max(puntos) from puntuacionesPracticaFacil) order by puntos desc", null);

        if(consulta.moveToFirst()) { //sirve para saber si se encontro algo en la base de datos
            String bd_nombre = consulta.getString(0);
            String bd_puntos = consulta.getString(1);
            String bd_nivel = consulta.getString(2);
            tvPuntuacionesNivelFacil.setText(bd_nombre + ": " + bd_puntos + " pts  Nivel: " + bd_nivel + "\n");
            database.close();
        } else {
            database.close();
        }

        //Tabla modo practica Normal
        BaseDatosSQLite bdNormal = new BaseDatosSQLite(this,"juego", null, 1);
        SQLiteDatabase databaseNormal = bdNormal.getWritableDatabase();

        Cursor consultaNormal = databaseNormal.rawQuery("select * from puntuacionesPracticaNormal where puntos = (select max(puntos) from puntuacionesPracticaNormal) order by puntos desc", null);

        while(consultaNormal.moveToNext()) {
            String bd_nombreN = consultaNormal.getString(0);
            String bd_puntosN = consultaNormal.getString(1);
            String bd_nivelN = consultaNormal.getString(2);
            tvPuntuacionesNivelNormal.setText(bd_nombreN + ": " + bd_puntosN + " pts  Nivel: " + bd_nivelN + "\n" + tvPuntuacionesNivelNormal.getText().toString());

        }
        databaseNormal.close();
        //Tabla modo practica Dificil
        BaseDatosSQLite bdDificil = new BaseDatosSQLite(this,"juego", null, 1);
        SQLiteDatabase databaseDificil = bdDificil.getWritableDatabase();

        Cursor consultaDificil = databaseDificil.rawQuery("select * from puntuacionesPracticaDificil where puntos = (select max(puntos) from puntuacionesPracticaDificil) order by puntos desc", null);

        if(consultaDificil.moveToFirst()) {
            String bd_nombreD = consultaDificil.getString(0);
            String bd_puntosD = consultaDificil.getString(1);
            String bd_nivelD = consultaDificil.getString(2);
            tvPuntuacionesNivelDificil.setText(bd_nombreD + ": " + bd_puntosD + " pts  Nivel: " + bd_nivelD + "\n");
            databaseDificil.close();
        } else {
            databaseDificil.close();
        }

        //Tabla modo aventura
        BaseDatosSQLite bd2 = new BaseDatosSQLite(this, "juego", null, 1);
        SQLiteDatabase database2 = bd2.getWritableDatabase();

        Cursor consulta2 = database2.rawQuery("select * from puntuacionesAventura where puntos = (select max(puntos) from puntuacionesAventura) order by puntos desc", null);
        if(consulta2.moveToFirst()) {
            String bd_nombre2 = consulta2.getString(0);
            String bd_puntos2 = consulta2.getString(1);
            tvPuntuacionesAventura.setText(bd_nombre2 + ": " + bd_puntos2 + " pts" + "\n");
            database2.close();
        } else {
            database2.close();
        }

     }
}
