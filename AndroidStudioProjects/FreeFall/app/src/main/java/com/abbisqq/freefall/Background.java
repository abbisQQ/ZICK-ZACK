package com.abbisqq.freefall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by babis on 27/10/2016.
 */

public class Background {
    Bitmap bgImage;
    private int transparent;
    public Background(){
        transparent=255;
    }

    public void onDraw(Canvas c){
        c.drawARGB(transparent,150,150,10);
        if(transparent<255){
            transparent=transparent+5;
        }

    }

    public void setTransparent(int transparent) {
        this.transparent = transparent;
    }
}
