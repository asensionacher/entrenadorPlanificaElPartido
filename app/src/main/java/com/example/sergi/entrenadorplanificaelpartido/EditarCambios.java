package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;


public class EditarCambios extends Activity {
    Button anadir_cambio;
    Button empezar_partido;
    Context context = this;
    LinearLayout ll;
    float index = 1.0f;
    Integer number = 0;
    ArrayList<Spinner> sale = new ArrayList<Spinner>();
    ArrayList<Spinner> entra = new ArrayList<Spinner>();
    ArrayList<Spinner> minuto = new ArrayList<Spinner>();
    Spinner spinner, spinner1, spinner2;
    String player0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cambios);

        empezar_partido = (Button)findViewById(R.id.button_empezar_partido);
        anadir_cambio = (Button)findViewById(R.id.button_anadir_cambio);
        ll = (LinearLayout)findViewById(R.id.linear_layout_editar_cambios);

        inicializateAddChanges();
        Bundle bundle = this.getIntent().getExtras();
        inicializateStartMatch(bundle);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addNamesOnSpinner(Spinner spinner) {
        Bundle bundle = this.getIntent().getExtras();
        List<String> list = new ArrayList<String>();
        player0 = bundle.getString("0");
        for (Integer i = 0; i < bundle.getInt("numero"); i++) {
            list.add(bundle.getString(i.toString()));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
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

    void inicializateAddChanges() {
        final int aux = number;
        anadir_cambio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                inicializateSpinner(0);
                inicializateSpinner(1);
                inicializateSpinner(2);
                index+=1.0f;
                number++;
            }
        });
    }

    void inicializateStartMatch(final Bundle bundle) {
        empezar_partido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(EditarCambios.this, Partido.class);
                Bundle b = new Bundle();
                b.putInt("numero",entra.size());
                for (Integer i = 0; i < entra.size(); i++) {
                    b.putString("entra" + i.toString(), entra.get(i).getSelectedItem().toString());
                    b.putString("minuto" + i.toString(), minuto.get(i).getSelectedItem().toString());
                    b.putString("sale" + i.toString(),sale.get(i).getSelectedItem().toString());
                }
                for (Integer i = 0; i < 7; i++) {
                    b.putString("titular" + i.toString(), bundle.getString(i.toString()));
                }

                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }

    void inicializateSpinner(int i) {
        spinner = new Spinner(context);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, index);
        lp.gravity= Gravity.LEFT;
        lp.weight=index;

        if (i == 0)  {
            lp.gravity= Gravity.LEFT;
            addNamesOnSpinner(spinner);
            sale.add(spinner);
        }
        else if (i == 1)  {
            lp.gravity= Gravity.CENTER;
            addMinutesOnSpinner(spinner);
            minuto.add(spinner);
        }
        else if(i == 2) {
            lp.gravity= Gravity.RIGHT;
            addNamesOnSpinner(spinner);
            entra.add(spinner);
        }
        spinner.setLayoutParams(lp);
        ll.addView(spinner);
    }
}
