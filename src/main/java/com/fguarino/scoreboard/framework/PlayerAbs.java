package com.fguarino.scoreboard.framework;

import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.fxml.FXML;

public abstract class PlayerAbs {

    int number, goal, penalty;
    String name = "Spielername";
    boolean isPenaltied = false;
    boolean active = false;

    ArrayList<PlayerAbs> homePlayers = new ArrayList<>();
    ArrayList<PlayerAbs> guestPlayers = new ArrayList<>();

    // S = ScoreboardLabel C = ControlLabel
    @FXML
    public Label numberLabelS, goalLabelS, nameLabelS, centerNumberLabelS, penaltyLabelS, numberLabelC, goalLabelC,
            nameLabelC, centerNumberLabelC, penaltyLabelC;
    @FXML
    public Circle pen1CircleS, pen2CircleS, pen3CircleS, pen1CircleC, pen2CircleC, pen3CircleC;
    @FXML
    public Button gButton, sButton, aButton;
    @FXML
    CheckBox playCheckBox;
    @FXML
    public Rectangle rect;
    @FXML
    HBox centerHBoxS, centerHBoxC;
    Penalty pen = new Penalty(this);

    Parent rootS, rootC, centerS, centerC;

    public PlayerAbs(int i) {
        this(i, "Spielername");
    }

    public PlayerAbs(int i, String s) {
        name = s;
        number = i;
        goal = 0;
        penalty = 0;
    }

    // called by the FXML loader after the labels declared above are injected:
    public void initialize() {
    }

    public void activatePlayer() {
    }

    public void refreshActivePlayer() {
        for (PlayerAbs p : Globals.homePlayers) {
            Globals.scoreboardRef.homeBoxS.getChildren().remove(p.centerS);
            if (p.active) {
                Globals.scoreboardRef.homeBoxS.getChildren().add(p.centerS);
            }

        }

        for (PlayerAbs p : Globals.guestPlayers) {
            Globals.scoreboardRef.guestBoxS.getChildren().remove(p.centerS);
            if (p.active) {
                Globals.scoreboardRef.guestBoxS.getChildren().add(p.centerS);
            }
        }

        Globals.homeTeam.refreshPlayerCount();
        Globals.guestTeam.refreshPlayerCount();

    }

    public void addGoal() {
        goal++;
        refreshGoal();
    }

    public void subGoal() {
        goal--;
        refreshGoal();
    }

    public int getGoal() {
        return goal;
    }

    public void addPenalty() {
        if (penalty < 3) {
            penalty++;
            Globals.scoreboardRef.stopAllTimer();
        }
        refreshPenalty();
    }

    public void addPenaltyTimer(HomePlayer p) {
        if (penalty < 3 && !isPenaltied) {
            // Penalty pen = new Penalty(p);
            pen.addPenaltyTimer();
            addPenalty();

            if (!p.active && Globals.homeTeam.homePlayerCount >= 7) {
                for (PlayerAbs pl : Globals.homePlayers) {
                    pl.active = false;
                    pl.playCheckBox.setSelected(false);
                    if (pl.isPenaltied) {
                        pl.active = true;
                        pl.playCheckBox.setSelected(true);
                    }
                }

            }
            p.active = true;
            p.playCheckBox.setSelected(true);
            refreshActivePlayer();
        }
        refreshPenalty();
    }

    public void addPenaltyTimer(GuestPlayer p) {
        if (penalty < 3 && !isPenaltied) {
            // Penalty pen = new Penalty(p);
            pen.addPenaltyTimer();
            addPenalty();

            if (!p.active && Globals.guestTeam.guestPlayerCount >= 7) {
                for (PlayerAbs pl : Globals.guestPlayers) {
                    pl.active = false;
                    pl.playCheckBox.setSelected(false);
                    if (pl.isPenaltied) {
                        pl.active = true;
                        pl.playCheckBox.setSelected(true);
                    }
                }

            }
        }
        p.active = true;
        p.playCheckBox.setSelected(true);
        refreshActivePlayer();
        refreshPenalty();
    }

    public void subPenalty() {
        if (penalty > 0) {
            penalty--;
        }
        refreshPenalty();
    }

    public void subPenaltyTimer() {
        if (isPenaltied) {
            isPenaltied = false;
            pen.deleteTimer();
        }
        subPenalty();
    }

    public int getPenalty() {
        return penalty;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        refreshNameLabel();
    }

    public void refreshNameLabel() {
        nameLabelS.setText(name);
    }

