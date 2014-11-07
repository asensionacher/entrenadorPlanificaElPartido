package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Titulares extends Activity {

    Spinner s1,s2,s3,s4,s5,s6,s7;
    Button cambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulares);

        s1 = (Spinner)findViewById(R.id.spinner_portero);
        s2 = (Spinner)findViewById(R.id.spinner_lateral_izquierdo);
        s3 = (Spinner)findViewById(R.id.spinner_central);
        s4 = (Spinner)findViewById(R.id.spinner_lateral_derecho);
        s5 = (Spinner)findViewById(R.id.spinner_medio_izquierdo);
        s6 = (Spinner)findViewById(R.id.spinner_medio_derecho);
        s7 = (Spinner)findViewById(R.id.spinner_delantero);

        addNamesOnSpinner(s1);
        addNamesOnSpinner(s2);
        addNamesOnSpinner(s3);
        addNamesOnSpinner(s4);
        addNamesOnSpinner(s5);
        addNamesOnSpinner(s6);
        addNamesOnSpinner(s7);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_titulares, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.ir_editar_minuto:
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
                            seguir = false;
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
                    SharedPreferences prefs =
                            getSharedPreferences("Titulares", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.putString("por", s1.getSelectedItem().toString());
                    editor.putString("dizq", s2.getSelectedItem().toString());
                    editor.putString("dcen", s3.getSelectedItem().toString());
                    editor.putString("dder", s4.getSelectedItem().toString());
                    editor.putString("mizq", s5.getSelectedItem().toString());
                    editor.putString("mder", s6.getSelectedItem().toString());
                    editor.putString("del", s7.getSelectedItem().toString());
                    editor.commit();

                    Intent intent =
                            new Intent(Titulares.this, EditarMinutosCambios.class);
                    startActivity(intent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void addNamesOnSpinner(Spinner spinner) {

        SharedPreferences prefs =
                getSharedPreferences("PartidoActual", Context.MODE_PRIVATE);
        List<String> list = new ArrayList<String>();
        for (Integer i = 0; i < prefs.getInt("jugadores", 0); i++) {
            list.add(prefs.getString("jugador" + i.toString(), "error"));
        }
        list.add("VACÍO");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent =
                new Intent(Titulares.this, MainActivity.class);
        startActivity(intent);
    }

}
