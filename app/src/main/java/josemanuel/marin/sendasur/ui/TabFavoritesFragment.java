package josemanuel.marin.sendasur.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.controller.FavoritesAdapter;
import josemanuel.marin.sendasur.controller.SendaViewModel;
import josemanuel.marin.sendasur.model.Senda;

public class TabFavoritesFragment extends Fragment{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FavoritesAdapter mAdapter;
    List<Senda> sendas = Senda.SENDA_LIST;
    SendaViewModel sw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment,container,false);

        mAdapter = new FavoritesAdapter(sendas,getContext());

        recyclerView = view.findViewById(R.id.reciclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        sw = new SendaViewModel(getActivity().getApplication());
        sw.getFavoritesSendas().observe(getActivity(), new Observer<List<Senda>>() {
            @Override
            public void onChanged(List<Senda> sendas) {
                mAdapter.setSendas(sendas);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        Senda senda = mAdapter.getSendaAtPosition(position);
                        Toast.makeText(getActivity().getApplication(), "La ruta "+senda.getNombre().toLowerCase() + " ha sido eliminada de favoritos", Toast.LENGTH_LONG).show();

                        sw.deleteSenda(senda);
                    }


                });

        helper.attachToRecyclerView(recyclerView);

        return view;
    }
}
