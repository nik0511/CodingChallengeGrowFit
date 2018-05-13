package com.example.ramon_sifer.codingchallengegrowfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ramon_sifer.codingchallengegrowfit.customclasses.NetworkStatus;
import com.example.ramon_sifer.codingchallengegrowfit.fragments.ListData;


public class MainActivity extends AppCompatActivity {

    private NetworkStatus network_status;
    private TextView noInternetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noInternetView = (TextView) findViewById(R.id.no_internet_view);

        network_status = new NetworkStatus(getApplicationContext());

        if (network_status.isConnectingToInternet()) {
            noInternetView.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_switch, ListData.newInstance(), "ListFragment").commit();
        }else{
            noInternetView.setVisibility(View.VISIBLE);
        }
    }


}
