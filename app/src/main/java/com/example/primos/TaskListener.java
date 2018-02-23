package com.example.primos;

/**
 * Created by Indogroup02 on 23/02/2018.
 */

public interface TaskListener {

    void onPreExecute();
    void onProgressUpdate(double progreso);
    void onPostExecute(boolean resultado);
    void onCancelled();

}
