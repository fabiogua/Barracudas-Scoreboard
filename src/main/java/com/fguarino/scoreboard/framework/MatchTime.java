package com.fguarino.scoreboard.framework;

import java.text.SimpleDateFormat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class MatchTime {
    enum Pause {
        NOPAUSE, SHORTPAUSE, LONGPAUSE;
    }

    static Pause pause = Pause.NOPAUSE;

    Timer mainTimer;
    int period;
    boolean isPaused = true;

    long lastSynced;
    long time;
    String formattedTime;

    AnchorPane rootS, rootC;
    @FXML
    Label periodLabelS, timeLabelS, timeoutLabelS, periodLabelC, timeLabelC, timeoutLabelC;
    @FXML
    Button removeTimerButton, addTimerButton;

    @FXML
    TextField timeTextField, periodTextField, timeoutTextField;

    public MatchTime() {
        this(Globals.PERIOD_TIME);
    }

    public MatchTime(long time) {
        this.time = time;
        lastSynced = time;
        mainTimer = new Timer();
        formattedTime = new SimpleDateFormat("mm:ss").format(time);
        period = 1;

        try {
            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/timeS.fxml"));

            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/timeC.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();

        } catch (Exception e) {
        }

        timeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String s = timeTextField.getText();
                    Long l = convertToLong(s);
                    setTime(l);

                    timeTextField.clear();
                    Globals.matchTime.timeTextField.setPromptText(String.valueOf(Globals.matchTime.formattedTime));
                    rootC.requestFocus();
                }

                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    rootC.requestFocus();
                    timeTextField.clear();
                }
            }

        });

        periodTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    setPeriod(Integer.parseInt(periodTextField.getText()));
                    periodTextField.clear();
                    Globals.matchTime.periodTextField.setPromptText(String.valueOf(Globals.matchTime.period));
                    rootC.requestFocus();
                }

                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    rootC.requestFocus();
                    periodTextField.clear();
                }
            }

        });

        timeoutTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String s = timeoutTextField.getText();
                    Long l = convertToLong(s);
                    Globals.timeoutRef.setTime(l);
                    timeoutTextField.clear();
                    Globals.matchTime.timeoutTextField.setPromptText(String.valueOf(Globals.timeoutRef.formattedTime));
                    rootC.requestFocus();
                }

                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    rootC.requestFocus();
                    timeoutTextField.clear();
                }
            }
        });

        createScoreboard();
        createControlboard();

        FunctionPtr handler = new FunctionPtr() {
            @Override
            public void invoke() {

                if (getTime() == 15000 && (pause == Pause.SHORTPAUSE || pause == Pause.LONGPAUSE)) {
                    Globals.horn.play();
                }

                if (lastSynced - getTime() >= 5000) {
                    if (Globals.isLive) {
                        Globals.selenium.refresh();
                        lastSynced = getTime();
                    }
                }

                if (getTime() == 13000 && (pause == Pause.SHORTPAUSE || pause == Pause.LONGPAUSE)) {
                    Globals.horn.stop();
                }

                if (getTime() > 0) {
                    setTime(getTime() - 1);
                    refreshTime();
                } else {
                    getMatchTimer().stop();
                    setPaused(true);
                    Globals.horn.play();
                    if (period <= 4 && (pause == Pause.LONGPAUSE || pause == Pause.SHORTPAUSE)) {
                        period++;
                        refreshPeriod();
                        setTime(Globals.PERIOD_TIME);
                        pause = Pause.NOPAUSE;
                        refreshTime();
                    } else if ((period == 1 || period == 3) && pause == Pause.NOPAUSE) {
                        setTime(Globals.SHORT_PAUSE_TIME);
                        pause = Pause.SHORTPAUSE;
                        getMatchTimer().start();
                    } else if ((period == 2) && pause == Pause.NOPAUSE) {
                        setTime(Globals.LONG_PAUSE_TIME);
                        pause = Pause.LONGPAUSE;
                        getMatchTimer().start();
                    }
                }
            }
        };

        if (mainTimer != null) {
            getMatchTimer().addHandler(handler);
        }
    }

    public Long convertToLong(String s) {
        Long l = 0L;

        String min;
        String seconds;
        String sec;
        String mil;

        min = s.split(":")[0];

        if (s.split(":").length > 1) {
            seconds = s.split(":")[1];

            if (seconds.split(",").length > 1) {
                sec = seconds.split(",")[0];
                mil = seconds.split(",")[1];
                l = (((Long.parseLong(min) * 60 + Long.parseLong(sec)) * 1000) + Long.parseLong(mil) * 100);
            } else {
                l = ((Long.parseLong(min) * 60 + Long.parseLong(seconds)) * 1000);
            }
        } else if (s.split(":").length <= 1 && s.split(",").length > 1) {

            sec = s.split(",")[0];
            mil = s.split(",")[1];
            l = ((Long.parseLong(sec) * 1000) + Long.parseLong(mil) * 100);

        } else {
            l = (Long.parseLong(min) * 60000);
        }

        return l;
    }

    private void refreshTime() {
        if (getTime() < 10000) {
            formattedTime = new SimpleDateFormat("ss.S").format(getTime());
            formattedTime = formattedTime.substring(1, 4);
        } else if (getTime() < 60000) {
            formattedTime = new SimpleDateFormat("ss.SSS").format(time);
            formattedTime = formattedTime.substring(0, 4);
        } else {
            formattedTime = new SimpleDateFormat("mm:ss").format(time);
        }
        timeLabelS.setText(String.valueOf(formattedTime));
        timeLabelC.setText(String.valueOf(formattedTime));

        Globals.matchTime.timeTextField.setPromptText(String.valueOf(Globals.matchTime.formattedTime));
        Globals.matchTime.periodTextField.setPromptText(String.valueOf(Globals.matchTime.period));
    }

    private void refreshPeriod() {
        periodLabelS.setText(String.valueOf(period));
        periodLabelC.setText(String.valueOf(period));
    }

    public void createScoreboard() {

    }

    public void createControlboard() {
    }

    public void setTime(long t) {
        time = t;
        refreshTime();
    }

    public void setPeriod(int period) {
        this.period = period;
        refreshPeriod();
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriodTime(long t) {
        Globals.setPeriodTime(t);
        if (pause == Pause.NOPAUSE) {
            setTime(t);
        }
    }

    public void setShortPauseTime(long t) {
        Globals.setShortPauseTime(t);
        if (pause == Pause.SHORTPAUSE) {
            setTime(t);
        }
    }

    public void setLongPauseTime(long t) {
        Globals.setLongPauseTime(t);
        if (pause == Pause.LONGPAUSE) {
            setTime(t);
        }
    }

    public long getTime() {
        return time;
    }

    public Timer getMatchTimer() {
        return mainTimer;
    }

    public AnchorPane getRootS() {
        return rootS;
    }

    public AnchorPane getRootC() {
        return rootC;
    }

    public boolean getPaused() {
        return isPaused;
    }

    public void setPaused(boolean b) {
        isPaused = b;
    }

    public void startStop() {
        if (isPaused) {
            start();

        } else {
            stop();
        }
    }

    public void start() {
        if (!Globals.timeoutRef.getRunning()) {
            isPaused = false;
            mainTimer.start();
        }
    }

    public void stop() {
        isPaused = true;
        mainTimer.stop();
    }

    public Label getTimeLabelS() {
        return timeLabelS;
    }

    public Label getTimeLabelC() {
        return timeLabelC;
    }

    public Label getPeriodLabelS() {
        return periodLabelS;
    }

    public Label getPeriodLabelC() {
        return periodLabelC;
    }
}
