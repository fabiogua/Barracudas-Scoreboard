package com.fguarino.scoreboard.framework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Penalty {
    Timer penaltyTimer;
    int playerNumber;
    long time;
    String formattedTime = new SimpleDateFormat("ss").format(time);
    PlayerAbs player;


    @FXML
    Label timeLabelS, timeLabelC;

    public Penalty(PlayerAbs p){
        this(p, Globals.PENALTY_TIME);
    }

    public Penalty(PlayerAbs p, long time){
        player = p;
        penaltyTimer = new Timer();
        formattedTime = new SimpleDateFormat("ss").format(time);
        playerNumber = p.getNumber();

        Globals.scoreboardRef.stopAllTimer();

        /*removeTimerButton.setOnAction(e -> { 
            if(p.getPenaltied()){
                deleteTimer();
            }
        });*/

        FunctionPtr handler = new FunctionPtr() {
            @Override
            public void invoke() {

                if (getTime() > 0) {
                    setTime(getTime()-1);

                } else {
                    deleteTimer();
                }
            }
        };
        
        if (penaltyTimer != null) {
            getPenaltyTimer().addHandler(handler);
        }
    }

    private void refreshLabel() {
        formattedTime = new SimpleDateFormat("ss").format(getTime());
        if(getTime() < 10000){
            formattedTime = formattedTime.substring(1, 2);
        }
//        timeLabelS.setText(String.valueOf(formattedTime));
        player.penaltyLabelS.setText(String.valueOf(formattedTime));
        player.penaltyLabelC.setText(String.valueOf(formattedTime));
    }

    public void setTime(long t){
        time = t;
        player.penaltyLabelC.setVisible(true);
        refreshLabel();
    }
    
    public void addPenaltyTimer(){
        Globals.penalties.add(Penalty.this);     
        setTime(Globals.PENALTY_TIME);
        player.penaltyLabelS.setVisible(true);
        player.penaltyLabelC.setVisible(true);
        player.setPenaltied(true);
        if(!Globals.isAutoStop)
        penaltyTimer.start();
    }

    public long getTime() {
        return time;
    }

    public Timer getPenaltyTimer() {
        return penaltyTimer;
    }
    public static ArrayList<Penalty> getPenalties() {
        return Globals.penalties;
    }

    public void deleteTimer(){
        getPenaltyTimer().stop();
        Globals.penalties.remove(this);     
        player.setPenaltied(false);
        player.penaltyLabelS.setVisible(false);
        player.penaltyLabelC.setVisible(false);
    }
}
