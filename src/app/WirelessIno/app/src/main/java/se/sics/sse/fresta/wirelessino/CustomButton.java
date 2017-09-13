package se.sics.sse.fresta.wirelessino;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Jesper on 2017-09-13.
 *
 * A simple button with toggle functionality
 *
 */

class CustomButton{

    CustomButton(int sizeX, int sizeY, int posX, int posY, Paint color){
        isPressed = false;
        boundingBox = new RectF(sizeX, sizeY, posX, posY);
        buttonColor = color;

        initColors();

    }

    Paint pYellow, pRed;

    public RectF boundingBox;
    boolean isPressed;
    Paint buttonColor;

    public RectF getBounds(){
        return boundingBox;
    }

    public void toggle(){
        isPressed = !isPressed;

        changeColor();
    }

    void initColors(){
        pYellow = new Paint();
        pYellow.setColor(Color.YELLOW);
        pRed = new Paint();
        pRed.setColor(Color.RED);
    }

    private void changeColor(){
        if(isPressed){
            buttonColor = pRed;
        }
        else{
            buttonColor = pYellow;
        }
    }

    Paint getColor(){
        return buttonColor;
    }
}
