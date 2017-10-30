package com.example.gamer.myapplication.Data;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gamer.myapplication.Controller.WebActivity;
import com.example.gamer.myapplication.Data.Match;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<Match> list;
    private ArrayList<String> favTeams;
    private Context context;
    private int teamWidth = 300, timeWidth = 150, typeWidth = 150, maxHieght = 50;
    public String searchString;

    public MyAdapter(Context context, ArrayList<Match> objects, ArrayList<String> favTeams){
        this.list = objects;
        this.context = context;
        this.favTeams = favTeams;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i).getDate();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final TextView team1 = new TextView(context);
        team1.setMinWidth(teamWidth);
        team1.setMaxWidth(teamWidth);
        team1.setMaxHeight(maxHieght);

        team1.setText(list.get(i).getTeamOneName());

        TextView team2 = new TextView(context);
        team2.setMinWidth(teamWidth-20);
        team2.setMaxWidth(teamWidth-20);
        team2.setMaxHeight(maxHieght);
        team2.setText(list.get(i).getTeamTwoName());

        TextView time = new TextView(context);
        time.setMinWidth(timeWidth);
        time.setMaxWidth(timeWidth);
        time.setMaxHeight(maxHieght);
        time.setText(list.get(i).getTime());

        TextView type = new TextView(context);
        type.setMinWidth(typeWidth);
        type.setMaxWidth(typeWidth);
        type.setMaxHeight(maxHieght);
        type.setText(list.get(i).getGameType());

        TextView day = new TextView(context);
        day.setMinWidth(typeWidth);
        day.setMaxWidth(typeWidth);
        day.setMaxHeight(maxHieght);
        day.setText(list.get(i).getDate());

        if(favTeams.contains(list.get(i).getTeamOneName().toLowerCase())){
            team1.setTextColor(Color.RED);
        }
        if(favTeams.contains(list.get(i).getTeamTwoName().toLowerCase())){
            team2.setTextColor(Color.RED);
        }

        LinearLayout l = new LinearLayout(context);
        l.addView(time);
        l.addView(team1);
        l.addView(team2);
        l.addView(type);
        l.addView(day);
        l.setOrientation(LinearLayout.HORIZONTAL);

        return l;
    }
}
