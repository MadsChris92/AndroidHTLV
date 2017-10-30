package com.example.gamer.myapplication.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.WebSearch;

public class MainActivity extends AppCompatActivity{
    /**
     * Der skal bruges internet forbindelse ellers vil  appen crashe ved startup
     */

    public static WebSearch webSearch;
    private EditText nameTxt;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = "Mads";

        nameTxt = (EditText) findViewById(R.id.nameEditText);

        webSearch = new WebSearch();

        webSearch.execute();
    }

    /**
     Henter data from HLTV i en anden tråd, da processen kan tage lang tid.
     Dataen bruges i en anden activity, men burde være preloaded som baggrundsproces.
     */


    public void changeScene(Class c, String extraKey, String extraValue) {

        if(!username.isEmpty()){
            Intent intent = new Intent(this, c);
            intent.putExtra(extraKey, extraValue);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Invalid Name", Toast.LENGTH_LONG).show();
        }
    }

    public void goBtn(View view) {
        if(webSearch.isDone()){
            changeScene(WebActivity.class, "USERNAME", username);
        }else{
            Toast.makeText(getBaseContext(), "Retrieving Data... Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    public void userBtn(View view) {
        changeScene(UserActivity.class, "USERNAME", username);
    }

    public void changeUserName(View view) {
        username = nameTxt.getText().toString();
        nameTxt.setText("");
    }
}
