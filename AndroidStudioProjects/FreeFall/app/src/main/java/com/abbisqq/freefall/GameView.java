package com.abbisqq.freefall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import layout.GameFragment;
import layout.StartingFragment;

/**
 * Created by babis on 19/10/2016.
 */

public class GameView extends SurfaceView implements Runnable,View.OnTouchListener{

    //Declare everything i need
    Thread thread;
    //a holder to grab the surfaceview
    SurfaceHolder holder;
    //3 obstacle objects one for each moving wall
    Obstacle ob1,ob2,ob3;

    Boolean screenIsReady = false,obstacles=false,endMusic=false;

    Canvas c;

    MediaPlayer mp,go,main,buttonPressed,stoneMove,speedBoost;
    // the classes i need
    MainActivity ma;
    MySprite sprite;
    Background bg;
    StartingFragment startingFragment;

    Bitmap hero,wall;
    //private int's that can be accessed though setters and getters
    private static  int obstacleRight ,obstacleGap=200,score=0,highscore;
    //a paint(color) to draw the score
    Paint paint;
    //sharepreferences to save the highscore
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    public GameView(Context context) {
        super(context);
        //instanciate everythin in our constructor
        startingFragment = new StartingFragment();
        ma = new MainActivity();
        mp = MediaPlayer.create(context,R.raw.coin);
        go = MediaPlayer.create(context,R.raw.gameover);
        main = MediaPlayer.create(context,R.raw.mainmusic);
        stoneMove = MediaPlayer.create(context,R.raw.stones);
        speedBoost = MediaPlayer.create(context,R.raw.speedboost);
        paint = new Paint(Color.BLACK);
        paint.setTextSize(150);
        holder = getHolder();
        buttonPressed = MediaPlayer.create(context,R.raw.press);
        hero = BitmapFactory.decodeResource(getResources(),R.drawable.hero);
        wall = BitmapFactory.decodeResource(getResources(),R.drawable.wall);

        //set on touch listener in order to get touches.
        setOnTouchListener(this);
        //get the saves highscore
        sharedPreferences = getContext().getSharedPreferences("h",0);
        highscore = sharedPreferences.getInt("h",0);
        //set how big the gap between the two rectangles will be
        obstacleGap = 300;
    }


    @Override
    public void run() {

        while (screenIsReady) {

            if (!holder.getSurface().isValid()) {
                continue;
            } else {
                c = holder.lockCanvas();
                //first time it runs we will start background sprite and obstacles classes
                if(obstacles==false){
                    bg = new Background();
                    sprite = new MySprite(hero,this);
                    ob1= new Obstacle(0,this,wall);
                    ob2 = new Obstacle(-(c.getHeight()/3),this,wall);
                    ob3 = new Obstacle(-2*(c.getHeight()/3),this,wall);
                    //setting obstacles to true so it wont make new objects again its time it loops through
                    obstacles=true;

                }
                //some changes as the game progress in obstacle gap and in speed
                if(score==5&&obstacleGap==300){
                    obstacleGap=275;
                    stoneMove.start();
                }else if(score==10&&obstacleGap==275){
                    stoneMove.start();
                    obstacleGap=250;
                }else if (score==15&&obstacleGap==250){
                    stoneMove.start();
                    obstacleGap=225;
                }else if(score==20&&obstacleGap==225){
                    stoneMove.start();
                    obstacleGap=200;
                }else if(score==40) {
                    ob1.setMaxSpeed(5);
                    ob2.setMaxSpeed(5);
                    ob3.setMaxSpeed(5);
                    //setting lower trasparent than 255 makes it look cooler
                    bg.setTransparent(180);
                    speedBoost.start();
                    sprite.setTopspeed(8);
                }
                myDraw(c);
                holder.unlockCanvasAndPost(c);

            }
        }
    }
    //what happend when you hit a wall
    public void gameIsOver(){
        if(score>highscore){
            highscore=score;
            editor=sharedPreferences.edit();
            editor.putInt("h",highscore);
            editor.commit();        }
        go.start();
        endMusic = true;

    }

    protected void myDraw(Canvas canvas){

        bg.onDraw(canvas);
        ob1.onDraw(canvas);
        ob2.onDraw(canvas);
        ob3.onDraw(canvas);
        sprite.onDraw(canvas);
        canvas.drawText(String.valueOf(score),canvas.getWidth()/2-20,200,paint);
        collision(ob1.getRect1(),ob1.getRect2(),sprite.getSrc());
        collision(ob2.getRect1(),ob2.getRect2(),sprite.getSrc());
        collision(ob3.getRect1(),ob3.getRect2(),sprite.getSrc());






    }

    public void onPause() {
        screenIsReady = false;
        while (thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    public void onResume(){
        obstacles=false;
        screenIsReady=true;
        thread = new Thread(this);
        thread.start();
        score=0;
        obstacleGap=300;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //each time the user touch the screen our hero change direction
        if(motionEvent.getAction()==motionEvent.ACTION_DOWN) {
            buttonPressed.start();
            if (sprite.getDirection() == 2) {
                sprite.setDirection(1);
            } else if (sprite.getDirection() == 1) {
                sprite.setDirection(2);
            }
        }
        return true;
    }
    //collision between the player and the walls
    public void collision(Rect obstacle1,Rect obstacle2,Rect player){
        if(sprite.getGameIsOn()) {
            try{
            if (obstacle1.intersect(player) || obstacle2.intersect(player)) {
                //onResume();
                ob1.setGameisOn(false);
                ob2.setGameisOn(false);
                ob3.setGameisOn(false);
                sprite.setGameisOn(false);

            }}catch (Exception e){

            }
        }
    }

    public void scoreAndCoin(){
        score++;
        mp.start();
    }


    public void setObstacleRight(int obstacleRight){
        this.obstacleRight=obstacleRight;

    }
    public int getObstacleRight(){
        return obstacleRight;
    }

    public int getObstacleGap() {
        return obstacleGap;
    }

    public static void setObstacleGap(int obstacleGap) {
        GameView.obstacleGap = obstacleGap;
    }
}


