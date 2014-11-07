package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class CrearEquipo extends Activity {

    LinearLayout ll;
    Context context = this;
    ArrayList<EditText> nombres = new ArrayList<EditText>();
    Integer indice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        SharedPreferences prefs =
                getSharedPreferences("Jugadores",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.commit();

        ll = (LinearLayout)findViewById(R.id.LinearLayout_crear_equipo);

        inicializarEditTexts();

    }

    @Override
    public void onBackPressed()
    {
        SharedPreferences prefs =
                getSharedPreferences("Jugadores",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();

        for (Integer i = 0; i < nombres.size(); i++) {
            nombres.get(i).getText().toString();
            editor.putString("jugador" + i.toString(), nombres.get(i).getText().toString());
        }
        editor.putInt("jugadores", nombres.size());
        editor.commit();

        Intent intent = new Intent(CrearEquipo.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_equipo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.nuevo_jugador_equipo:
                EditText et = new EditText(context);
                nombres.add(et);
                et.setMinLines(1);
                et.setMaxLines(1);
                et.setHint("Nombre del jugador");
                ll.addView(et);
                break;

            case R.id.guardar_equipo:
                SharedPreferences prefs =
                        getSharedPreferences("Jugadores", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();

                for (Integer i = 0; i < nombres.size(); i++) {
                    if (!nombres.get(i).getText().toString().equals(""))
                        editor.putString("jugador" + i.toString(),
                                nombres.get(i).getText().toString());
                }
                editor.putInt("jugadores", nombres.size());
                editor.commit();

                Intent intent = new Intent(CrearEquipo.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void inicializarEditTexts() {
        SharedPreferences prefs =
                getSharedPreferences("Jugadores",Context.MODE_PRIVATE);

        for(Integer i = 0; i < prefs.getInt("jugadores", 0); i++) {
            if (!prefs.getString("jugador" + i.toString(), "error").equals("")) {
                EditText et = new EditText(context);
                nombres.add(et);
                String s = prefs.getString("jugador" + i.toString(), "error");
                et.setText(s);
                et.setMinLines(1);
                et.setMaxLines(1);
                et.setHint("Nombre del jugador");
                ll.addView(et);
                indice++;
            }
        }
    }
}
