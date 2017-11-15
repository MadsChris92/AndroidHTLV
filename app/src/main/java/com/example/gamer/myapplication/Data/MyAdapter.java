package com.example.gamer.myapplication.Data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<Match> list;
    private ArrayList<String> favTeams;
    ArrayList<TextView> textViews = new ArrayList<>();
    private Context context;

    private int teamWidth = 300, timeWidth = 150, typeWidth = 150, maxHieght = 50;

    public MyAdapter(Context context, ArrayList<Match> objects, ArrayList<String> favTeams){
        this.list = objects;
        this.context = context;
        this.favTeams = favTeams;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
//        int height = size.y;

        teamWidth = width/4 + 30;
        typeWidth = width/9;
        timeWidth = width/8;

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
        team1.setTextColor(Color.WHITE);
        team1.setSingleLine(true);

        TextView team2 = new TextView(context);
        team2.setMinWidth(teamWidth-20);
        team2.setMaxWidth(teamWidth-20);
        team2.setMaxHeight(maxHieght);
        team2.setText(list.get(i).getTeamTwoName());
        team2.setTextColor(Color.WHITE);
        team2.setSingleLine(true);

        TextView time = new TextView(context);
        time.setMinWidth(timeWidth);
        time.setMaxWidth(timeWidth);
        time.setMaxHeight(maxHieght);
        time.setText(list.get(i).getTime());
        time.setTextColor(Color.WHITE);
        time.setSingleLine(true);

        TextView type = new TextView(context);
        type.setMinWidth(typeWidth);
        type.setMaxWidth(typeWidth);
        type.setMaxHeight(maxHieght);
        type.setText(list.get(i).getGameType());
        type.setTextColor(Color.WHITE);
        type.setSingleLine(true);

        TextView day = new TextView(context);
        day.setMinWidth(typeWidth);
        day.setMaxWidth(typeWidth);
        day.setMaxHeight(maxHieght);
        day.setText(list.get(i).getDate());
        day.setTextColor(Color.WHITE);
        day.setSingleLine(true);

        /*
        RadioButton btn = new RadioButton(context);
        btn.setWidth(typeWidth);
        btn.setHeight(maxHieght);
        */
        textViews.add(team1);
        textViews.add(team2);
        textViews.add(time);
        textViews.add(type);
        textViews.add(day);

        if(favTeams.contains(list.get(i).getTeamOneName().toLowerCase())){
            team1.setTextColor(Color.GREEN);
        }
        if(favTeams.contains(list.get(i).getTeamTwoName().toLowerCase())){
            team2.setTextColor(Color.GREEN);
        }

        LinearLayout l = new LinearLayout(context);
        l.addView(time);
        l.addView(team1);
        l.addView(team2);
        l.addView(type);
        l.addView(day);
//        l.addView(btn);
        l.setOrientation(LinearLayout.HORIZONTAL);

        return l;
    }

    public void printGame(int i){

    }
}
