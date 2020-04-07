package com.bawp.nodo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.nodo.util.NoDoRepoisitory;

import java.util.List;

public class NoDoViewModel extends AndroidViewModel {
    private NoDoRepoisitory noDoRepoisitory;
    private LiveData<List<NoDo>> allNoDos;
    public NoDoViewModel(@NonNull Application application) {
        super(application);
        noDoRepoisitory = new NoDoRepoisitory(application);
        allNoDos = noDoRepoisitory.getAllNoDos();
    }
    public LiveData<List<NoDo>> getAllNoDos() {
        return allNoDos;
    }
    public void insert(NoDo noDo) {
        noDoRepoisitory.insert(noDo);
    }
}
