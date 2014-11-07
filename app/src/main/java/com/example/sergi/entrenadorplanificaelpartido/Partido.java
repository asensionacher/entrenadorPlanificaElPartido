package com.example.sergi.entrenadorplanificaelpartido;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Partido extends Activity {
    // TODO: VACIAR SHARED PREFERENCES AL FINAL! OnbackPressed preguntar si quieres acabar
    // TODO: Y llevar a mainactivity
    int NOTIF_ALERTA_ID = 0;
    Chronometer myChronometer;
    Boolean start = true;
    private long lastPause;
    Boolean first = true;
    Boolean primeraParte = true;
    Boolean fin = false;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ArrayList<Cambio> cambios = new ArrayList<Cambio>();
    ArrayList<Integer> minutos = new ArrayList<Integer>();
    Drawable background;
    Boolean dos = false;

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
        titulares();
        minuto();
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

                        //CAMBIOS
                        for (int i = 0; i < cambios.size(); i++) {

                            String mCambios = cambios.get(i).getMinuto();
                            String pos = cambios.get(i).getPos();
                            String entra = cambios.get(i).getEntra();
                            Boolean avisado = cambios.get(i).getAvisado();
                            String sale = cambios.get(i).getSale();
                            Boolean notificado = cambios.get(i).getNotificado();

                            if (minutes.toString().equals(mCambios)
                                    && !cambios.get(i).getEscrito()) {

                                if (pos.equals("por")) {
                                    tv1.setText(entra);
                                    tv1.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("dizq")) {
                                    tv2.setText(entra);
                                    tv2.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("dcen")) {
                                    tv3.setText(entra);
                                    tv3.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("dder")) {
                                    tv4.setText(entra);
                                    tv4.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("mizq")) {
                                    tv5.setText(entra);
                                    tv5.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("mder")) {
                                    tv6.setText(entra);
                                    tv6.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                } else if (pos.equals("del")) {
                                    tv7.setText(entra);
                                    tv7.setTextColor(Color.BLACK);
                                    if (!notificado) {
                                        notificacionCambio(entra, sale);
                                        cambios.get(i).setNotificado(true);
                                    }
                                }

                            }
                            Integer mICambios = Integer.parseInt(mCambios);
                            if (minutes + 5 == mICambios || minutes + 4 == mICambios ||
                                    minutes + 3 == mICambios || minutes + 2 == mICambios
                                    || minutes + 1 == mICambios) {

                                if (pos.equals("por")) {
                                    tv1.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv1.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv1.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("dizq")) {
                                    tv2.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv2.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv2.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("dcen")) {
                                    tv3.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv3.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv3.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("dder")) {
                                    tv4.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv4.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv4.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("mizq")) {
                                    tv5.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv5.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv5.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("mder")) {
                                    tv6.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv6.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv6.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                } else if (pos.equals("del")) {
                                    tv7.setText(entra + " x " + sale + " Min" +
                                            mCambios);
                                    if (avisado) {
                                        tv7.setTextColor(Color.YELLOW);
                                        cambios.get(i).setAvisado(false);
                                    } else {
                                        tv7.setTextColor(Color.RED);
                                        cambios.get(i).setAvisado(true);
                                    }
                                }
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
        String por = tv1.getText().toString();
        String dizq = tv2.getText().toString();
        String dcen = tv3.getText().toString();
        String dder = tv4.getText().toString();
        String mizq = tv5.getText().toString();
        String mder = tv6.getText().toString();
        String del = tv7.getText().toString();

        for (Integer i = 0; i < prefs.getInt("minutos", 0); i++) {
            String mCambio = prefs.getString(i.toString(), "ERROR");
            Log.d("Minuto", mCambio);
            minutos.add(Integer.parseInt(mCambio));

            SharedPreferences cPrefs =
                    getSharedPreferences(mCambio, Context.MODE_PRIVATE);

            String jugador = cPrefs.getString("por", "error");
            if (!jugador.equals(por)) {
                Log.d("por", "Minuto = " + mCambio + " entra -> " + jugador +
                " sale -> " + por);
                Cambio cambio = new Cambio(jugador, por, mCambio, "por");
                cambios.add(cambio);
                por = jugador;
            }

            jugador = cPrefs.getString("dizq", "error");
            if (!jugador.equals(dizq)) {
                Log.d("dizq", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + dizq);
                Cambio cambio = new Cambio(jugador, dizq, mCambio, "dizq");
                cambios.add(cambio);
                dizq = jugador;
            }

            jugador = cPrefs.getString("dcen", "error");
            if (!jugador.equals(dcen)) {
                Log.d("dcen", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + dcen);
                Cambio cambio = new Cambio(jugador, dcen, mCambio, "dcen");
                cambios.add(cambio);
                dcen = jugador;
            }

            jugador = cPrefs.getString("dder", "error");
            if (!jugador.equals(dder)) {
                Log.d("dder", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + dder);
                Cambio cambio = new Cambio(jugador, dder, mCambio, "dder");
                cambios.add(cambio);
                dder = jugador;
            }

            jugador = cPrefs.getString("mizq", "error");
            if (!jugador.equals(mizq)) {
                Log.d("mizq", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + mizq);
                Cambio cambio = new Cambio(jugador, mizq, mCambio, "mizq");
                cambios.add(cambio);
                mizq = jugador;
            }

            jugador = cPrefs.getString("mder", "error");
            if (!jugador.equals(mder)) {
                Log.d("mder", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + mder);
                Cambio cambio = new Cambio(jugador, mder, mCambio, "mder");
                cambios.add(cambio);
                mder = jugador;
            }

            jugador = cPrefs.getString("del", "error");
            if (!jugador.equals(del)) {
                Log.d("del", "Minuto = " + mCambio + " entra -> " + jugador +
                        " sale -> " + del);
                Cambio cambio = new Cambio(jugador, del, mCambio, "del");
                cambios.add(cambio);
                del = jugador;
            }


        }
    }

    @Override
    public void onBackPressed()
    {
        if (!dos) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Dale otra vez para salr del partido", Toast.LENGTH_SHORT);

            toast1.show();
            dos = true;
        }

        else if (dos) {
            Intent intent = new Intent(Partido.this, MainActivity.class);
            startActivity(intent);
        }
    }

    void notificacionCambio(String entra, String sale) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(Partido.this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon((((BitmapDrawable)getResources()
                                .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                        .setContentTitle("Cambio")
                        .setContentText("Cambio, entra " + entra + " y sale " + sale)
                        .setContentInfo("4")
                        .setTicker("Cambio");
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
        NOTIF_ALERTA_ID++;
    }

}
