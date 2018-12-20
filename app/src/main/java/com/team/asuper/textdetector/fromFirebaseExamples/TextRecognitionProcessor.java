package com.team.asuper.textdetector.fromFirebaseExamples;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Vibrator;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.team.asuper.textdetector.TextDetectionCameraActivity;
import com.team.asuper.textdetector.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Processor for the text recognition demo.
 */
public class TextRecognitionProcessor extends VisionProcessorBase<FirebaseVisionText> {

    private static final String TAG = "TextRecProc";

    private List<String> targetWords;
    public void setTargetWords(List<String> targetWords) {
        this.targetWords = new ArrayList<String>(targetWords);
    }

    private final FirebaseVisionTextRecognizer detector;

    public TextRecognitionProcessor(TextDetectionCameraActivity textDetectionCameraActivity) {
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
    protected void onSuccess(
            @Nullable Bitmap originalCameraImage,
            @NonNull FirebaseVisionText results,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        if (originalCameraImage != null) {
            CameraImageGraphic imageGraphic = new CameraImageGraphic(graphicOverlay,
                    originalCameraImage);
            graphicOverlay.add(imageGraphic);
        }
        List<FirebaseVisionText.TextBlock> blocks = results.getTextBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    for (int l = 0; l < targetWords.size(); l++){
                        if(elements.get(k).getText().equalsIgnoreCase(targetWords.get(l))) {
                            GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay,
                                    elements.get(k));
                            graphicOverlay.add(textGraphic);
                            Vibrator vibrator = (Vibrator) MainActivity.context.getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                    }
                    /*
                    GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay,
                            elements.get(k));
                    graphicOverlay.add(textGraphic);
                    */
                }
            }
        }
        graphicOverlay.postInvalidate();
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Text detection failed." + e);
    }

}