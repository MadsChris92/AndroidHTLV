package com.example.gamer.myapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamer.myapplication.Data.TeamButton;
import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.DatabaseHelper;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    EditText teamTxt;
    TextView dataTxt;
    LinearLayout container;
    String username;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myDb = new DatabaseHelper(getBaseContext());

        teamTxt = (EditText) findViewById(R.id.teamTxt);
        dataTxt = (TextView) findViewById(R.id.dataTextView);
        container = (LinearLayout) findViewById(R.id.btnContainer);
        dataTxt.setText("Press button to remove team");
        dataTxt.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        container.setFocusable(true);
        container.requestFocus();

        displayData();
    }

    public void submitTeam(View view) {
        if(myDb.insertData(username, teamTxt.getText().toString())){
            Toast.makeText(getBaseContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
            teamTxt.setText("");
            displayData();
        }else{
            Toast.makeText(getBaseContext(), "Insertion Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayData(){
        final Cursor res = myDb.getAllData();
        int count = 0;
        container.removeAllViews();
        if (res.getCount() == 0) {
            dataTxt.setText("NO DATA");
            dataTxt.setVisibility(View.VISIBLE);
            return;
        }

        while (res.moveToNext()) {
            if(username.equalsIgnoreCase(res.getString(1))){
                count++;
                final TeamButton b = new TeamButton(getBaseContext(), res.getString(2), res.getInt(0));
                b.setText(res.getString(2));
                b.setBackgroundColor(4);

                b.setTextSize(22);
                //b.setTextColor(Color.GREEN);
                //b.setBackground(null);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeData(getBaseContext(), b.getText().toString(), b.getId());
                    }
                });

                container.addView(b);
            }
        }

        if(count == 0){
            dataTxt.setText("User has no saved data");
        }else{
            dataTxt.setText("Press button to remove team");
        }
        dataTxt.setVisibility(View.VISIBLE);
    }

    /**
     * Jeg bruger id til at slette data, dajeg på denne måde ikke skal tage højde
     * for, hvilken bruger der har slettet hvad. Da der kun kan eksisterer en række
     * med dette ID (primary key).
     */

    private void removeData(Context baseContext, String s, int id) {
        if(myDb.deleteData(id + "") > 0){
            Toast.makeText(getBaseContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
            displayData();
        }else{
            Toast.makeText(getBaseContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
