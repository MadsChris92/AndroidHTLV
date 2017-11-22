package com.example.gamer.myapplication.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.MyAlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PopTeamActivity extends AppCompatActivity {
    String getDesc = "DESC", getTeam = "TEAM", getTime = "TIME";
    String desc, teams, time;
    TextView descTxt, teamTxt;
    Button alarmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_team);

        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        getWindow().setLayout((int) (size.x * .8),(int)(size.y *.3));

        descTxt = (TextView) findViewById(R.id.descTxt);
        teamTxt = (TextView) findViewById(R.id.teamTxt);
        alarmBtn = (Button) findViewById(R.id.alarmBtn);


        Intent intent = getIntent();
        desc = intent.getStringExtra(getDesc);
        teams = intent.getStringExtra(getTeam);
        time = intent.getStringExtra(getTime);

        descTxt.setText(desc);
        teamTxt.setText(teams + " - " + time);

        final String hod = time.substring(0,2);
        final String mod = time.substring(3,5);

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PopTeamActivity.this, MyAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(PopTeamActivity.this, 0,myIntent, 0);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Calendar cal_alarm = Calendar.getInstance();
                Date dat = new Date();
                cal_alarm.setTime(dat);


                cal_alarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hod));
                cal_alarm.set(Calendar.MINUTE,Integer.parseInt(mod));
                cal_alarm.set(Calendar.SECOND,0);
                /*
                cal_alarm.set(Calendar.HOUR_OF_DAY,23);
                cal_alarm.set(Calendar.MINUTE,03);
                */


                long timeToAlarm = TimeUnit.MILLISECONDS.toMinutes(cal_alarm.getTimeInMillis() - System.currentTimeMillis());


                manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
                Toast.makeText(getBaseContext(), "Alarm goes of in " + timeToAlarm + " minutes[" + hod + ":" + mod + "]", Toast.LENGTH_LONG).show();
            }
        });
    }
}
