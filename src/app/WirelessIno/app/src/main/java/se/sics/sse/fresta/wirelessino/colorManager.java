package se.sics.sse.fresta.wirelessino;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by konglobemeralt on 2017-09-15.
 */

public class colorManager {

    private Paint p, pRed, pBlue, pYellow, pControls;

    public void colorManager() {
       // p = new Paint();
       // p.setColor(Color.WHITE);
       // p.setTextAlign(Paint.Align.CENTER);
       // p.setTypeface(
       //         "fonts/KellySlab-Regular.ttf"));
       // p.setTextSize(textSize);
        p.setAntiAlias(true);
        pRed = new Paint();
        pRed.setColor(Color.RED);
        pRed.setAntiAlias(true);
        pBlue = new Paint();
        pBlue.setColor(Color.BLUE);
        pBlue.setAntiAlias(true);
        pYellow = new Paint();
        pYellow.setColor(Color.YELLOW);
        pYellow.setAntiAlias(true);
        pControls = new Paint();
        pControls.setColor(Color.argb(220, 100, 180, 180));
        pControls.setAntiAlias(true);

    }

}
