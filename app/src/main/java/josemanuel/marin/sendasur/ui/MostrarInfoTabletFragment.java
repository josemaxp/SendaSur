package josemanuel.marin.sendasur.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;

import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.controller.SendaViewModel;
import josemanuel.marin.sendasur.model.Senda;

public class MostrarInfoTabletFragment extends Fragment implements View.OnClickListener {
    SendaViewModel viewModel;
    Button mbutton;
    static Senda nsenda;
    static String title;

    public MostrarInfoTabletFragment(){}

    public static MostrarInfoTabletFragment newInstance(String ntitle){
        MostrarInfoTabletFragment fragment =new MostrarInfoTabletFragment();

        title = ntitle;
        Bundle arguments = new Bundle();
        arguments.putSerializable(Senda.SENDA_ID_KEY, nsenda);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel =new SendaViewModel(getActivity().getApplication());
        viewModel.getAllSendas().observe(this, new Observer<List<Senda>>() {
            @Override
            public void onChanged(List<Senda> sendas) {
                if (sendas!=null) {
                    for (int i = 0; i < sendas.size(); i++) {
                        if (sendas.get(i).getNombre().equals(title)){
                            nsenda = sendas.get(i);
                        }
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.senda_info_layout,container,false);

        if (nsenda !=null){
            ((TextView) rootView.findViewById(R.id.sendaTitle)).setText(nsenda.getNombre());
            ((TextView) rootView.findViewById(R.id.textViewLongitud)).setText(nsenda.getLongitudSenda());
            ((TextView) rootView.findViewById(R.id.textViewIda)).setText(nsenda.getTiempoIda());
            ((TextView) rootView.findViewById(R.id.textViewDificultad)).setText(nsenda.getDificultad());
            ((TextView) rootView.findViewById(R.id.textViewDesc)).setText(nsenda.getDescripcion());
            ((ImageView) rootView.findViewById(R.id.imageView4)).setImageResource(R.drawable.ic_baseline_add_road_24);
            ((ImageView) rootView.findViewById(R.id.imageView3)).setImageResource(R.drawable.ic_baseline_access_time_24);
            ((ImageView) rootView.findViewById(R.id.imageView2)).setImageResource(R.drawable.ic_baseline_hiking_24);
            ImageView iv = rootView.findViewById(R.id.imageView);
            Picasso.get().load(nsenda.getImagen()).into(iv);
        }
        mbutton = rootView.findViewById(R.id.buttonFav);
        mbutton.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        viewModel.insert(nsenda);
        Toast.makeText(getActivity().getApplication(), "La ruta "+nsenda.getNombre().toLowerCase()+" ha sido añadida a favoritos", Toast.LENGTH_LONG).show();
    }
}
