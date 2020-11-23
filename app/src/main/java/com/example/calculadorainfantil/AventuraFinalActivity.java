package com.example.calculadorainfantil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AventuraFinalActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES
    private TextView tvNombre, tvPuntos, tvVidas, tvInfo;
    private ImageView ivEstrellas;
    private ImageButton ibVolver;

    int puntos;
    int vidas = 3;
    String nombreJugador, puntos_string, vidas_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventura_final);

        tvNombre = findViewById(R.id.tvNombre);
        tvPuntos = findViewById(R.id.textView3);
        tvVidas = findViewById(R.id.textViewVidas);
        tvInfo = findViewById(R.id.textViewInfo);
        ivEstrellas = findViewById(R.id.imageView_estrellas);
        ibVolver = findViewById(R.id.imageButtonExit);

        nombreJugador = getIntent().getStringExtra("jugador");
        tvNombre.setText(nombreJugador);

        puntos_string = getIntent().getStringExtra("puntos");
        puntos = Integer.parseInt(puntos_string);
        tvPuntos.setText(String.valueOf(puntos));

        vidas_string = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(vidas_string);

        if(vidas == 3) {
            ivEstrellas.setImageResource(R.drawable.tres_estrellas);
            tvInfo.setText("Excelente trabajo");
            tvVidas.setText("" + 3);
        } else if(vidas == 2) {
            ivEstrellas.setImageResource(R.drawable.dos_estrellas);
            tvInfo.setText("Muy bien trabajado");
            tvVidas.setText("" + 2);
        } else {
            ivEstrellas.setImageResource(R.drawable.una_estrella);
            tvInfo.setText("Buen trabajo");
            tvVidas.setText("" + 1);
        }
    }

    public void Salir(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
    //------------------------------ METODOS PARA GUARDAR EL ESTADO --------------------------------------------------------------------------

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        BitmapDrawable drawableVidas = (BitmapDrawable) ivEstrellas.getDrawable();
        Bitmap bitmapVidas = drawableVidas.getBitmap();

        outState.putString("nombreTV", tvNombre.getText().toString());
        outState.putString("nombre", nombreJugador);

        outState.putString("puntosTV", tvPuntos.getText().toString());
        outState.putInt("puntos", puntos);

        outState.putString("vidasTV", tvVidas.getText().toString());
        outState.putInt("vidas", vidas);
        outState.putParcelable("vidasIV", bitmapVidas);

        outState.putString("info", tvInfo.getText().toString());



    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombreJugador = savedInstanceState.getString("nombre");
        tvNombre.setText(savedInstanceState.getString("nombreTV"));

        tvPuntos.setText(savedInstanceState.getString("puntosTV"));
        puntos = savedInstanceState.getInt("puntos");

        vidas = savedInstanceState.getInt("vidas");
        ivEstrellas.setImageBitmap((Bitmap) savedInstanceState.getParcelable("vidasIV"));
        tvVidas.setText(savedInstanceState.getString("vidasTV"));

        tvInfo.setText(savedInstanceState.getString("info"));
    }

}