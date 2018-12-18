package com.team.asuper.textdetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void startCamera(View view){
        Intent intent = new Intent(this, TextDetectionCameraActivity.class);
        startActivity(intent);
    }

    /*
    public void startCamera(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    */
    public void detectText(View view) {
        // Do something in response to button

        //---------------read file, detect text and display it on textview--------------------------

        final TextView textview = (TextView) findViewById(R.id.screenText);
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


/*
        try {
        String resultText = result.getResult().getText();
        //Log.d("tah", resultText)
        }
        catch (Exception e) {
            textview.setText(e.getMessage());
        }


        //textview.setText(resultText);
*/

    }
}
