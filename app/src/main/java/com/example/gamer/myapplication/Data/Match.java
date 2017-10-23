package com.example.gamer.myapplication.Data;


public class Match {
    private String teamOneName;
    private String teamOneName2;
    private String teamTwoName;
    private String teamTwoName2;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    private String date;
    private String st;



    private String gameType;


    public Match(String time, String teamOneName, String teamOneName2, String teamTwoName, String teamTwoName2){
        this.teamOneName = teamOneName;
        this.teamOneName2 = teamOneName2;

        this.teamTwoName = teamTwoName;
        this.teamTwoName2 = teamTwoName2;

        if(!time.contains(":")){
            time = null;
        }

        this.time = time;
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        if(gameType.contains("-")){
            gameType = "???";
        }
        this.gameType = gameType;
    }

    private void lengthen(){
        if(st.length() < 40) {
            st = st + " ";
            lengthen();
        }
    }

    public String toString(){
        st = "[" + time +  "]: " + teamOneName + " " + teamOneName2 +  " vs " + teamTwoName +
                " [" + gameType + "] ";

//        lengthen();

        if(time != null){
            return st;
        }else return null;

    }
}
