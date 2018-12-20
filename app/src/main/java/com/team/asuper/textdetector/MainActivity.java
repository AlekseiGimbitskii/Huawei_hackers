package com.team.asuper.textdetector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> targetWords;
    public static Context context;
    private DrawerLayout mDrawerLayout;

    private static final int REQUEST_RECORD_AUDIO = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        targetWords = new ArrayList<String>();

        SharedPreferences sharedPref = MainActivity.context.getSharedPreferences("targetWordList", Context.MODE_PRIVATE);
        String csvList = sharedPref.getString("targetWordList", "");
        String[] items = csvList.split(",");
        for (String s: items) {
            targetWords.add(s);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        requestMicrophonePermission();
        startSpeechRecognition ();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MainActivity.this, SpeechRecognitionService.class);
        stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(MainActivity.this, SpeechRecognitionService.class);
        stopService(intent);
    }

    public void startCamera(View view){
        Intent intent = new Intent(this, TextDetectionCameraActivity.class);
        intent.putExtra("targetWords", targetWords);
        startActivity(intent);
    }

    public void showMainPage(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showCamera(MenuItem item){
        Intent intent = new Intent(this, TextDetectionCameraActivity.class);
        intent.putExtra("targetWords", targetWords);
        startActivity(intent);
    }

    public void showSetting(MenuItem item){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showWords(MenuItem item){
        Intent intent = new Intent(this, AddWordsActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startSpeechRecognition () {
        Intent serviceIntent = new Intent(this, SpeechRecognitionService.class);
        try {
            startService(serviceIntent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestMicrophonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startSpeechRecognition ();
        }
    }

    /*
    public void detectText(View view) {
        // Do something in response to button

        //---------------read file, detect text and display it on textview--------------------------

        final TextView textview = (TextView) findViewById(R.id.screexnText);
        //textview.setText("success");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.textarea);
        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        //textview.setBackground(drawable);


        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        Task<FirebaseVisionText> result = detector.processImage(image)

                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        //textview.setText("success");
                        String resultText = firebaseVisionText.getText();
                        textview.setText(resultText);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        textview.setText("failure");
                    }
                });



        try {
        String resultText = result.getResult().getText();
        //Log.d("tah", resultText)
        }
        catch (Exception e) {
            textview.setText(e.getMessage());
        }


        //textview.setText(resultText);


    }
    */
}
