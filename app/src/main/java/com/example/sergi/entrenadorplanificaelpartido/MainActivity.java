package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainActivity extends Activity {

    Button anadir_jugador;
    Button editar_cambios;
    LinearLayout ll;
    ArrayList<String> nombres;
    Context context;
    Integer indice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        nombres = new ArrayList();

        anadir_jugador = (Button)findViewById(R.id.button_anadir_jugador);
        ll = (LinearLayout)findViewById(R.id.linear_layout_main);
        editar_cambios = (Button)findViewById(R.id.button_elegir_titulares);

        anadir_jugador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText et = new EditText(context);
                final int aux = indice;
                et.setId(indice);
                nombres.add(indice, null);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        nombres.set(aux, s.toString());
                    }
                });
                et.setMinLines(1);
                et.setMaxLines(1);
                et.setHint("Nombre del jugador");
                ll.addView(et);
                indice++;
            }
        });

        editar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nombres.size()>6) {
                    Intent intent =
                            new Intent(MainActivity.this, EditarCambios.class);
                    Bundle b = new Bundle();
                    b.putInt("numero", nombres.size());
                    for (Integer i = 0; i < nombres.size(); i++) {
                        b.putString(i.toString(), nombres.get(i));
                        Log.d("Numero" + i.toString(), nombres.get(i));
                    }
                    intent.putExtras(b);

                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }
            }
        });
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
        return super.onOptionsItemSelected(item);
    }
}
