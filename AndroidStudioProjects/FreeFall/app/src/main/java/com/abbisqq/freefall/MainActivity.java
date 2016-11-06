package com.abbisqq.freefall;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import layout.GameFragment;
import layout.StartingFragment;

public class MainActivity extends AppCompatActivity implements StartingFragment.OnStartingFragmentInteractionListener, GameFragment.OnGameFragmentInteractionListener {
    GameFragment gf = new GameFragment();
    StartingFragment sf = new StartingFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new StartingFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();


        }


    }

    public void loadGameFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, gf).addToBackStack(null).commit();

    }

    public void loadStartingFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, sf).commit();

    }


    @Override
    public void OnStartingFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnGameFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            gf.onPause();
            loadStartingFragment();
        }catch (Exception e){

        }
    }

}
