package com.example.calculadorainfantil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    private Transition transition;
    public static final long DURACION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void BotonJugar(View view) {
        transition = new Explode();
        Jugar();
    }

    public void BotonScore(View view) {
        transition = new Explode();
        Score();
    }

    //-------------------------------------- BOTON JUGAR --------------------------------------------------------------

    public void Jugar() {
        transition.setDuration(DURACION);
        transition.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, JugarActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    //------------------------------------- BOTON SCORE ---------------------------------------------------------------

    public void Score() {
        transition.setDuration(DURACION);
        transition.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, PuntuacionesActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    //------------------------------------- BOTON INFO ----------------------------------------------------------------

    public void Info(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informacion para el usuario");
        builder.setMessage("Programador: Alex Sastre Bragado\nFecha de Creacion: 10/11/2020\n\nPara iniciar, seleccione el boton JUGAR y despues MODO AVENTURA o ELEGIR NIVEL.\nPara ver la puntuacion seleccione el boton SCORE");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
