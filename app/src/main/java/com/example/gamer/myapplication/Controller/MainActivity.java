package com.example.gamer.myapplication.Controller;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.MyAlarmReceiver;
import com.example.gamer.myapplication.Utility.WebSearch;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{
    /**
     * Der skal bruges internet forbindelse ellers vil det ikke være muligt at tilgå 'WebActivity'
     */

    public static WebSearch webSearch;
    private EditText nameTxt;
    private String username;
    private TextView locTxt;
    LocationManager locationManager;
    private TextView welTxt;
    MyAlarmReceiver myAlarmReceiver;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = "Mads";
        nameTxt = (EditText) findViewById(R.id.nameEditText);
        locTxt = (TextView) findViewById(R.id.locationTxt);
        welTxt = (TextView) findViewById(R.id.welcomeTxt);

        searchHltv();

        if(connected){
            locationSetup();
            locTxt.setText("Searching for GPS...");
        }else{
            locTxt.setText("No internet connection");
        }

        myAlarmReceiver = new MyAlarmReceiver();
        Button b = (Button) findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager manger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent;
                PendingIntent pendingIntent;

                myIntent = new Intent(MainActivity.this, MyAlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0,myIntent, 0);

                manger.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, pendingIntent);
            }
        });
    }

    public void searchHltv(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
        }

        if(connected){
            webSearch = new WebSearch();
            webSearch.execute();
        }
    }

    public void locationSetup(){

        int locationAccess = 0;
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationAccess);
        }

        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

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
        if(connected){
            if(webSearch.isDone()){
                changeScene(WebActivity.class, "USERNAME", username);
            }
        }else if(!connected){
            Toast.makeText(getBaseContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getBaseContext(), "Retrieving data... Try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void userBtn(View view) {
        changeScene(UserActivity.class, "USERNAME", username);
    }

    public void changeUserName(View view) {
        username = nameTxt.getText().toString();
        nameTxt.setText("");
    }

    @Override
    public void onLocationChanged(Location location) {

        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = cityName;
        locTxt.setText("Connecting from: " + s);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
