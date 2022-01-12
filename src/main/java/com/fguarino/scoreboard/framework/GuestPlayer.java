package com.fguarino.scoreboard.framework;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class GuestPlayer extends PlayerAbs{

    public GuestPlayer(int i) {
        super(i);
        Globals.guestPlayers.add(this);
        try{
            //rootS = FXMLLoader.load(getClass().getResource("/com/fguarino/guestPlayerS.fxml"));

            FXMLLoader fxmlLoaderCenter = new FXMLLoader(getClass().getResource("/com/fguarino/guestCenterPlayerS.fxml"));
            fxmlLoaderCenter.setController(this);
            centerS = fxmlLoaderCenter.load();

            //FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/guestPlayerS.fxml"));
            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/guestPlayerS2.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/guestPlayerC.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();
          
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        aButton.setOnAction(e -> {if(Globals.scoreboardRef.getCorrectMode()){subPenaltyTimer();}else{addPenaltyTimer(this);}});
        sButton.setOnAction(e -> {if(Globals.scoreboardRef.getCorrectMode()){subPenalty();}else{addPenalty();}});
        gButton.setOnAction(e -> {if(Globals.scoreboardRef.getCorrectMode()){subGoal();}else{addGoal();}});
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
    public void addGoal(){
        Globals.scoreboardRef.guestTeam.addScore();
        super.addGoal();
    }

    @Override
    public void subGoal(){
        Globals.scoreboardRef.guestTeam.subScore();
        super.subGoal();
    }
    
}
