package com.fguarino.scoreboard.framework;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

public class ScoreboardLivePlayer{

    public static Player[] guestPlayers;
    boolean isCorrectMode = false;
    Shadow playerShadow;
    String matchName = "FINA WORLDCUP";
    @FXML
    Label matchNameLabel;
    @FXML
    VBox homePaneS, penaltyPaneS, guestPaneS, homePaneC, penaltyPaneC, guestPaneC;
    MatchTime m;
    Timeout timeout;

    HomeTeam homeTeam;
    GuestTeam guestTeam;
    Players players;
    CenterBox centerBox;
    MenuBar menuBar;
    MatchSettings matchSettings;
    Horn horn;

    Parent rootS, rootC;
        
    public ScoreboardLivePlayer (){    
        //Globals.scoreboardRef = this;
        horn = new Horn();
        Globals.horn = horn;

        playerShadow = new Shadow();
        Globals.playerShadow = playerShadow.getPlayerShadow();
        try{


            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/scoreboard2.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/controlboard2.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();

        } catch (IOException e) {
            //TODO: handle exception
        }

        matchNameLabel.setText(matchName);


        m = new MatchTime();
        Globals.matchTime = m;

        timeout = new Timeout();
        Globals.timeoutRef = timeout; 

        homeTeam = new HomeTeam();
        Globals.homeTeam = homeTeam;

        guestTeam = new GuestTeam();
        Globals.guestTeam = guestTeam;

        players = new Players();
        centerBox = new CenterBox();
        Globals.centerBox = centerBox;

        menuBar = new MenuBar();
        matchSettings = new MatchSettings();
        Globals.matchSettings = matchSettings;

        homePaneS.getChildren().add(players.homePlayerBoxS);
        guestPaneS.getChildren().add(players.guestPlayerBoxS);

        homePaneC.getChildren().add(players.homePlayerBoxC);
        guestPaneC.getChildren().add(players.guestPlayerBoxC);
        

        /*
        matchNameLabelS.setEffect(Globals.playerShadow);
        matchNameLabelS.setTextAlignment(TextAlignment.CENTER);
        matchNameLabelS.setTextFill(Globals.MATCHNAME_COLOR);
        matchNameLabelS.setFont(Globals.MATCHNAME_FONT);
        matchNameLabelS.setAlignment(Pos.TOP_CENTER);
        */
        createControlStage();
        createScoreboardStage();
    }

    public void createControlStage(){
        Stage controlStage = new Stage();
        /*
        BorderPane rootC = new BorderPane();
        BorderPane bottomBoxC = new BorderPane();
        BorderPane headerC = new BorderPane();
        VBox topBorderC = new VBox(menuBar.getRoot(),matchNameLabelC, headerC, timeout.getRootC());

        topBorderC.setAlignment(Pos.TOP_CENTER);

        headerC.setLeft(homeTeam.getRootC());
        headerC.setCenter(m.getRootC());
        headerC.setRight(guestTeam.getRootC());
        m.getRootC().setAlignment(Pos.CENTER);
        headerC.setPadding(new Insets(20,20,0,20));


     bottomBoxC.setLeft(players.getHomePlayerBoxC());
        bottomBoxC.setCenter(Globals.centerBox.getRootC());
        bottomBoxC.setRight(players.getGuestPlayerBoxC());   
        bottomBoxC.setPadding(new Insets(0,20,20,20));


        rootC.setTop(topBorderC);
        rootC.setCenter(bottomBoxC);*/
        Scene controlScene = new Scene(rootC);
        controlStage.setScene(controlScene);
        //rootC.setBackground(Globals.BACKGROUND);
        controlStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        
        controlScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.SPACE){
                    if(Globals.matchTime.getPaused()){
                        startAllTimer();
                    }else{
                        stopAllTimer();
                    }
                }
                if(event.getCode() == KeyCode.C){
                    setCorrectMode(!getCorrectMode());
                    if(getCorrectMode()){
                        //rootC.setBackground(Globals.CORRECT_BACKGROUND);
                    }else{
                        //rootC.setBackground(Globals.BACKGROUND);
                    }
                }

                if(event.getCode() == KeyCode.ENTER){
                    horn.play();
                }
            }
        });

        controlScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    horn.stop();
                }            
            }
        });

        controlStage.show();
    }

    public void createScoreboardStage(){
        Stage scoreboardStage = new Stage();
        /*
        BorderPane root = new BorderPane();
        BorderPane bottomBox = new BorderPane();
        BorderPane headerS = new BorderPane();
        VBox topBorder = new VBox(matchNameLabel, headerS, timeout.getRootS());

        topBorder.setAlignment(Pos.TOP_CENTER);
        topBorder.setPadding(new Insets(20,20,0,20));
        headerS.setLeft(homeTeam.getRootS());
        headerS.setCenter(m.getRootS());
        headerS.setRight(guestTeam.getRootS());
        m.getRootS().setAlignment(Pos.CENTER);

        headerS.setPadding(new Insets(10,20,0,20));

     bottomBox.setLeft(players.getHomePlayerBoxS());
        bottomBox.setCenter(Globals.centerBox.getRootS());
        bottomBox.setRight(players.getGuestPlayerBoxS());  
        bottomBox.setPadding(new Insets(0,20,20,20)); 

        root.setBackground(Globals.BACKGROUND);
        root.setTop(topBorder);
        root.setCenter(bottomBox);
        root.setBackground(Globals.BACKGROUND);
        */
        Scene scoreboardScene = new Scene(rootS);
        scoreboardStage.setScene(scoreboardScene);

        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens
        if (screens.size() > 1) {
            Rectangle2D bounds = screens.get(1).getVisualBounds();
            scoreboardStage.setX(bounds.getMinX());
            scoreboardStage.setY(bounds.getMinY());
            scoreboardStage.setFullScreen(true);
            //scoreboardStage.setWidth(bounds.getWidth());
            //scoreboardStage.setHeight(bounds.getHeight());
        }else{
            scoreboardStage.setFullScreen(true);
        }
        scoreboardStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        scoreboardStage.show();
    }

    public void stopAllTimer(){
        Globals.matchTime.stop();
        for(Penalty p : Globals.penalties){
            p.getPenaltyTimer().stop();
        }
    }

    public void startAllTimer(){
        Globals.matchTime.start();
        for(Penalty p : Globals.penalties){
            p.getPenaltyTimer().start();
        }
    }

    public boolean getCorrectMode(){
        return isCorrectMode;
    }

    public void setCorrectMode(boolean b){
        isCorrectMode = b;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName.toUpperCase();
        refreshMatchNameLabel();
    }

    public void refreshMatchNameLabel(){
        matchNameLabel.setText(matchName);
    }

    public String getMatchName() {
        return matchName;
    }
}