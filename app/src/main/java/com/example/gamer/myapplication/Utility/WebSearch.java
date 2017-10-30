package com.example.gamer.myapplication.Utility;

import android.os.AsyncTask;

import com.example.gamer.myapplication.Data.Match;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WebSearch extends AsyncTask<Void, Void, Void>{

    /**
     * An asynchronous task is defined by a computation that runs on a background thread and whose result is published
     * on the UI thread. An asynchronous task is defined by 3 generic types, called Params, Progress and Result,
     * and 4 steps, called onPreExecute, doInBackground, onProgressUpdate and onPostExecute.
     */

    private ArrayList<Character> charArray = new ArrayList<>();
    public ArrayList<String> words = new ArrayList<>();
    public ArrayList<Match> matches = new ArrayList<>();
    private ArrayList<String> dates;
    private String websiteData;
    private String date;
    private boolean done = false;

    /**
     * Indsæt i Android manifest:     <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     */

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("https://www.hltv.org/matches").get();
            websiteData = doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        searchData();
    }

    void searchData(){
        ArrayList<String> gameTypes = new ArrayList<>();
        wordConvert();
        findDate();
        String s;
        for (int i = 0; i < words.size(); i++) {
            Match m = new Match("", "", "", "", "");
            if(words.get(i).equals("vs")){
                if(words.get(i - 2).contains(":")){
                    m = new Match(words.get(i - 2),words.get(i - 1 ), "",words.get(i + 1),"");
                    matches.add(m);
                    s = words.get(i-3);
                    gameTypes.add(s);
                }else if(!words.get(i - 2).contains(":")){
                    m = new Match(words.get(i - 3),words.get(i - 2 ), words.get(i - 1),words.get(i + 1),"");
                    matches.add(m);
                    s = words.get(i - 4);
                    gameTypes.add(s);
                }
            }
            if(dates.contains(words.get(i))){
                date = words.get(i);
            }
            if(i > words.size() - 6){

            }else{
                m.setDescription(words.get(i+2) + " " + words.get(i+3) + " " + words.get(i+4) + " " + words.get(i+5));
            }

            m.setDate(date);
        }
        done = true;

        for (int i = 0; i < gameTypes.size() - 1; i++) {
            matches.get(i).setGameType(gameTypes.get(i + 1));
        }
    }

    public boolean isDone(){
        return this.done;
    }

    private void findDate() {
        dates = new ArrayList<>();
        DateFormat df =  new SimpleDateFormat("yyyy-");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");


        date = df2.format(Calendar.getInstance().getTime());
        String yearDate = df.format(Calendar.getInstance().getTime());

        boolean start = false;
        for (String w : words){

            if(w.equalsIgnoreCase("Upcoming")){
                start = true;
            }

            if(w.contains(yearDate) && start){
                dates.add(w);
            }
        }
    }

    private void wordConvert(){
        charArray = new ArrayList<>();

        for (Character c : websiteData.toCharArray()) {
            charArray.add(c);
        }

        String tempWord = "";
        for (int i = 0; i < charArray.size(); i++) {
            if(charArray.get(i) == ' '){
                words.add(tempWord);
                tempWord = "";
            }else if(charArray.get(i) != ','){
                tempWord = tempWord + charArray.get(i);
            }
        }
    }
}

