package josemanuel.marin.sendasur.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.model.Senda;
import josemanuel.marin.sendasur.ui.mostrarInfo;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>{
    private List<Senda> sendas;
    private Context context;
    public static final String EXTRA_MESSAGE = "josemanuel.marin.sendasur.extra.MESSAGE";

    public FavoritesAdapter(List<Senda> sendas, Context context) {
        this.sendas = sendas;
        this.context = context;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favorites_list, parent, false);
        return new FavViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Senda mSenda = sendas.get(position);
        holder.nombreSendaView.setText(mSenda.getNombre());
        holder.descripcionSendaView.setText(mSenda.getDescripcion());
    }

    @Override
    public int getItemCount() {
         if(sendas != null){
            return sendas.size();
        }else{
             return 0;
         }
    }

    public void setSendas(List<Senda> Sendas){
        sendas = Sendas;
        notifyDataSetChanged();

    }

    public Senda getSendaAtPosition (int position) {
        return sendas.get(position);
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView nombreSendaView;
        public final TextView descripcionSendaView;
        final FavoritesAdapter mAdapter;

        public FavViewHolder(@NonNull View sendaView, FavoritesAdapter Adapter) {
            super(sendaView);
            nombreSendaView = sendaView.findViewById(R.id.textViewTitle);
            descripcionSendaView = sendaView.findViewById(R.id.textViewDistance);
            mAdapter = Adapter;
            sendaView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Senda element = sendas.get(mPosition);

            Intent intent = new Intent(context, mostrarInfo.class);
            intent.putExtra(EXTRA_MESSAGE, element.getNombre());
            context.startActivity(intent);

            mAdapter.notifyDataSetChanged();
        }
    }

}
