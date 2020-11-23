package com.example.calculadorainfantil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

public class JugarActivity extends AppCompatActivity {

    private EditText etNombre;

    private Transition transition;
    public static final long DURACION = 1500;
    public static final long DURACION2 = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(MainActivity.DURACION);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        etNombre = findViewById(R.id.eTNombre);
    }

    public void BotonAventura(View view) {
        transition = new Fade(Fade.OUT);
        ModoAventura();
    }

    public void BotonNiveles(View view) {
        transition = new Explode();
        Niveles();
    }


    //------------------------------------- MODO AVENTURA -----------------------------------
    public void ModoAventura() {
        String nombre = etNombre.getText().toString();

        if(!nombre.equals("")) {
            transition.setDuration(DURACION);
            transition.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(transition);

            Intent intent = new Intent(this, AventuraNivel1Activity.class);
            intent.putExtra("jugador", nombre);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

        } else {
            Toast.makeText(this, "Escribe tu nombre.", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------------------------- ELEGIR NIVEL ------------------------------------
    public void Niveles() {
        String nombre = etNombre.getText().toString();

        if(!nombre.equals("")) {
            transition.setDuration(DURACION2);
            transition.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(transition);

            Intent intent = new Intent(this, NivelesActivity.class);
            intent.putExtra("jugador", nombre);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

        } else {
            Toast.makeText(this, "Escribe tu nombre.", Toast.LENGTH_SHORT).show();
        }
    }




}