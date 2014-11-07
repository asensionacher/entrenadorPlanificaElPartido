package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;


public class Partido extends Activity {
    // TODO: VACIAR SHARED PREFERENCES AL FINAL! OnbackPressed preguntar si quieres acabar
    // TODO: Y llevar a mainactivity

    Chronometer myChronometer;
    Boolean start = true;
    private long lastPause;
    Boolean first = true;
    Boolean primeraParte = true;
    Boolean fin = false;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ArrayList<Integer> minutos = new ArrayList<Integer>();
    Drawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);
        myChronometer = (Chronometer)findViewById(R.id.chronometer_partido);
        tv1 = (TextView)findViewById(R.id.tv_portero);
        tv2 = (TextView)findViewById(R.id.tv_lateral_izquierdo);
        tv3 = (TextView)findViewById(R.id.tv_central);
        tv4 = (TextView)findViewById(R.id.tv_lateral_derecho);
        tv5 = (TextView)findViewById(R.id.tv_medio_izquierdo);
        tv6 = (TextView)findViewById(R.id.tv_medio_derecho);
        tv7 = (TextView)findViewById(R.id.tv_delantero);
        background = tv1.getBackground();

        initializeChronometer();
        minuto();
        titulares();
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
        switch (item.getItemId()) {
            case R.id.ini_partido:
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
                    } else {
                        lastPause = SystemClock.elapsedRealtime();
                        myChronometer.stop();
                        start = true;
                    }
                }
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void initializeChronometer() {
        myChronometer.setOnChronometerTickListener(
                new Chronometer.OnChronometerTickListener() {

                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        long milliseconds = SystemClock.elapsedRealtime() - myChronometer.getBase();
                        Integer seconds = (int) (milliseconds / 1000) % 60;
                        Integer minutes = (int) ((milliseconds / (1000 * 60)) % 60);
                        if (minutes == 25 && primeraParte) {
                            primeraParte = false;
                            lastPause = SystemClock.elapsedRealtime();
                            myChronometer.stop();
                            start = true;

                        }
                        if (minutes == 50) {
                            myChronometer.stop();
                            fin = true;
                            //TODO: Vaciar shared preferences
                        }
                        for (int i = 0; i < minutos.size(); i++) {
                            if (minutes == minutos.get(i)) {
                                //TODO: Coger cambios del minuto
                                //TODO: añadirlo en el tv correspondiente
                                //TODO: color texto negro
                            }

                            if (minutes - 5 == minutos.get(i)) {
                                //TODO: En el tv, entra x sale y con colores parpadeando el texto
                                // TODO: durante los 5 minutos cada segundo
                            }
                        }

                    }
                }
        );
    }

    void titulares() {
        SharedPreferences prefs =
                getSharedPreferences("Titulares", Context.MODE_PRIVATE);
        tv1.setText(prefs.getString("por", "error"));
        tv2.setText(prefs.getString("dizq", "error"));
        tv3.setText(prefs.getString("dcen", "error"));
        tv4.setText(prefs.getString("dder", "error"));
        tv5.setText(prefs.getString("mizq", "error"));
        tv6.setText(prefs.getString("mder", "error"));
        tv7.setText(prefs.getString("del", "error"));
    }

    void minuto() {
        SharedPreferences prefs =
                getSharedPreferences("Minutos", Context.MODE_PRIVATE);
        for (Integer i = 0; i < prefs.getInt("minutos", 0); i++) {
            minutos.add(Integer.parseInt(prefs.getString(i.toString(), "0")));
        }
    }

}
