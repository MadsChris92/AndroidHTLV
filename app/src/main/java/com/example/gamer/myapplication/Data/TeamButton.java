package com.example.gamer.myapplication.Data;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.example.gamer.myapplication.Utility.DatabaseHelper;


public class TeamButton extends Button {
    String team;
    DatabaseHelper myDb;
    int id;

    public TeamButton(Context context, String team, int anInt) {
        super(context);
        this.team = team;
        this.id = anInt;
      //  myDb = new DatabaseHelper(getContext());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
