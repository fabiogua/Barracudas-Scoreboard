package com.fguarino.newScoreboard;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class Scoreboard {

    public static Player[] guestPlayers;
    boolean isCorrectMode = false;
    Shadow playerShadow;
    String matchName = "FINA WORLDCUP";
    @FXML
    BorderPane root, rootCC;
    @FXML
    MenuItem settingsMenu;
    @FXML
    Label matchNameLabel;
    @FXML
    VBox homePaneS, penaltyPaneS, guestPaneS, homePaneC, penaltyPaneC, guestPaneC, timePaneS, timePaneC;
    @FXML
    Label periodLabelS, timeoutLabelS, timeLabelS;
    @FXML
    AnchorPane homeTeamS, guestTeamS, homeTeamC, guestTeamC;
    MatchTime m;
    Timeout timeout;
    Scene controlScene;

    HomeTeam homeTeam;
    GuestTeam guestTeam;
    Players players;
    @FXML
    GridPane centerBoxC;
    @FXML
    VBox homeBoxS, guestBoxS;
    MenuBar menuBar;
    Horn horn;

    Stage scoreboardStage = new Stage();
    Stage controlStage = new Stage();

    Parent rootS, rootC;

    public Scoreboard() {
        Globals.scoreboardRef = this;
        horn = new Horn();
        Globals.horn = horn;

        playerShadow = new Shadow();
        Globals.playerShadow = playerShadow.getPlayerShadow();
        try {

            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/scoreboard.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/controlboard.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();

        } catch (IOException e) {
            // TODO: handle exception
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

        menuBar = new MenuBar();

        homePaneS.getChildren().addAll(players.homePlayerBoxS);
        guestPaneS.getChildren().addAll(players.guestPlayerBoxS);
        homeTeamS.getChildren().add(homeTeam.getRootS());
        guestTeamS.getChildren().add(guestTeam.getRootS());
        timePaneS.getChildren().add(m.getRootS());

        timeLabelS = m.getTimeLabelS();
        periodLabelS = m.getPeriodLabelS();
        timeLabelS = timeout.getTimeoutLabelS();

        homePaneC.getChildren().add(players.homePlayerBoxC);
        guestPaneC.getChildren().add(players.guestPlayerBoxC);
        homeTeamC.getChildren().add(homeTeam.getRootC());
        guestTeamC.getChildren().add(guestTeam.getRootC());
        timePaneC.getChildren().add(m.getRootC());

        settingsMenu.setOnAction(e -> Globals.matchSettings.open());

        createControlStage();
        createScoreboardStage();
    }

    public void createControlStage() {
        ObservableList<Screen> screens = Screen.getScreens();// Get list of Screens

        if (screens.size() < 1) {
            Rectangle2D bounds = screens.get(0).getVisualBounds();
            controlStage.setX(bounds.getMinX());
            controlStage.setY(bounds.getMinY());
            controlStage.setFullScreen(true);
        } else {
            Rectangle2D bounds = screens.get(0).getVisualBounds();

            controlStage.setFullScreen(false);
            controlStage.setWidth(bounds.getWidth());
            controlStage.setHeight(bounds.getHeight());

            if (bounds.getHeight() / screens.get(0).getOutputScaleX() <= 800) {
                // matchNameLabel.setVisible(false);
            }

        }
        root.setBackground(Globals.BACKGROUND);
        root.setMaxHeight(screens.get(0).getBounds().getHeight());

        controlScene = new Scene(rootC);
        controlStage.setScene(controlScene);
        rootCC.setBackground(Globals.BACKGROUND);
        controlStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });

        controlScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    if (!Globals.timeoutRef.isRunning) {

                        if (Globals.matchTime.getPaused()) {
                            startAllTimer();
                        } else {
                            stopAllTimer();
                        }
                    }
                }
                if (event.getCode() == KeyCode.C) {
                    setCorrectMode(!getCorrectMode());
                    if (getCorrectMode()) {
                        rootCC.setBackground(Globals.CORRECT_BACKGROUND);

                        Globals.matchTime.periodLabelC.setVisible(false);
                        Globals.matchTime.timeLabelC.setVisible(false);
                        Globals.matchTime.timeoutLabelC.setVisible(false);

                        Globals.matchTime.periodTextField.setVisible(true);

                        if (Globals.timeoutRef.getRunning()) {
                            Globals.matchTime.timeoutTextField.setVisible(true);
                        } else {
                            Globals.matchTime.timeTextField.setVisible(true);
                        }

                    } else {
                        rootCC.setBackground(Globals.BACKGROUND);

                        Globals.matchTime.timeTextField.setVisible(false);
                        Globals.matchTime.periodTextField.setVisible(false);
                        Globals.matchTime.timeoutTextField.setVisible(false);
                        Globals.matchTime.periodLabelC.setVisible(true);

                        if (Globals.timeoutRef.getRunning()) {
                            Globals.matchTime.timeoutLabelC.setVisible(true);
                        } else {
                            Globals.matchTime.timeLabelC.setVisible(true);
                        }
                    }
                }

                if (event.getCode() == KeyCode.ENTER) {
                    horn.play();
                }
            }
        });

        controlScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    horn.stop();
                }
            }
        });

        // controlStage.show();
    }

    public void createScoreboardStage() {
        Scene scoreboardScene = new Scene(rootS);
        scoreboardStage.setScene(scoreboardScene);

        ObservableList<Screen> screens = Screen.getScreens();// Get list of Screens
        if (screens.size() > 1) {
            Rectangle2D bounds = screens.get(1).getVisualBounds();
            scoreboardStage.setX(bounds.getMinX());
            scoreboardStage.setY(bounds.getMinY());
            scoreboardStage.setFullScreen(true);
        } else {
            Rectangle2D bounds = screens.get(0).getVisualBounds();

            scoreboardStage.setFullScreen(true);
            scoreboardStage.setWidth(bounds.getWidth());
            scoreboardStage.setHeight(bounds.getHeight());
        }
        scoreboardStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        // scoreboardStage.show();
    }

    public void stopAllTimer() {
        Globals.matchTime.stop();
        Globals.matchTime.timeLabelC.setTextFill(Color.RED);
        ;

        for (Penalty p : Globals.penalties) {
            p.getPenaltyTimer().stop();
        }
    }

    public void startAllTimer() {
        Globals.matchTime.start();
        Globals.matchTime.timeLabelC.setTextFill(Color.WHITE);
        ;

        for (Penalty p : Globals.penalties) {
            p.getPenaltyTimer().start();
        }
    }

    public boolean getCorrectMode() {
        return isCorrectMode;
    }

    public void setCorrectMode(boolean b) {
        isCorrectMode = b;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName.toUpperCase();
        refreshMatchNameLabel();
    }

    public void refreshMatchNameLabel() {
        matchNameLabel.setText(matchName);
    }

    public String getMatchName() {
        return matchName;
    }
}