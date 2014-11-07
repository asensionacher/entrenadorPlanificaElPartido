package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    LinearLayout ll;
    ArrayList<Spinner> nombres = new ArrayList<Spinner>();
    Context context;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ll = (LinearLayout)findViewById(R.id.linear_layout_main);
        Spinner spinner = new Spinner(context);
        addNamesOnSpinner(spinner);
        nombres.add(spinner);
        ll.addView(spinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_crear_equipo:
                Intent intent =
                        new Intent(MainActivity.this, CrearEquipo.class);
                startActivity(intent);
                return true;

            case R.id.nuevo_jugador:
                Spinner spinner = new Spinner(context);
                addNamesOnSpinner(spinner);
                nombres.add(spinner);
                ll.addView(spinner);
                return true;

            case R.id.editar_titulares:
                if (nombres.size()>6) {
                    SharedPreferences prefs = getSharedPreferences("PartidoActual",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    for (Integer i = 0; i < nombres.size(); i++) {
                        editor.putString("jugador" + i.toString(),
                                nombres.get(i).getSelectedItem().toString());
                    }
                    editor.putInt("jugadores", nombres.size());
                    editor.commit();
                    Intent intent1 =
                            new Intent(MainActivity.this, Titulares.class);
                    //Iniciamos la nueva actividad
                    startActivity(intent1);
                }

                else {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Tienen que haber mínimo 7 jugadores!", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void addNamesOnSpinner(Spinner spinner) {

        SharedPreferences prefs =
                getSharedPreferences("Jugadores",Context.MODE_PRIVATE);

        List<String> list = new ArrayList<String>();
        for (Integer i = 0; i < prefs.getInt("jugadores", 0); i++) {
            if (!prefs.getString("jugador" + i.toString(), "error").equals("")) {
                list.add(prefs.getString("jugador" + i.toString(), "error"));
            }
        }
        list.add("VACÍO");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed()
    {

    }
}