    public void setNameLabelFontSize(int size) {
        nameLabelS.setFont(new Font("Calibri Bold", size));
        refreshNameLabel();
    }

    public Parent getRootS() {
        return rootS;
    }

    public Parent getRootC() {
        return rootC;
    }

    public Parent getCenterC() {
        return centerC;
    }

    public Parent getCenterS() {
        return centerS;
    }

    public void refreshGoal() {
        goalLabelS.setText(String.valueOf(goal));
        goalLabelC.setText(String.valueOf(goal));

        if (goal <= 0) {
            goalLabelS.setVisible(false);
            goalLabelC.setVisible(false);
        } else {
            goalLabelS.setVisible(true);
            goalLabelC.setVisible(true);
        }
    }

    public boolean getPenaltied() {
        return isPenaltied;
    }

    public void setPenaltied(boolean b) {
        isPenaltied = b;
    }

    public void refreshPenalty() {
        switch (penalty) {
            case 0:
                pen1CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen1CircleS.setStroke(Globals.PLAYER_COLOR);

                pen2CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen2CircleS.setStroke(Globals.PLAYER_COLOR);

                pen3CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleS.setStroke(Globals.PLAYER_COLOR);

                pen1CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen1CircleC.setStroke(Globals.PLAYER_COLOR);

                pen2CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen2CircleC.setStroke(Globals.PLAYER_COLOR);

                pen3CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleC.setStroke(Globals.PLAYER_COLOR);
                break;

            case 1:
                pen1CircleS.setStroke(Globals.PLAYER_COLOR);
                pen1CircleS.setFill(Globals.PLAYER_COLOR);

                pen2CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen2CircleS.setStroke(Globals.PLAYER_COLOR);

                pen3CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleS.setStroke(Globals.PLAYER_COLOR);

                pen1CircleC.setStroke(Globals.PLAYER_COLOR);
                pen1CircleC.setFill(Globals.PLAYER_COLOR);

                pen2CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen2CircleC.setStroke(Globals.PLAYER_COLOR);

                pen3CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleC.setStroke(Globals.PLAYER_COLOR);
                break;
            case 2:
                pen1CircleS.setFill(Globals.PLAYER_COLOR);
                pen1CircleS.setStroke(Globals.PLAYER_COLOR);

                pen2CircleS.setFill(Globals.PLAYER_COLOR);
                pen2CircleS.setStroke(Globals.PLAYER_COLOR);

                pen3CircleS.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleS.setStroke(Globals.PLAYER_COLOR);

                numberLabelS.setTextFill(Globals.PLAYER_NUMBER_COLOR);
                nameLabelS.setTextFill(Globals.PLAYER_COLOR);

                pen1CircleC.setFill(Globals.PLAYER_COLOR);
                pen1CircleC.setStroke(Globals.PLAYER_COLOR);

                pen2CircleC.setFill(Globals.PLAYER_COLOR);
                pen2CircleC.setStroke(Globals.PLAYER_COLOR);

                pen3CircleC.setFill(Globals.TRANSPARENT_COLOR);
                pen3CircleC.setStroke(Globals.PLAYER_COLOR);

                numberLabelC.setTextFill(Globals.PLAYER_NUMBER_COLOR);
                // nameLabelC.setTextFill(Globals.PLAYER_COLOR);
                break;

            case 3:
                pen1CircleS.setFill(Globals.EXCLUDED_COLOR);
                pen1CircleS.setStroke(Globals.EXCLUDED_COLOR);

                pen2CircleS.setFill(Globals.EXCLUDED_COLOR);
                pen2CircleS.setStroke(Globals.EXCLUDED_COLOR);

                pen3CircleS.setFill(Globals.EXCLUDED_COLOR);
                pen3CircleS.setStroke(Globals.EXCLUDED_COLOR);

                numberLabelS.setTextFill(Globals.EXCLUDED_COLOR);
                nameLabelS.setTextFill(Globals.EXCLUDED_COLOR);

                pen1CircleC.setFill(Globals.EXCLUDED_COLOR);
                pen1CircleC.setStroke(Globals.EXCLUDED_COLOR);

                pen2CircleC.setFill(Globals.EXCLUDED_COLOR);
                pen2CircleC.setStroke(Globals.EXCLUDED_COLOR);

                pen3CircleC.setFill(Globals.EXCLUDED_COLOR);
                pen3CircleC.setStroke(Globals.EXCLUDED_COLOR);

                numberLabelC.setTextFill(Globals.EXCLUDED_COLOR);
                // nameLabelC.setTextFill(Globals.EXCLUDED_COLOR);

                break;
        }
    }
}
