package com.fguarino.scoreboard.framework;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class HomePlayer extends PlayerAbs {

    public HomePlayer(int i) {
        super(i);
        Globals.homePlayers.add(this);

        try {
            // FXMLLoader fxmlLoaderS = new
            // FXMLLoader(getClass().getResource("/com/fguarino/homePlayerS.fxml"));
            FXMLLoader fxmlLoaderCenter = new FXMLLoader(
                    getClass().getResource("/com/fguarino/homeCenterPlayerS.fxml"));
            fxmlLoaderCenter.setController(this);
            centerS = fxmlLoaderCenter.load();

            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/homePlayerS2.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/homePlayerC.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        aButton.setOnAction(e -> {
            if (Globals.scoreboardRef.getCorrectMode()) {
                subPenaltyTimer();
            } else {
                addPenaltyTimer(this);
            }
        });
        sButton.setOnAction(e -> {
            if (Globals.scoreboardRef.getCorrectMode()) {
                subPenalty();
            } else {
                addPenalty();
            }
        });
        gButton.setOnAction(e -> {
            if (Globals.scoreboardRef.getCorrectMode()) {
                subGoal();
            } else {
                addGoal();
            }
        });
        playCheckBox.setOnAction(e -> activatePlayer());

        refreshGoal();
        refreshNameLabel();

        numberLabelS.setText(String.valueOf(number));
        centerNumberLabelS.setText(String.valueOf(number));
        goalLabelS.setText(String.valueOf(goal));
        numberLabelC.setText(String.valueOf(number));
        goalLabelC.setText(String.valueOf(goal));
    }

    @Override
    public void activatePlayer() {
        active = !active;
        super.refreshActivePlayer();
    }

    @Override
    public void addGoal() {
        Globals.scoreboardRef.homeTeam.addScore();
        super.addGoal();
    }

    @Override
    public void subGoal() {
        if (super.goal > 0) {
            Globals.scoreboardRef.homeTeam.subScore();
            super.subGoal();
        }
    }

}
