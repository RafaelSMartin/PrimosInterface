package com.example.primos;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TaskListener {

    private static final String TAG = MainActivity.class.getName();

    private EditText inputField, resultField;
    private Button primecheckbutton;
    private MyAsyncTask myAsyncTask;
//    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = (EditText) findViewById(R.id.inputField);
        resultField = (EditText) findViewById(R.id.resultField);
        primecheckbutton = (Button) findViewById(R.id.primecheckbutton);


    }

    public void triggerPrimecheck(View v){
        if (myAsyncTask == null || myAsyncTask.getStatus() != AsyncTask.Status.FINISHED){
//            isRunning = true;
            Log.d(TAG, "Tread " + Thread.currentThread().getId() + ": Comienza triggerPrimecheck");
            long parameter = Long.parseLong(inputField.getText().toString());
            myAsyncTask = new MyAsyncTask(this);
            myAsyncTask.execute(parameter);
            Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": triggerPrimecheck termina");
        } else if (myAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
//            isRunning = false;
            Log.d(TAG, "Cancelando test" + Thread.currentThread().getId());
            myAsyncTask.cancel(true);
        }
        Log.d(TAG, "Estado de triggerPrimecheckFinal " + myAsyncTask.getStatus().toString());

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
//            isRunning = false;
            Log.d(TAG, "Cancelando test" + Thread.currentThread().getId());
            myAsyncTask.cancel(true);
        }
//        Log.d(TAG, "Estado de onPause() " + myAsyncTask.getStatus().toString());
    }


    @Override
    public void onPreExecute() {
        lockScreenOrientation();
        resultField.setText("");
        primecheckbutton.setText("CANCELAR");
    }

    @Override
    public void onProgressUpdate(double progreso) {
        resultField.setText(String.format("%.1f%% completado", progreso*100));
    }

    @Override
    public void onPostExecute(boolean resultado) {
        unlockScreenOrientation();
        resultField.setText(resultado + "");
        primecheckbutton.setText("¿ES PRIMO?");

    }

    @Override
    public void onCancelled() {
        unlockScreenOrientation();
        resultField.setText("Proceso cancelado");
        primecheckbutton.setText("¿ES PRIMO?");

    }


    private void lockScreenOrientation() {
        int currentOrientation= getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }


}
