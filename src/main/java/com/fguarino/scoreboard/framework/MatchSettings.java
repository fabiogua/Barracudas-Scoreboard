package com.fguarino.scoreboard.framework;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MatchSettings {

    @FXML
    BorderPane root;
    @FXML
    TextField matchNameTextField, matchTimeTextField, shortPauseTextField, longPauseTextField, homeNameTextField,
            guestNameTextField;
    @FXML
    VBox homeBox, guestBox;
    @FXML
    Button startButton, quitButton;
    @FXML
    CheckBox showNameCheckBox;

    boolean isNameActive = false;
    @FXML
    Button homeLogoButton, guestLogoButton;

    Stage stage = new Stage();

    Scene scene;

    MatchSettings() {
        try {

            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/settings.fxml"));
            fxmlLoaderS.setController(this);
            root = fxmlLoaderS.load();

        } catch (IOException e) {
        }

        FileChooser homeFileChooser = new FileChooser();
        homeFileChooser.setTitle("Open Resource File");

        homeLogoButton.setOnAction(e -> {
            File file = homeFileChooser.showOpenDialog(stage);

            if (file != null) {
                Globals.homeTeam.setLogo(file.getAbsolutePath());
            }
        });

        FileChooser guestFileChooser = new FileChooser();
        guestFileChooser.setTitle("Open Resource File");

        guestLogoButton.setOnAction(e -> {
            File file = guestFileChooser.showOpenDialog(stage);

            if (file != null) {
                Globals.guestTeam.setLogo(file.getAbsolutePath());
            }
        });

        // homeNameTextField.setOnAction(e ->
        // Globals.homeTeam.setTeamName(homeNameTextField.getText().toUpperCase()));
        homeNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue,
                    final String newValue) {
                homeNameTextField.setText(checkInput(homeNameTextField, 3));
            }
        });

        homeNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
                    Globals.homeTeam.setTeamName(checkInput(homeNameTextField, 3).toUpperCase());
                    homeNameTextField.clear();

                    if (event.getCode().equals(KeyCode.ENTER)) {
                        root.requestFocus();
                    }
                    homeNameTextField.setText(Globals.homeTeam.getTeamName());
                }

                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    root.requestFocus();
                    homeNameTextField.clear();
                    homeNameTextField.setText(Globals.homeTeam.getTeamName());
                }
            }
        });

        for (PlayerAbs p : Globals.homePlayers) {
            SettingsPlayer s = new SettingsPlayer(p);
            homeBox.getChildren().add(s.getRoot());
        }

        homeNameTextField.setPromptText("TEAMKÜRZEL");
        guestNameTextField.setPromptText("TEAMKÜRZEL");

        // guestNameTextField.setOnAction(e ->
        // Globals.guestTeam.setTeamName(guestNameTextField.getText().toUpperCase()));
        guestNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
                    Globals.guestTeam.setTeamName(checkInput(guestNameTextField, 3).toUpperCase());
                    guestNameTextField.clear();
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        root.requestFocus();
                    }
                    guestNameTextField.setText(Globals.guestTeam.getTeamName());
                }

                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    root.requestFocus();
                    guestNameTextField.clear();
                    guestNameTextField.setText(Globals.guestTeam.getTeamName());
                }
            }

        });
        guestNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue,
                    final String newValue) {
                guestNameTextField.setText(checkInput(guestNameTextField, 3));
            }
        });

        for (PlayerAbs p : Globals.guestPlayers) {
            SettingsPlayer s = new SettingsPlayer(p);
            guestBox.getChildren().add(s.getRoot());
        }
        removeNameLabel();

        startButton.setOnAction(e -> startMatch());
        quitButton.setOnAction(e -> quitMatch());
        matchNameTextField.setOnAction(e -> Globals.scoreboardRef.setMatchName(matchNameTextField.getText()));
        matchTimeTextField
                .setOnAction(e -> Globals.matchTime.setPeriodTime(Long.valueOf(matchTimeTextField.getText()) * 60000));
        // periodTextField.setOnAction(e ->
        // Globals.matchTime.setPeriod(Integer.valueOf(periodTextField.getText())));
        shortPauseTextField.setOnAction(
                e -> Globals.matchTime.setShortPauseTime(Long.valueOf(shortPauseTextField.getText()) * 60000));
        longPauseTextField.setOnAction(
                e -> Globals.matchTime.setLongPauseTime(Long.valueOf(longPauseTextField.getText()) * 60000));
        showNameCheckBox.setOnAction(e -> {
            isNameActive = !isNameActive;
            Globals.isLive = true;

            checkNameLabel();
        });

        scene = new Scene(getRoot());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        root.requestFocus();
        stage.show();

    }

    public void startMatch() {
        startButton.setDisable(true);
        removeNameLabel();
        checkNameLabel();
        Globals.scoreboardRef.scoreboardStage.show();
        Globals.scoreboardRef.controlStage.show();
        stage.close();
    }

    public void checkNameLabel() {
        if (isNameActive) {
            addNameLabel();
            checkLive();
        } else {
            removeNameLabel();
        }
    }

    public void checkLive() {
        if (Globals.isLive) {
            Selenium selenium = new Selenium();
            Globals.selenium = selenium;
            selenium.start();
        } else {
        }
    }

    public void addNameLabel() {
        for (PlayerAbs p : Globals.homePlayers) {
            p.centerHBoxS.getChildren().add(1, p.nameLabelS);
        }

        for (PlayerAbs p : Globals.guestPlayers) {
            if (p.centerHBoxS.getChildren().size() == 1) {
                p.centerHBoxS.getChildren().add(0, p.nameLabelS);
            } else {
                p.centerHBoxS.getChildren().add(1, p.nameLabelS);
            }
        }
    }

    public void removeNameLabel() {
        for (PlayerAbs p : Globals.homePlayers) {
            p.centerHBoxS.getChildren().remove(p.nameLabelS);
        }

        for (PlayerAbs p : Globals.guestPlayers) {
            p.centerHBoxS.getChildren().remove(p.nameLabelS);
        }
    }

    public void quitMatch() {
        if (Globals.scoreboardRef.scoreboardStage.isShowing()) {
            stage.hide();
        } else {
            Platform.exit();
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public void open() {
        stage.hide();
        stage.show();
    }

    class SettingsPlayer {
        HBox pRoot;
        @FXML
        TextField nameTextField;
        @FXML
        Label numberLabel;

        public SettingsPlayer(PlayerAbs p) {

            try {
                FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/settingsPlayer.fxml"));
                fxmlLoaderS.setController(this);
                pRoot = fxmlLoaderS.load();
            } catch (Exception e) {
                // TODO: handle exception
            }

            numberLabel.setText(String.valueOf(p.number));
            nameTextField.setPromptText(p.getName());

            nameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
                        p.setName(checkInput(nameTextField, 14));
                        nameTextField.clear();
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            pRoot.requestFocus();
                        }
                        nameTextField.setText(p.getName());

                    }

                    if (event.getCode().equals(KeyCode.ESCAPE)) {
                        pRoot.requestFocus();
                        nameTextField.clear();
                        nameTextField.setText(p.getName());
                    }
                }

            });
            nameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends String> ov, final String oldValue,
                        final String newValue) {
                    nameTextField.setText(checkInput(nameTextField, 14));
                }
            });
        }

        public HBox getRoot() {
            return pRoot;
        }
    }

    public String checkInput(TextField t, int limit) {
        String s = t.getText();
        if (s.length() > limit) {
            s = s.substring(0, limit);
        }
        return s;
    }
}
