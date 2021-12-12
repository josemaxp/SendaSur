package josemanuel.marin.sendasur.model;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JSONReader {
    public static List<Senda> extractFeatureFromJson(String sendasJSON) {
        if (TextUtils.isEmpty(sendasJSON)) {
            return null;
        }

        List<Senda> sendas = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(sendasJSON);

            JSONArray sendasArray = baseJsonResponse.getJSONArray("records");

            for (int i = 0; i < sendasArray.length(); i++) {

                JSONObject currentSenda = sendasArray.getJSONObject(i);

                try {
                    int id = currentSenda.getInt("id");
                    String nombre = currentSenda.getString("NOMBRE");

                    String latitud = currentSenda.getString("LATITUDE");
                    double latitudNum = Double.parseDouble(latitud);

                    String longitud = currentSenda.getString("LONGITUDE");
                    double longitudNum = Double.parseDouble(longitud);

                    String longitudSenda = currentSenda.getString("LONGITUD");
                    String tiempoIda = currentSenda.getString("TIEMPO DE IDA");
                    String dificultad = currentSenda.getString("DIFICULTAD");
                    String descripcion = currentSenda.getString("DESCRIPCION");
                    String imagen = currentSenda.getString("IMAGEN");

                    sendas.add(new Senda(id,nombre, latitudNum, longitudNum,longitudSenda,tiempoIda,dificultad,descripcion,imagen));
                }catch (NumberFormatException e){}
                }


        } catch (JSONException e) {
            Log.e("JSonReader", "Problem parsing the sendas JSON results", e);
        }

        return sendas;
    }

}

