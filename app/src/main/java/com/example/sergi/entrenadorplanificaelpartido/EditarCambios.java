package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditarCambios extends Activity {
    Context context = this;
    Integer minutos;
    Integer indice = 0;
    Spinner s1, s2, s3, s4, s5, s6, s7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cambios);

        s1 = (Spinner)findViewById(R.id.spinner_portero_cambio);
        s2 = (Spinner)findViewById(R.id.spinner_lateral_izquierdo_cambio);
        s3 = (Spinner)findViewById(R.id.spinner_central_cambio);
        s4 = (Spinner)findViewById(R.id.spinner_lateral_derecho_cambio);
        s5 = (Spinner)findViewById(R.id.spinner_medio_izquierdo_cambio);
        s6 = (Spinner)findViewById(R.id.spinner_medio_derecho_cambio);
        s7 = (Spinner)findViewById(R.id.spinner_delantero_Cambio);

        SharedPreferences prefs =
                getSharedPreferences("Minutos", Context.MODE_PRIVATE);
        minutos = prefs.getInt("minutos", 0);
        if (minutos == 0) {
            Intent intent =
                    new Intent(EditarCambios.this, Partido.class);
            startActivity(intent);
        }

        setTitle("Cambios en el minuto " + prefs.getString("0","error"));

        setValueToSpinner(s1, "por", "Titulares");
        setValueToSpinner(s2, "dizq", "Titulares");
        setValueToSpinner(s3, "dcen", "Titulares");
        setValueToSpinner(s4, "dder", "Titulares");
        setValueToSpinner(s5, "mizq", "Titulares");
        setValueToSpinner(s6, "mder", "Titulares");
        setValueToSpinner(s7, "del", "Titulares");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editar_cambios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.guardar_cambios:
                SharedPreferences prefs =
                        getSharedPreferences("Minutos", Context.MODE_PRIVATE);
                String saux = prefs.getString(indice.toString(), "error");


                Boolean seguir = true;
                ArrayList<String> aux = new ArrayList<String>();
                aux.add(s1.getSelectedItem().toString());
                aux.add(s2.getSelectedItem().toString());
                aux.add(s3.getSelectedItem().toString());
                aux.add(s4.getSelectedItem().toString());
                aux.add(s5.getSelectedItem().toString());
                aux.add(s6.getSelectedItem().toString());
                aux.add(s7.getSelectedItem().toString());

                for (int i = 0; i < 7; i++) {
                    for (int j = i+1; j < 7; j++) {
                        if (aux.get(i).equals(aux.get(j))) {
                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "No pueden haber dos jugadores iguales",
                                            Toast.LENGTH_SHORT);

                            toast1.show();
                            return true;
                        }
                    }
                }

                if (seguir) {
                    SharedPreferences prefsMinuto = getSharedPreferences(saux,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefsMinuto.edit();

                    editor.clear();
                    editor.putString("por", s1.getSelectedItem().toString());
                    editor.putString("dizq", s2.getSelectedItem().toString());
                    editor.putString("dcen", s3.getSelectedItem().toString());
                    editor.putString("dder", s4.getSelectedItem().toString());
                    editor.putString("mizq", s5.getSelectedItem().toString());
                    editor.putString("mder", s6.getSelectedItem().toString());
                    editor.putString("del", s7.getSelectedItem().toString());
                    editor.commit();

                    indice++;
                    if (indice == minutos) {
                        Intent intent =
                                new Intent(EditarCambios.this, Partido.class);
                        startActivity(intent);
                    }
                    setTitle("Cambios en el minuto " + prefs.getString(indice.toString(), "Final"));
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    void setValueToSpinner(Spinner spinner, String pos, String minuto) {

        SharedPreferences prefs =
                getSharedPreferences(minuto, Context.MODE_PRIVATE);
        SharedPreferences prefsJugadores =
                getSharedPreferences("PartidoActual",Context.MODE_PRIVATE);
        SharedPreferences prefsTitulares =
                getSharedPreferences("Titulares",Context.MODE_PRIVATE);


        List<String> list = new ArrayList<String>();

        String auxPos = prefsTitulares.getString(pos, "error");
        list.add(auxPos);
        for (Integer i = 0; i < prefsJugadores.getInt("jugadores", 0); i++) {
            String aux = prefsJugadores.getString("jugador" + i.toString(), "error");
            if (!aux.equals(auxPos)) {
                list.add(aux);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
