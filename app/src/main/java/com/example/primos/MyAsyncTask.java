package com.example.primos;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Indogroup02 on 23/02/2018.
 */

public class MyAsyncTask extends AsyncTask<Long, Double, Boolean> {

    private final static String TAG = MyAsyncTask.class.getName();
    private final TaskListener listener;


    public MyAsyncTask(TaskListener listener){
        this.listener = listener;
    }

    @Override
    protected void onPreExecute(){
//        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onPreExecute()");
//        resultField.setText("");
//        primecheckbutton.setText("CANCELAR");
//        Log.d(TAG, "Estado de onPreExecute() " + myAsyncTask.getStatus().toString());


        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onPreExecute()");
        listener.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Long... n){
        if(isCancelled()){
            return null;
        }


        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": Comienza doInBackground");
        long numComprobar = n[0];
        if(numComprobar < 2 || numComprobar % 2 == 0)
            return false;
        double limite = Math.sqrt(numComprobar) + 0.0001;
        double progreso = 0;
        for(long factor = 3; factor < limite && !isCancelled(); factor += 2){
            if(numComprobar % factor == 0){
                return false;
            }
            if (factor > limite * progreso / 100){
                publishProgress(progreso / 100);
                progreso += 5;
            }
        }
        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": Finaliza doInBackground");
//        Log.d(TAG, "Estado de doInBackground " + myAsyncTask.getStatus().toString());
        return true;
    }

    @Override
    protected void onProgressUpdate(Double... progress){
//        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
//        resultField.setText(String.format("%.1f%% completed", progress[0]*100));
//        Log.d(TAG, "Estado de onProgressUpdate() " + myAsyncTask.getStatus().toString());

        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
        listener.onProgressUpdate(progress[0]);

    }

    @Override
    protected void onPostExecute(Boolean isPrime){
//        if(!isCancelled()){
//            Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onPostExecute()");
//            resultField.setText(isPrime + "");
//            primecheckbutton.setText("¿ES PRIMO?");
//        } else{
//            resultField.setText("Proceso cancelado");
//            primecheckbutton.setText("¿ES PRIMO?");
//        }
//        Log.d(TAG, "Estado de onPostExecute() " + myAsyncTask.getStatus().toString());

        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onPostExecute()");
        listener.onPostExecute(isPrime);

    }

    @Override
    protected  void onCancelled(){
//        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onCancelled");
//        super.onCancelled();
//        resultField.setText("Proceso cancelado");
//        primecheckbutton.setText("¿ES PRIMO?");
//        Log.d(TAG, "Estado de onCancelled() " + myAsyncTask.getStatus().toString());

        Log.d(TAG, "Thread " + Thread.currentThread().getId() + ": onCancelled");
        listener.onCancelled();
        super.onCancelled();

    }




}
