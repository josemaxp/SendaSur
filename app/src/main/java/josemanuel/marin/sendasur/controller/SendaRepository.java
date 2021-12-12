package josemanuel.marin.sendasur.controller;

import android.app.Application;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import josemanuel.marin.sendasur.model.JSONReader;
import josemanuel.marin.sendasur.model.Senda;
import josemanuel.marin.sendasur.model.SendaDAO;
import josemanuel.marin.sendasur.model.SendaRoomDatabase;

public class SendaRepository {
    MutableLiveData<List<Senda>> sendas;
    private SendaDAO mSendaDao;
    private LiveData<List<Senda>> allSendas;

    public SendaRepository(Application application) {
        SendaRoomDatabase db = SendaRoomDatabase.getDatabase(application);
        mSendaDao = db.sendaDao();
        allSendas = mSendaDao.getAllSendas();
        this.application = application;
    }

    Application application;
    private static final String USGS_REQUEST_URL = "https://josemaxp.github.io/jsonapi/senderoscadiz.json";


    public LiveData<List<Senda>> getSendas(){
        if (sendas==null){
            sendas= new MutableLiveData<>();
            cargaSendasVolley();
        }

        return sendas;
    }

    private void cargaSendasVolley() {

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", "3");
        uriBuilder.appendQueryParameter("orderby", "magnitude");
        final RequestQueue requestQueue = Volley.newRequestQueue(application);
        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.build().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sendas.postValue(JSONReader.extractFeatureFromJson(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("SendaRepository", "Error en la carga de volley"+ error.getMessage());
                    }
                });
        requestQueue.add(request);
    }

    LiveData<List<Senda>> getAllWords() {return allSendas;}

    public void insert (Senda senda) {
        new insertAsyncTask(mSendaDao).execute(senda);
    }

    public void deleteSenda(Senda senda)  {
        new deleteWordAsyncTask(mSendaDao).execute(senda);
    }

    private static class insertAsyncTask extends AsyncTask<Senda, Void, Void> {

        private SendaDAO mAsyncTaskDao;

        insertAsyncTask(SendaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public Void doInBackground(final Senda... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        public void onPostExecute(Void unused) {

        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Senda, Void, Void> {
        private SendaDAO mAsyncTaskDao;

        deleteWordAsyncTask(SendaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Senda... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }



}
