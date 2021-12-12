package josemanuel.marin.sendasur.model;

import android.app.Application;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class VolleyRequest {
    Application application;
    private static final String USGS_REQUEST_URL = "https://josemaxp.github.io/jsonapi/senderoscadiz.json";

    public VolleyRequest(Application application) {
        this.application = application;
    }

    List<Senda> loadSendas() {

        List<Senda> sendas = new ArrayList<>();

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
                        List<Senda> prueba = JSONReader.extractFeatureFromJson(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        return sendas;

    }
}
