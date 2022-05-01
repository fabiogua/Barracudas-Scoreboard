package com.fguarino.scoreboard.framework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
    Scene scene;

    @Override
    public void start(Stage stage) {
        Scoreboard s = new Scoreboard();
        Globals.scoreboardRef = s;
        MatchSettings matchSettings = new MatchSettings();
        Globals.matchSettings = matchSettings;
    }

    public static void main(String[] args) {
        launch(args);

    }
}