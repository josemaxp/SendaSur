package josemanuel.marin.sendasur.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.model.Senda;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>{
    private List<Senda> sendas;
    private Context context;

    public FavoritesAdapter(List<Senda> sendas, Context context) {
        this.sendas = sendas;
        this.context = context;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favorites_list, parent, false);
        return new FavViewHolder(v);
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

    public class FavViewHolder extends RecyclerView.ViewHolder{
        public final TextView nombreSendaView;
        public final TextView descripcionSendaView;

        public FavViewHolder(@NonNull View sendaView) {
            super(sendaView);
            nombreSendaView = sendaView.findViewById(R.id.textViewTitle);
            descripcionSendaView = sendaView.findViewById(R.id.textViewDistance);
        }
    }

}
