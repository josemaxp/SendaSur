package josemanuel.marin.sendasur.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;

import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.controller.SendaViewModel;
import josemanuel.marin.sendasur.model.Senda;

public class mostrarInfo extends AppCompatActivity {
    SendaViewModel viewModel;
    Button mbutton;
    Senda nsenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senda_info_layout);
        Intent intent = getIntent();

        viewModel =new SendaViewModel(this.getApplication());
        viewModel.getAllSendas().observe(this, new Observer<List<Senda>>() {
            @Override
            public void onChanged(List<Senda> sendas) {
                if (sendas!=null) {
                    for (int i = 0; i < sendas.size(); i++) {
                        if (sendas.get(i).getNombre().equals(intent.getStringExtra(TabMapFragment.EXTRA_MESSAGE))){
                            TextView textViewNombre = findViewById(R.id.sendaTitle);
                            textViewNombre.setText(intent.getStringExtra(TabMapFragment.EXTRA_MESSAGE));

                            TextView tvlongitud = findViewById(R.id.textViewLongitud);
                            tvlongitud.setText(sendas.get(i).getLongitudSenda());

                            TextView tvida = findViewById(R.id.textViewIda);
                            tvida.setText(sendas.get(i).getTiempoIda());

                            TextView tvdificultad = findViewById(R.id.textViewDificultad);
                            tvdificultad.setText(sendas.get(i).getDificultad());

                            TextView tvdescripcion = findViewById(R.id.textViewDesc);
                            tvdescripcion.setText(sendas.get(i).getDescripcion());

                            ImageView iv = findViewById(R.id.imageView);
                            Picasso.get().load(sendas.get(i).getImagen()).into(iv);

                            nsenda = sendas.get(i);
                        }
                    }
                }
            }
        });
    }

    public void addOrEliminateFav(View view) {
        mbutton = findViewById(R.id.buttonFav);
        viewModel.insert(nsenda);
        Toast.makeText(getApplication(), "La ruta "+nsenda.getNombre().toLowerCase()+" ha sido añadida a favoritos", Toast.LENGTH_LONG).show();
    }
}
