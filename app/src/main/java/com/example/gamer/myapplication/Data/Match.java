package com.example.gamer.myapplication.Data;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Match {
    private String teamOneName;
    private String teamOneName2;
    private String teamTwoName;
    private String teamTwoName2;



    private String description;


    private String time;
    private String date;
    private String st;
    private String gameType;


    public Match(String time, String teamOneName, String teamOneName2, String teamTwoName, String teamTwoName2){
        this.teamOneName = teamOneName;
        this.teamOneName2 = teamOneName2;

        this.teamTwoName = teamTwoName;
        this.teamTwoName2 = teamTwoName2;

        if(teamOneName.length() > 10){
            this.teamOneName = teamOneName.substring(0, 11);
        }
        if(teamTwoName.length() > 10){
            this.teamTwoName = teamTwoName.substring(0, 11);
        }



        if(!time.contains(":") || time.length() > 6){
            time = "99:99";
        }

        this.time = time;
    }

    public String toString(){
        st = "[" + time +  "]: " + teamOneName + " " + teamOneName2 +  " vs " + teamTwoName +
                " [" + gameType + "] ";

        if(time != null){
            return st;
        }else return null;
    }

    /**
     * Getters and Setters
     * @return
     */

    public String getTeamOneName() {
        return teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setDate(String date) {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        String df = df2.format(Calendar.getInstance().getTime());

        df = df.substring(df.length() -2, df.length());
        date = date.substring(date.length() -2, date.length());

        int currentdate = Integer.parseInt(df);
        int matchDate = Integer.parseInt(date);

        if(matchDate == currentdate){
            date = "tdy";
        }if(matchDate == currentdate + 1){
            date = "tmr";
        }
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        if(gameType.contains("-") || gameType.length() > 3){
            gameType = "tba";
        }
        this.gameType = gameType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
