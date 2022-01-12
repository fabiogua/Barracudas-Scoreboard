package com.fguarino.scoreboard.framework;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Shadow {
    static DropShadow playerShadow, playerBoxShadow;
 
    public Shadow(){
        playerShadow = new DropShadow();
        playerShadow.setBlurType(BlurType.ONE_PASS_BOX); 
        playerShadow.setColor(Color.rgb(0, 0, 0, 0.3)); 
        playerShadow.setHeight(5); 
        playerShadow.setWidth(5); 
        playerShadow.setRadius(5); 
        playerShadow.setOffsetX(1); 
        playerShadow.setOffsetY(1); 
        playerShadow.setSpread(12);

        playerBoxShadow = new DropShadow();
        playerBoxShadow.setBlurType(BlurType.GAUSSIAN); 
        playerBoxShadow.setColor(Color.rgb(255, 255, 255, 0.4)); 
        playerBoxShadow.setHeight(20); 
        playerBoxShadow.setWidth(5); 
        playerBoxShadow.setRadius(5); 
        playerBoxShadow.setOffsetX(5); 
        playerBoxShadow.setOffsetY(3); 
        playerBoxShadow.setSpread(12);
    }

    public DropShadow getPlayerShadow(){
        return playerShadow;
    }

    public DropShadow getPlayerBoxShadow(){
        return playerBoxShadow;
    }
}
