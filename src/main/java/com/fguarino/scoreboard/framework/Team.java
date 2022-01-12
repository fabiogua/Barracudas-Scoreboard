package com.fguarino.scoreboard.framework;

import java.io.FileInputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Team {

    String name, color;
    int timeOut, score, homePlayerCount, guestPlayerCount;
    
    static String teamName = "BAR";

    FileInputStream inputstream;
    Image image;

    String coachName;
    /*

    VBox rootTeamS = new VBox();
    VBox rootTeamC = new VBox();

    HBox timeoutHBoxS = new HBox(30);
    HBox timeoutHBoxC = new HBox(30);*/

    @FXML
    Rectangle colorRectangle;
    @FXML
    Circle timeoutCircle1, timeoutCircle2, timeoutCircle1C, timeoutCircle2C;
    @FXML
    Button timeOutButtonC;
    @FXML
    Label nameLabel, scoreLabel, nameLabelC, scoreLabelC, playerCountLabel;
    Paint teamColor;
    @FXML
    ImageView logoView;

    AnchorPane rootS, rootC;

    public Team(String color){
        if(color.equals("WHITE")){
            teamColor = Color.WHITE;
        }else{
            teamColor = Color.DARKBLUE;
        }

        //setLogo("C:\\Users\\fabio\\Documents\\GitHub\\WaterpoloScoreboard\\src\\logos\\testLogo.png");

    }

    public String getCoachName() {
        return coachName;
    }
    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
        refreshScore();
    }
    
    public void addScore(){
        score++;
        refreshScore();
    }

    public void subScore(){
        if(score>0){
            score--;
            refreshScore();
        }
    }

    public void refreshPlayerCount(){
        homePlayerCount = 0;
        guestPlayerCount = 0;

        for (PlayerAbs p : Globals.homePlayers) {
            if(p.active){
                homePlayerCount++;
            }
        }

        for (PlayerAbs p : Globals.guestPlayers) {
            if(p.active){
                guestPlayerCount++;
            }
        }


    }

    public static String getTeamName() {
        return teamName;
    }

    public void setTeamName(String t) {
        teamName = t;
        refreshNameLabel();
    }
    
    private void refreshNameLabel() {
        nameLabel.setText(teamName);
        nameLabelC.setText(teamName);
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        refreshTimeOut();
    }

    public void addTimeOut(){
        if(timeOut < 2){
            if(!Globals.timeoutRef.getRunning()){
                timeOut++;
                Globals.timeoutRef.setTimer();
            }
        }
        Globals.scoreboardRef.stopAllTimer();
        refreshTimeOut();
    }

    public void subTimeOut(){
        if(timeOut > 0){
            timeOut--;
        }
        refreshTimeOut();
    }

    public AnchorPane getRootC() {
        return rootC;
    }

    public AnchorPane getRootS() {
        return rootS;
    }

    public void refreshScore(){
        scoreLabel.setText(String.valueOf(score));
        scoreLabelC.setText(String.valueOf(score));
    }

    public void setLogo(String s){

        try {
            inputstream = new FileInputStream(s);;
            image = new Image(inputstream);
            logoView.setImage(image);
        } catch (Exception e) {
        }
    }

    public void refreshTimeOut(){
        switch(timeOut){
            case 0:
                timeoutCircle1.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle1.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle2.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle2.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle1C.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle1C.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle2C.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle2C.setStroke(Globals.PLAYER_COLOR);
            break;
        
            case 1:
                timeoutCircle1.setFill(Globals.PLAYER_COLOR);
                timeoutCircle2.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle2.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle2.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle1C.setFill(Globals.PLAYER_COLOR);
                timeoutCircle2C.setStroke(Globals.PLAYER_COLOR);

                timeoutCircle2C.setFill(Globals.TRANSPARENT_COLOR);
                timeoutCircle2C.setStroke(Globals.PLAYER_COLOR);
            break;

            case 2:
                timeoutCircle1.setFill(Globals.EXCLUDED_COLOR);
                timeoutCircle1.setStroke(Globals.EXCLUDED_COLOR);

                timeoutCircle2.setFill(Globals.EXCLUDED_COLOR);
                timeoutCircle2.setStroke(Globals.EXCLUDED_COLOR);

                timeoutCircle1C.setFill(Globals.EXCLUDED_COLOR);
                timeoutCircle1C.setStroke(Globals.EXCLUDED_COLOR);

                timeoutCircle2C.setFill(Globals.EXCLUDED_COLOR);
                timeoutCircle2C.setStroke(Globals.EXCLUDED_COLOR);
            break;
            
        }
    }
}
