package com.example.gamer.myapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gamer.myapplication.Data.Match;
import com.example.gamer.myapplication.Data.MyAdapter;
import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.DatabaseHelper;


import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {
    TextView txt;
    ConstraintLayout container;
    String username, admin = "admin";
    GridView grid;
    ArrayList<Match> matches = new ArrayList<>();
    ArrayList<String> favTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        matches = MainActivity.webSearch.matches;

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        organizeData(MainActivity.webSearch.matches);
    }


    void organizeData(ArrayList<Match> matches){
        DatabaseHelper myDb = new DatabaseHelper(getBaseContext());
        Cursor res = myDb.getAllData();
        favTeams = new ArrayList<>();

        while (res.moveToNext()){
            if(res.getString(1).equalsIgnoreCase(username))
            favTeams.add(res.getString(2));
        }

        if(username.equalsIgnoreCase(admin)){
            startActivity(new Intent(WebActivity.this, PopAdminActivity.class));
            setGridView(favTeams, matches, favTeams);
        }else {
            setGridView(favTeams, matches, favTeams);
        }
    }

    private void setGridView(ArrayList<String> favTeams, final ArrayList<Match> matches, ArrayList<String> teams){
        grid = (GridView) findViewById(R.id.grid);
  //      setAdapter(getBaseContext(), matches, favTeams);
        grid.setAdapter(new MyAdapter(getBaseContext(), matches, favTeams));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), PopTeamActivity.class);
                intent.putExtra("DESC", matches.get(i).getDescription());
                intent.putExtra("TEAM", matches.get(i).getTeamOneName() + " vs "  + matches.get(i).getTeamTwoName());
                intent.putExtra("TIME", matches.get(i).getTime());
                startActivity(intent);
              //  Toast.makeText(getBaseContext(),matches.get(i).getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void teamSearch(View view) {
        ArrayList<Match> specMatches = new ArrayList<>();
        EditText searchTxt = (EditText) findViewById(R.id.searchTeamTxt);
        for (Match m : matches){
            if(m.getTeamOneName().equalsIgnoreCase(searchTxt.getText().toString()) || m.getTeamTwoName().equalsIgnoreCase(searchTxt.getText().toString())){
                specMatches.add(m);
            }
        }

        if(searchTxt.getText().toString().isEmpty()){
            grid.setAdapter(new MyAdapter(getBaseContext(), matches, favTeams));
        }else {
            grid.setAdapter(new MyAdapter(getBaseContext(), specMatches, favTeams));
            searchTxt.setText("");
        }
    }

    public static void setAdapter(Context context, ArrayList<Match> list, ArrayList<String> favTeams){

    }
}

