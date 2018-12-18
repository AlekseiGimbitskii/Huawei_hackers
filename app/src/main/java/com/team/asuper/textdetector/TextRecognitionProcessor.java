package com.team.asuper.textdetector;

import android.app.LauncherActivity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.team.asuper.textdetector.ExamplesCopyPaste.FrameMetadata;
import com.team.asuper.textdetector.ExamplesCopyPaste.GraphicOverlay;
import com.team.asuper.textdetector.ExamplesCopyPaste.VisionProcessorBase;

import java.io.IOException;

public class TextRecognitionProcessor extends VisionProcessorBase<FirebaseVisionText> {

    private static final String TAG = "TextRecProcessor";

    private final FirebaseVisionTextRecognizer detector;
    private final TestExampleCameraActivity testActInstance;

    public TextRecognitionProcessor(TestExampleCameraActivity activity) {
        testActInstance = activity;
        detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Text Detector: " + e);
        }
    }

    @Override
    protected Task<FirebaseVisionText> detectInImage(FirebaseVisionImage image) {
        return detector.processImage(image);
    }

    @Override
    protected void onSuccess(@Nullable Bitmap originalCameraImage,
                             @NonNull FirebaseVisionText results,
                             @NonNull FrameMetadata frameMetadata,
                             @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        //activityInstance.updateSpinnerFromTextResults(results);
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Text detection failed." + e);
    }
}