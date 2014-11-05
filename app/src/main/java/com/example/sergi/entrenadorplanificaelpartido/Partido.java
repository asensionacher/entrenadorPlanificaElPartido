package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Partido extends Activity {

    Chronometer myChronometer;
    Button button;
    Boolean start = true;
    private long lastPause;
    Boolean first = true;
    TextView tv, tvt;
    Boolean primeraParte = true;
    Boolean fin = false;
    ArrayList<Cambio> arrayCambios = new ArrayList<Cambio>();
    ArrayList<String> arrayTitulares = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);
        myChronometer = (Chronometer)findViewById(R.id.chronometer_partido);
        button = (Button)findViewById(R.id.button_start_finish_match);
        tv = (TextView)findViewById(R.id.textView_changes);
        tvt = (TextView)findViewById(R.id.textView_campo);

        Bundle bundle = this.getIntent().getExtras();
        getChanges(bundle);
        initializeButton();
        initializeTitulares(bundle);
        initializeChronometer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    void initializeChronometer() {
        myChronometer.setOnChronometerTickListener(
                new Chronometer.OnChronometerTickListener() {

                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        // TODO Auto-generated method stub
                        long milliseconds = SystemClock.elapsedRealtime() - myChronometer.getBase();
                        Integer seconds = (int) (milliseconds / 1000) % 60;
                        Integer minutes = (int) ((milliseconds / (1000 * 60)) % 60);
                        if (minutes == 25 && primeraParte) {
                            primeraParte = false;
                            button.setText("Empezar segunda parte");
                            lastPause = SystemClock.elapsedRealtime();
                            myChronometer.stop();
                            start = true;

                        }
                        if (minutes == 50) {
                            myChronometer.stop();
                            button.setText("Fin del partido");
                            fin = true;
                        }

                        for (Integer i = 0; i < arrayCambios.size(); i++) {
                            Integer minutesCinco = minutes + 5;

                            if (arrayCambios.get(i).getMinuto().equals(minutesCinco.toString())
                                    && !arrayCambios.get(i).getAvisado()) {
                                Log.d("Aviso", "5Minutos");
                                Log.d("Avisado", arrayCambios.get(i).getAvisado().toString());

                                tv.setText(tv.getText() + "\n-Dentro de cinco minutos: " +
                                        arrayCambios.get(i).getEntra() + " entra por " +
                                        arrayCambios.get(i).getSale() +
                                        " en el minuto " + arrayCambios.get(i).getMinuto().toString());
                                arrayCambios.get(i).setAvisado(true);
                                Log.d("Avisadoo", arrayCambios.get(i).getAvisado().toString());
                            }

                            if (arrayCambios.get(i).getMinuto().equals(minutes.toString())
                                    && !arrayCambios.get(i).getEscrito()) {
                                Log.d("introducir", "ENTRO");

                                tv.setText(tv.getText() +
                                        "\n-" + arrayCambios.get(i).getEntra() + " entra por " +
                                        arrayCambios.get(i).getSale()
                                        + " en el minuto " + arrayCambios.get(i).getMinuto().toString());
                                arrayCambios.get(i).setEscrito(true);
                                cambiarTitular(arrayCambios.get(i).getEntra(),
                                        arrayCambios.get(i).getSale());
                            }
                        }
                    }
                }
        );
    }

    void initializeButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fin) {
                    if (start) {
                        if (first) {
                            myChronometer.setBase(SystemClock.elapsedRealtime());
                            myChronometer.start();
                            first = false;
                        } else {
                            myChronometer.setBase(myChronometer.getBase() +
                                    SystemClock.elapsedRealtime() - lastPause);
                            myChronometer.start();
                        }
                        start = false;
                        button.setText("Parar cronometro");
                    } else {
                        button.setText("Reanudar partido");
                        lastPause = SystemClock.elapsedRealtime();
                        myChronometer.stop();
                        start = true;
                    }
                }
            }
        });
    }

    void getChanges(Bundle b) {
        for (Integer i = 0; i < b.getInt("numero"); i++) {
            Cambio cambio = new Cambio(b.getString("entra"+i.toString()),
                    b.getString("sale"+i.toString()),
                    b.getString("minuto"+i.toString()));
            arrayCambios.add(cambio);
        }
        for (Integer i = 0; i < arrayCambios.size(); i++) {
            Log.d("Entra " + i.toString(), arrayCambios.get(i).getEntra());
            Log.d("Sale " + i.toString(), arrayCambios.get(i).getSale());
            Log.d("Minuto " + i.toString(), arrayCambios.get(i).getMinuto());
        }
    }

    void initializeTitulares(Bundle b) {
        tvt.setText("En el campo");
        for (Integer i = 0; i < 7; i++) {
            tvt.setText(tvt.getText() + "\n" + b.getString("titular"+i.toString()));
            arrayTitulares.add(b.getString("titular"+i.toString()));
        }
    }

    void cambiarTitular(String entra, String sale) {
        tvt.setText("En el campo");
        for (Integer i = 0; i < 7; i++) {
            if (sale.equals(arrayTitulares.get(i))) {
                arrayTitulares.set(i, entra);
            }
            tvt.setText(tvt.getText() + "\n" + arrayTitulares.get(i));

        }
    }
}
