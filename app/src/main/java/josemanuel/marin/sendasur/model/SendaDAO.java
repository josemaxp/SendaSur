package josemanuel.marin.sendasur.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SendaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Senda senda);

    @Query("SELECT * from senda_table ORDER BY sendaName ASC")
    LiveData<List<Senda>> getAllSendas();

    @Query("SELECT * from senda_table LIMIT 1")
    Senda[] getAnySenda();

    @Delete
    void deleteWord(Senda senda);

}
