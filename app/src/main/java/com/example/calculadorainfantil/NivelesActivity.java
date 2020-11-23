package com.example.calculadorainfantil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class NivelesActivity extends AppCompatActivity {

    private  String nombre;

    private Transition transition;
    public static final long DURACION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);

        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(MainActivity.DURACION);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        nombre = getIntent().getStringExtra("jugador");

    }

    public void BotonFacil(View view) {
        transition = new Fade(Fade.OUT);
        NivelFacil();
    }

    public void BotonNormal(View view) {
        transition = new Fade(Fade.OUT);
        NivelNormal();
    }

    public void BotonDificil(View view) {
        transition = new Fade(Fade.OUT);
        NivelDificil();
    }

    //------------------------------------- NIVEL FACIL -----------------------------------
    public void NivelFacil() {

        transition.setDuration(DURACION);
        transition.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, NivelFacilActivity.class);
        intent.putExtra("jugador", nombre);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    //------------------------------------- NIVEL NORMAL -----------------------------------
    public void NivelNormal() {
        transition.setDuration(DURACION);
        transition.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, NivelNormalActivity.class);
        intent.putExtra("jugador", nombre);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    //------------------------------------- NIVEL DIFICIL -----------------------------------
    public void NivelDificil() {
        transition.setDuration(DURACION);
        transition.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, NivelDificilActivity.class);
        intent.putExtra("jugador", nombre);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

}
