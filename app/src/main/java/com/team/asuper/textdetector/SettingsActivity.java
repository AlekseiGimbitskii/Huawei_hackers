package com.team.asuper.textdetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;




public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewPager vp = new ViewPager(this);
        //vp.setId( R.id.view_pager );
        //setContentView( vp );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
    }
}


