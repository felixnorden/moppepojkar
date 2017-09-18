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

    /**
     * gets the bounding box of the button as defined by a RectF object.
     * @return Return RectF
     */
    public RectF getBounds(){
        return boundingBox;
    }

    /**
     * Toggles the isPressed state of the button
     */
    public void toggle(){
        isPressed = !isPressed;
        changeColor();
    }

    /**
     * Initiates the colours used by the button to show current state.
     */
    private void initColors(){
        pYellow = new Paint();
        pYellow.setColor(Color.YELLOW);
        pRed = new Paint();
        pRed.setColor(Color.RED);
    }


    /**
     * Changes the colour of the button depending on current state.
     */
    private void changeColor(){
        if(isPressed){
            buttonColor = pRed;
        }
        else{
            buttonColor = pYellow;
        }
    }

    /**
     * Returns the colour of the button as a Paint object.
     * @return Return Paint
     */
    Paint getColor(){
        return buttonColor;
    }

    Boolean getStatus(){
        return isPressed;
    }
}
