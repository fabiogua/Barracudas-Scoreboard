package com.fguarino.scoreboard.framework;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CenterBox {
    HBox timeoutRootS, timeoutRootC, penaltyRootS, penaltyRootC;
    VBox rootS, rootC, homePenaltyBoxS, guestPenaltyBoxS, homePenaltyBoxC, guestPenaltyBoxC;
    Line horizontalLineS, horizontalLineC, verticalLineS, verticalLineC;
    Label homePenLabelS, homePenLabelC, guestPenLabelS, guestPenLabelC;
    
    public CenterBox(){
        rootS = new VBox(10);
        rootC = new VBox(10);

        timeoutRootS = new HBox(10);
        timeoutRootC = new HBox(10);

        penaltyRootS = new HBox(10);
        penaltyRootC = new HBox(10);

        homePenaltyBoxS = new VBox(10);
        homePenaltyBoxC = new VBox(10);
        guestPenaltyBoxS = new VBox(10);
        guestPenaltyBoxC = new VBox(10);

        homePenaltyBoxS.setPrefWidth(200);
        homePenaltyBoxC.setPrefWidth(200);
        guestPenaltyBoxS.setPrefWidth(200);
        guestPenaltyBoxC.setPrefWidth(200);

        timeoutRootS.setPrefHeight(120);
        timeoutRootC.setPrefHeight(120);

        timeoutRootS.setAlignment(Pos.CENTER);
        timeoutRootC.setAlignment(Pos.CENTER);


        homePenaltyBoxS.setAlignment(Pos.TOP_CENTER);
        homePenaltyBoxC.setAlignment(Pos.TOP_CENTER);
        guestPenaltyBoxS.setAlignment(Pos.TOP_CENTER);
        guestPenaltyBoxC.setAlignment(Pos.TOP_CENTER);

        homePenLabelS = new Label("NR - TIME");
        homePenLabelC = new Label("NR - TIME");

        homePenLabelS.setFont(Globals.DEFAULT_FONT);
        homePenLabelS.setTextFill(Globals.PENALTY_COLOR);

        homePenLabelC.setFont(Globals.DEFAULT_FONT);
        homePenLabelC.setTextFill(Globals.PENALTY_COLOR);

        homePenLabelS.setTextAlignment(TextAlignment.CENTER);
        homePenLabelC.setTextAlignment(TextAlignment.CENTER);

        guestPenLabelS = new Label("NR - TIME");
        guestPenLabelC = new Label("NR - TIME");

        guestPenLabelS.setFont(Globals.DEFAULT_FONT);
        guestPenLabelS.setTextFill(Globals.PENALTY_COLOR);

        guestPenLabelC.setFont(Globals.DEFAULT_FONT);
        guestPenLabelC.setTextFill(Globals.PENALTY_COLOR);

        homePenaltyBoxS.getChildren().addAll(homePenLabelS);
        homePenaltyBoxC.getChildren().addAll(homePenLabelC);

        guestPenaltyBoxS.getChildren().addAll(guestPenLabelS);
        guestPenaltyBoxC.getChildren().addAll(guestPenLabelC);
        
        verticalLineS = new Line(0, 0, 0, 400);
        verticalLineS.setStrokeWidth(4);
        verticalLineS.setStroke(Globals.DEFAULT_COLOR);

        verticalLineC = new Line(0, 0, 0, 400);
        verticalLineC.setStrokeWidth(4);
        verticalLineC.setStroke(Globals.DEFAULT_COLOR);

        penaltyRootS.getChildren().addAll(homePenaltyBoxS, verticalLineS, guestPenaltyBoxS);
        penaltyRootC.getChildren().addAll(homePenaltyBoxC, verticalLineC, guestPenaltyBoxC);

        penaltyRootS.setAlignment(Pos.TOP_CENTER);
        penaltyRootC.setAlignment(Pos.TOP_CENTER);

        horizontalLineS = new Line(0, 0, 400, 0);
        horizontalLineS.setStrokeWidth(4);
        horizontalLineS.setStroke(Globals.DEFAULT_COLOR);

        horizontalLineC = new Line(0, 0, 400, 0);
        horizontalLineC.setStrokeWidth(4);
        horizontalLineC.setStroke(Globals.DEFAULT_COLOR);

        rootS.setPrefWidth(300);
        rootC.setPrefWidth(300);

        rootS.setAlignment(Pos.CENTER);
        rootC.setAlignment(Pos.CENTER);

    }
    
    public VBox getRootS() {
        return rootS;
    }

    public VBox getRootC() {
        return rootC;
    }

    public VBox getHomePenaltyBoxC() {
        return homePenaltyBoxC;
    }

    public VBox getHomePenaltyBoxS() {
        return homePenaltyBoxS;
    }

    public VBox getGuestPenaltyBoxC() {
        return guestPenaltyBoxC;
    }

    public VBox getGuestPenaltyBoxS() {
        return guestPenaltyBoxS;
    }
}
