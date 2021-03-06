package com.example.calculadorainfantil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NivelNormalActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES
    private TextView tvNombre, tvPuntos;
    private ImageView ivNumUno, ivNumDos, ivVidas, ivSigno;
    private EditText etRespuesta;

    int puntos, numAleatorioUno, numAleatorioDos, resultado;
    int vidas = 3;
    String nombreJugador, puntos_string, vidas_string;

    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    String nivel = "Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel_normal);

        tvNombre = findViewById(R.id.tvNombre);
        tvPuntos = findViewById(R.id.tvPuntos);
        ivNumUno = findViewById(R.id.imageViewNum1);
        ivNumDos = findViewById(R.id.imageViewNum2);
        ivVidas = findViewById(R.id.imageView_vidas);
        ivSigno = findViewById(R.id.imageViewSigno);
        etRespuesta = findViewById(R.id.etRespuesta);

        nombreJugador = getIntent().getStringExtra("jugador");
        tvNombre.setText(nombreJugador);

        Toast.makeText(this, "Choza Abandonada - Nivel Normal", Toast.LENGTH_LONG).show();

        NumeroAleatorio();
    }

    //----------------------------------------- METODO COMPROBAR RESPUESTA ---------------------------------------------------------

    public void Resolver(View view) {
        String respuesta = etRespuesta.getText().toString();

        if(!respuesta.equals("")){
            int respuestaJugador = Integer.parseInt(respuesta);
            if(resultado == respuestaJugador){
                puntos++;
                tvPuntos.setText("" + puntos);
                etRespuesta.setText("");
                NumeroAleatorio();

            } else {
                vidas--;
                etRespuesta.startAnimation(shakeError());

                switch (vidas) {
                    case 2:
                        Toast.makeText(this,"Te quedan 2 vidas", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.dos_vidas);
                        break;
                    case 1:
                        Toast.makeText(this,"Te queda 1 vida!!", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.una_vida);
                        break;
                    case 0:
                        BaseDatos();
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
        if(puntos <= 1000000) {
            numAleatorioUno = (int)(Math.random() * 10);
            numAleatorioDos = (int)(Math.random() * 10);

            if(numAleatorioDos != 0 && numAleatorioUno % numAleatorioDos == 0 ) {
                resultado = numAleatorioUno / numAleatorioDos;
                ivSigno.setImageResource(R.drawable.division);
            } else {
                resultado = numAleatorioUno * numAleatorioDos;
                ivSigno.setImageResource(R.drawable.multiplicacion);
            }

            for(int i = 0; i < numero.length; i++) {
                int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                if(numAleatorioUno == i) {
                    ivNumUno.setImageResource(id);
                } if(numAleatorioDos == i) {
                    ivNumDos.setImageResource(id);
                }
            }

        } else {
            puntos_string = String.valueOf(puntos);
            vidas_string = String.valueOf(vidas);

            Intent intent = new Intent(this, MainActivity.class);
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
        insertar.put("nivel", nivel);

        bd.insert("puntuacionesPracticaNormal", null, insertar);
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

    //------------------------------ METODOS PARA GUARDAR EL ESTADO ----------------------------------------------------------------------------

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        BitmapDrawable drawableVidas = (BitmapDrawable) ivVidas.getDrawable();
        Bitmap bitmapVidas = drawableVidas.getBitmap();

        BitmapDrawable drawableNum1 = (BitmapDrawable) ivNumUno.getDrawable();
        Bitmap bitmapNum1 = drawableNum1.getBitmap();

        BitmapDrawable drawableNum2 = (BitmapDrawable) ivNumDos.getDrawable();
        Bitmap bitmapNum2 = drawableNum2.getBitmap();

        BitmapDrawable drawableSigno = (BitmapDrawable) ivSigno.getDrawable();
        Bitmap bitmapSigno = drawableSigno.getBitmap();

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
        outState.putParcelable("signo", bitmapSigno);


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
        ivSigno.setImageBitmap((Bitmap) savedInstanceState.getParcelable("signo"));
    }
}
