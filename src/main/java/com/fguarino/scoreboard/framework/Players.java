package com.fguarino.scoreboard.framework;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class Players {
    VBox homePlayerBoxS = new VBox();
    VBox guestPlayerBoxS = new VBox();

    VBox homePlayerBoxC = new VBox();
    VBox guestPlayerBoxC = new VBox();

    public Players(){

        for(int i = 0; i < 13; i++){
            HomePlayer hp = new HomePlayer(i+1);
            homePlayerBoxS.getChildren().add(hp.getRootS());
            homePlayerBoxS.setAlignment(Pos.CENTER_LEFT);
            homePlayerBoxC.getChildren().add(hp.getRootC());
            GuestPlayer gp = new GuestPlayer(i+1);
            guestPlayerBoxS.getChildren().add(gp.getRootS());
            guestPlayerBoxS.setAlignment(Pos.CENTER_RIGHT);
            guestPlayerBoxC.getChildren().add(gp.getRootC());
        }
    }

    public VBox getHomePlayerBoxC() {
        return homePlayerBoxC;
    }
    public VBox getHomePlayerBoxS() {
        return homePlayerBoxS;
    }
    public VBox getGuestPlayerBoxC() {
        return guestPlayerBoxC;
    }
    public VBox getGuestPlayerBoxS() {
        return guestPlayerBoxS;
    }
}
