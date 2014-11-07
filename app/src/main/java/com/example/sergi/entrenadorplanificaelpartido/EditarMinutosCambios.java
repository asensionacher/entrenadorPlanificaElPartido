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

import java.util.ArrayList;
import java.util.List;


public class EditarMinutosCambios extends Activity {

    LinearLayout ll;
    ArrayList<Spinner> minutos = new ArrayList<Spinner>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_minutos_cambios);

        ll = (LinearLayout)findViewById(R.id.LinearLayout_minutos);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_minutos_cambios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.anadir_minuto:
                Spinner s = new Spinner(context);
                addMinutesOnSpinner(s);
                minutos.add(s);
                ll.addView(s);
                return true;

            case R.id.editar_cambios:
                SharedPreferences prefs =
                        getSharedPreferences("Minutos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();

                for (Integer i = 0; i < minutos.size(); i++) {
                    editor.putString(i.toString(), minutos.get(i).getSelectedItem().toString());
                }
                editor.putInt("minutos", minutos.size());
                editor.commit();

                Intent intent =
                        new Intent(EditarMinutosCambios.this, EditarCambios.class);
                startActivity(intent);

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void addMinutesOnSpinner(Spinner spinner) {
        List<String> list = new ArrayList<String>();
        for (Integer i = 1; i < 51; i++) {
            list.add(i.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
