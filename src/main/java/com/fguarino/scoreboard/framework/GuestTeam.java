package com.fguarino.scoreboard.framework;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

public class GuestTeam extends Team{
    
    public GuestTeam(){
        super("BLUE");

        try{
            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/guestTeam.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/guestTeamC.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        refreshPlayerCount();
        timeOutButtonC.setOnAction(e -> {if(Globals.scoreboardRef.getCorrectMode()){subTimeOut();}else{addTimeOut();}});

    }

    @Override
    public void refreshPlayerCount() {
        // TODO Auto-generated method stub
        super.refreshPlayerCount();
        playerCountLabel.setText(String.valueOf(guestPlayerCount) + "/7");
        if(guestPlayerCount == 7){
            playerCountLabel.setTextFill(Color.WHITE);
        }else{
            playerCountLabel.setTextFill(Color.RED);
        }
    }
}
