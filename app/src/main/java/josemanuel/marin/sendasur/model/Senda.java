package josemanuel.marin.sendasur.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "senda_table")
public class Senda implements Serializable{
    public static final String SENDA_ID_KEY = "senda_id";
    public static  final List<Senda> SENDA_LIST =  new ArrayList<>();

    @PrimaryKey
    int id;

    @NonNull
    @ColumnInfo(name = "sendaName")
    String nombre;

    double latitud;
    double longitud;
    String longitudSenda;
    String tiempoIda;
    String dificultad;
    String descripcion;
    String imagen;

    public Senda(int id, String nombre, double latitud, double longitud, String longitudSenda, String tiempoIda, String dificultad, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.longitudSenda = longitudSenda;
        this.tiempoIda = tiempoIda;
        this.dificultad = dificultad;
        this.descripcion = descripcion;
        this.imagen = imagen;
        SENDA_LIST.add(this);
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getLongitudSenda() {
        return longitudSenda;
    }

    public String getTiempoIda() {
        return tiempoIda;
    }

    public String getDificultad() {
        return dificultad;
    }

    public String getDescripcion() { return descripcion; }

    public String getImagen() { return imagen; }
}
