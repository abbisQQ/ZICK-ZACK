package com.abbisqq.freefall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import layout.GameFragment;

/**
 * Created by babis on 22/10/2016.
 */

public class MySprite{
    Bitmap hero;
    int x,y, xSpeed,ySpeed, height, width,times=0;
    GameView gv;
    private int currentFrame=0,direction=1,topspeed=7;
    int srcY=400,srcX=100,heroFall=0;
    Rect src ,dst,rect;
    Boolean gameIsOn=true;




    public  MySprite(Bitmap hero, GameView gv){
       this.hero=hero;
        this.gv = gv;
        //4 rows
        height = hero.getHeight()/4;
        width = hero.getWidth()/4;
        x=gv.getWidth();
        y=gv.getHeight()/2;
        xSpeed=5;
        ySpeed=0;
    }

    public void update(){
        if (gameIsOn) {
            if (direction == 2) {
                if (xSpeed >= topspeed) {
                    xSpeed = topspeed;
                } else {
                    xSpeed = xSpeed + 1;
                }
            } else if (direction == 1) {
                if (xSpeed >= -topspeed) {
                    xSpeed = -topspeed;
                } else {
                    xSpeed = xSpeed - 1;
                }
            }
            if (times % 10 == 0) {
                if((x + hero.getWidth() / 4 <= gv.getWidth() && direction == 2)||(x > 0 && direction == 1))
                currentFrame = ++currentFrame % 4;
            }
            times++;

            if ((x + hero.getWidth() / 4)-hero.getWidth()/10 > gv.getWidth() && direction == 2) {
                xSpeed = 0;
            }
            if (x <= -hero.getWidth()/10 && direction == 1) {
                xSpeed = 0;
            }
            x = x + xSpeed;


        }else
        {

            xSpeed = 0;
            ySpeed =10;
            x=x+xSpeed;
            y=y+ySpeed;
            heroFall++;
            if(heroFall>100){
                gv.onResume();
            }
        }

        srcY = getDirection()*height;
        srcX = currentFrame*width;
        src= new Rect(srcX,srcY,srcX+width,srcY+height);
        dst= new Rect(new Rect(x,y,x+width, y+height));
        rect =  new Rect(new Rect(x+40,y+50,x+width-40, y+height-20));

    }


    public void setDirection(int direction){
            this.direction = direction;
    }
    public int getDirection(){
        return direction;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(hero,src,dst,null);


    }
    public Rect getSrc(){
        return rect;
    }
    public void setGameisOn(boolean gameIsOn){
        this.gameIsOn = gameIsOn;
        if(gameIsOn==false){
            gv.gameIsOver();
        }
    }
    public boolean getGameIsOn(){
        return gameIsOn;
    }

    public int getTopspeed() {
        return topspeed;
    }

    public void setTopspeed(int topspeed) {
        this.topspeed = topspeed;
    }
}
