package com.example.gamer.myapplication.Controller;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.gamer.myapplication.R;

public class PopAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        getWindow().setLayout((int) (size.x * .8),(int)(size.y *.8f));

        TextView txt = (TextView) findViewById(R.id.popTxt);

        txt.setText(MainActivity.webSearch.words.toString());

        txt.setMovementMethod(new ScrollingMovementMethod());
    }
}
