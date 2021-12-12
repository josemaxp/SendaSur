package josemanuel.marin.sendasur.controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import josemanuel.marin.sendasur.model.Senda;

public class SendaViewModel extends AndroidViewModel {
    SendaRepository repository;

    private LiveData<List<Senda>> mAllSendas;

    public SendaViewModel(@NonNull Application application) {
        super(application);
        repository = new SendaRepository(application);
        mAllSendas = repository.getSendas();
    }

    public LiveData<List<Senda>> getAllSendas() { return mAllSendas; }

    public LiveData<List<Senda>> getFavoritesSendas(){
        return repository.getAllWords();
    }

    public void insert(Senda senda){repository.insert(senda);}

    public void deleteSenda(Senda senda){repository.deleteSenda(senda);}


}
