package com.team.asuper.textdetector;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.google.firebase.ml.vision.text.FirebaseVisionText;


public class CustomView extends SurfaceView {

    private Rect rectangle;
    private Paint paint;

    public CustomView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.RED);
    }


    public void drawTextArea(FirebaseVisionText.Element word){


    }
}

