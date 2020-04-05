package com.bawp.nodo.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nodo_table")
public class NoDo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nodo_col")
    private String noDo;

    public NoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }

    public String getNoDo(){
        return noDo;
    }

    public void setNoDo(@NonNull  String noDo) {
        this.noDo = noDo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
