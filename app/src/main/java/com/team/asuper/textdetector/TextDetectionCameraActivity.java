package com.team.asuper.textdetector;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.team.asuper.textdetector.fromFirebaseExamples.CameraSource;
import com.team.asuper.textdetector.fromFirebaseExamples.CameraSourcePreview;
import com.team.asuper.textdetector.fromFirebaseExamples.GraphicOverlay;
import com.team.asuper.textdetector.fromFirebaseExamples.TextRecognitionProcessor;

import java.io.IOException;
import java.util.ArrayList;


public class TextDetectionCameraActivity extends AppCompatActivity {
    private static final String TAG = "TestExCameraActivity";
    private CameraSourcePreview preview; // To handle the camera
    private GraphicOverlay graphicOverlay; // To draw over the camera screen
    private CameraSource cameraSource = null; //To handle the camera
    private static final int PERMISSION_REQUESTS = 1; // to handle the runtime permissions
    private ArrayList<String> targetWords; //store words to be found



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_example_camera);

        targetWords = new ArrayList<String>();
        targetWords = (ArrayList<String>) getIntent().getSerializableExtra("targetWords");

        // getting views from the xml
        preview = (CameraSourcePreview) findViewById(R.id.Preview);
        graphicOverlay = (GraphicOverlay) findViewById(R.id.Overlay);


        // intializing views
        if (preview == null) {
            Log.d(TAG, " Preview is null ");
        }

        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null ");
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUESTS);


            try {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    createCameraSource();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        /*
        if (true) {
            createCameraSource();
        } else {
            getRuntimePermissions();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    // Actual code to start the camera
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "startCameraSource resume: Preview is null ");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "startCameraSource resume: graphOverlay is null ");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.d(TAG, "startCameraSource : Unable to start camera source." + e.getMessage());
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    /*
    // Function to check if all permissions given by the user
    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }


    // List of permissions required by the application to run.
    private String[] getRequiredPermissions() {
        return new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    // Checking a Runtime permission value
    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "isPermissionGranted Permission granted : " + permission);
            return true;
        }
        Log.d(TAG, "isPermissionGranted: Permission NOT granted -->" + permission);
        return false;
    }

    // getting runtime permissions
    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }
    */
    // Function to create a camera source and retain it.
    private void createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
        }

        try {
            //original textRecognitionProcessor was changed to display only target words
            TextRecognitionProcessor textRecognitionProcessor = new TextRecognitionProcessor(this);
            textRecognitionProcessor.setTargetWords(targetWords);
            cameraSource.setMachineLearningFrameProcessor(textRecognitionProcessor);

        } catch (Exception e) {
            Log.d(TAG, "createCameraSource can not create camera source: " + e.getCause());
            e.printStackTrace();
        }
    }

    /*
    //  updating and displaying the results recieved from Firebase Text Processor Api
    public void updateSpinnerFromTextResults(FirebaseVisionText textresults) {
        List<FirebaseVisionText.TextBlock> blocks = textresults.getTextBlocks();
        for (FirebaseVisionText.TextBlock eachBlock : blocks) {
            for (FirebaseVisionText.Line eachLine : eachBlock.getLines()) {
                for (FirebaseVisionText.Element eachElement : eachLine.getElements()) {
                    if (!displayList.contains(eachElement.getText()) && displayList.size() <= 9) {
                        displayList.add(eachElement.getText());
                    }
                }
            }
        }
        //resultNumberTv.setText(getString(R.string.x_results_found, displayList.size()));
        //displayAdapter.notifyDataSetChanged();
    }*/
}
