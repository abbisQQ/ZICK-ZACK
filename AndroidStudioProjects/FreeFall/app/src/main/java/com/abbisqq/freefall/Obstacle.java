package com.abbisqq.freefall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.telecom.CallScreeningService;

import java.util.Random;

import static android.R.attr.canRecord;
import static android.R.attr.theme;
import static android.R.attr.width;

/**
 * Created by babis on 21/10/2016.
 */

public class Obstacle {
    private int r1Left,r1Right,r1Top,r1Bottom;
    Paint paint;
    private Rect rect1,rect2;
    Random random=new Random();
    GameView gv;
    Bitmap wall;
    int screenWidth;
    private int maxSpeed;
    private boolean gameIsOn = true, scoreCounted=false;




    public Obstacle(int top,GameView gv,Bitmap wall){
        paint = new Paint(Color.WHITE);
        this.wall = wall;
        this.gv=gv;
        screenWidth = gv.getWidth();
        r1Top=top;
        maxSpeed=4;
        r1Bottom=r1Top+120;
        r1Left=r1Right-screenWidth;
        r1Right=random.nextInt(gv.getWidth()-gv.getObstacleGap());


    }
    public void update(){
        if(gameIsOn) {
            if(r1Top-120>gv.getHeight()/2&&scoreCounted==false){
                gv.scoreAndCoin();
                scoreCounted=true;
            }
            r1Top += maxSpeed;
            r1Bottom += maxSpeed;
        }
    }


    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void onDraw(Canvas c){
            update();
            if (c.getHeight() < r1Top) {
                r1Top = 0;
                r1Bottom = 120;
                r1Right = random.nextInt(screenWidth-gv.getObstacleGap());
                r1Left=r1Right-screenWidth;
                scoreCounted=false;
                gv.setObstacleRight(r1Right);
            }
            rect1 = new Rect(r1Left, r1Top, r1Right, r1Bottom);
            rect2 = new Rect((r1Right + gv.getObstacleGap()), r1Top, screenWidth+r1Right+gv.getObstacleGap(), r1Bottom);
            c.drawRect(rect1, paint);
            c.drawRect(rect2, paint);
            c.drawBitmap(wall,null,rect1,null);
            c.drawBitmap(wall,null,rect2,null);
        }

        public Rect getRect1(){
            return rect1;
        }
        public Rect getRect2(){
            return rect2;
    }
        public void setGameisOn(boolean gameIsOn){
            this.gameIsOn = gameIsOn;
        }

}
