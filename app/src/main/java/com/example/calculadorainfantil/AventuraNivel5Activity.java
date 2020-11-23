package com.example.calculadorainfantil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AventuraNivel5Activity extends AppCompatActivity {

    //DECLARACION DE VARIABLES
    private TextView tvNombre, tvPuntos;
    private ImageView ivNumUno, ivNumDos, ivVidas;
    private EditText etRespuesta;

    int puntos, numAleatorioUno, numAleatorioDos, resultado;
    int vidas = 3;
    String nombreJugador, puntos_string, vidas_string;

    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventura_nivel5);

        tvNombre = findViewById(R.id.tvNombre);
        tvPuntos = findViewById(R.id.tvPuntos);
        ivNumUno = findViewById(R.id.imageViewNum1);
        ivNumDos = findViewById(R.id.imageViewNum2);
        ivVidas = findViewById(R.id.imageView_vidas);
        etRespuesta = findViewById(R.id.etRespuesta);

        nombreJugador = getIntent().getStringExtra("jugador");
        tvNombre.setText(nombreJugador);

        puntos_string = getIntent().getStringExtra("puntos");
        puntos = Integer.parseInt(puntos_string);
        tvPuntos.setText(String.valueOf(puntos));

        vidas_string = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(vidas_string);
        if(vidas == 3) {
            ivVidas.setImageResource(R.drawable.tres_vidas);
        } else if(vidas == 2) {
            ivVidas.setImageResource(R.drawable.dos_vidas);
        } else {
            ivVidas.setImageResource(R.drawable.una_vida);
        }

        Toast.makeText(this, "Aldea Abandonada - Nivel 5", Toast.LENGTH_LONG).show();

        NumeroAleatorio();
    }

    public void BotonConfirmar(View view) {
        Resolver();
    }

    //----------------------------------------- METODO COMPROBAR RESPUESTA ---------------------------------------------------------

    public void Resolver() {
        String respuesta = etRespuesta.getText().toString();

        if(!respuesta.equals("")) {
            int respuestaJugador = Integer.parseInt(respuesta);
            if(resultado == respuestaJugador) {
                puntos++;
                tvPuntos.setText("" + puntos);
                etRespuesta.setText("");
                BaseDatos();
                NumeroAleatorio();

            } else {
                vidas--;
                BaseDatos();

                switch (vidas) {
                    case 2:
                        etRespuesta.startAnimation(shakeError());
                        Toast.makeText(this,"Te quedan 2 vidas", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.dos_vidas);
                        break;
                    case 1:
                        etRespuesta.startAnimation(shakeError());
                        Toast.makeText(this,"Te queda 1 vida!!", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.una_vida);
                        break;
                    case 0:
                        Toast.makeText(this,"Has perdido todas las vidas :(",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                etRespuesta.setText("");
                NumeroAleatorio();
            }
        } else {
            Toast.makeText(this,"Escribe tu respuesta.",Toast.LENGTH_SHORT).show();
        }


    }

    //---------------------------------------------------- METODO CAMBIAR NUMEROS --------------------------------------------------------

    public void NumeroAleatorio() {
        if(puntos <= 29) {
            numAleatorioUno = (int)(Math.random() * 10);
            numAleatorioDos = (int)(Math.random() * 10);

            if(numAleatorioDos != 0) {
                resultado = numAleatorioUno / numAleatorioDos;
            } else {
                NumeroAleatorio();
            }

            if(numAleatorioUno % numAleatorioDos == 0) {
                for(int i = 0; i < numero.length; i++) {
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if(numAleatorioUno == i) {
                        ivNumUno.setImageResource(id);
                    } if(numAleatorioDos == i) {
                        ivNumDos.setImageResource(id);
                    }
                }
            } else {
                NumeroAleatorio();
            }
        } else {
            puntos_string = String.valueOf(puntos);
            vidas_string = String.valueOf(vidas);

            Intent intent = new Intent(this, AventuraNivel6Activity.class);
            intent.putExtra("jugador", nombreJugador);
            intent.putExtra("puntos", puntos_string);
            intent.putExtra("vidas", vidas_string);

            startActivity(intent);
            finish();

        }
    }

    public void BaseDatos() {
        BaseDatosSQLite baseDatosSQLite = new BaseDatosSQLite(this, "juego", null, 1);
        SQLiteDatabase bd = baseDatosSQLite.getWritableDatabase();


            ContentValues insertar = new ContentValues();
            insertar.put("nombre", nombreJugador);
            insertar.put("puntos", puntos);

            bd.insert("puntuacionesAventura", null, insertar);
            bd.close();

    }

    //----------------------------------------------- METODO ANIMACION EDIT TEXT ------------------------------------------------------------
    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }


    @Override
    public void onBackPressed() {

    }

    //------------------------------ METODOS PARA GUARDAR EL ESTADO -----------------------------------------------------------------------------

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        BitmapDrawable drawableVidas = (BitmapDrawable) ivVidas.getDrawable();
        Bitmap bitmapVidas = drawableVidas.getBitmap();

        BitmapDrawable drawableNum1 = (BitmapDrawable) ivNumUno.getDrawable();
        Bitmap bitmapNum1 = drawableNum1.getBitmap();

        BitmapDrawable drawableNum2 = (BitmapDrawable) ivNumDos.getDrawable();
        Bitmap bitmapNum2 = drawableNum2.getBitmap();

        outState.putString("nombreTV", tvNombre.getText().toString());
        outState.putString("nombre", nombreJugador);

        outState.putString("puntosTV", tvPuntos.getText().toString());
        outState.putInt("puntos", puntos);

        outState.putInt("vidas", vidas);
        outState.putParcelable("vidasIV", bitmapVidas);

        outState.putInt("num1", numAleatorioUno);
        outState.putParcelable("num1IV", bitmapNum1);
        outState.putInt("num2", numAleatorioDos);
        outState.putParcelable("num2IV", bitmapNum2);
        outState.putInt("resultado", resultado);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombreJugador = savedInstanceState.getString("nombre");
        tvNombre.setText(savedInstanceState.getString("nombreTV"));

        tvPuntos.setText(savedInstanceState.getString("puntosTV"));
        puntos = savedInstanceState.getInt("puntos");

        vidas = savedInstanceState.getInt("vidas");
        ivVidas.setImageBitmap((Bitmap) savedInstanceState.getParcelable("vidasIV"));

        numAleatorioUno = savedInstanceState.getInt("num1");
        numAleatorioDos = savedInstanceState.getInt("num2");
        ivNumUno.setImageBitmap((Bitmap) savedInstanceState.getParcelable("num1IV"));
        ivNumDos.setImageBitmap((Bitmap) savedInstanceState.getParcelable("num2IV"));
        resultado = savedInstanceState.getInt("resultado");
    }
}