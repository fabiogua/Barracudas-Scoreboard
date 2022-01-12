package com.fguarino.scoreboard.framework;

import java.text.SimpleDateFormat;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Timeout {
    HBox rootS = new HBox();
    HBox rootC = new HBox();
    
    String formattedTime;
    boolean isRunning = false;
    long time;

    protected Timer timer;
    
    public Timeout(){
        this(Globals.TIMEOUT_TIME);
    }

    public Timeout(long time){
        setTime(time);
        formattedTime = new SimpleDateFormat("mm:ss").format(time);

        try {
            
            FXMLLoader fxmlLoaderS = new FXMLLoader(getClass().getResource("/com/fguarino/timeoutS.fxml"));
            fxmlLoaderS.setController(this);
            rootS = fxmlLoaderS.load();

            FXMLLoader fxmlLoaderC = new FXMLLoader(getClass().getResource("/com/fguarino/timeoutC.fxml"));
            fxmlLoaderC.setController(this);
            rootC = fxmlLoaderC.load();
            
        } catch (Exception e) {
            //TODO: handle exception
        }

        timer = new Timer();


        Globals.matchTime.removeTimerButton.setOnAction(e -> { 
                stop();
        });

        FunctionPtr handler = new FunctionPtr() {
            @Override
            public void invoke() {
                if (getTime() > 0) {
                    setTime(getTime()-1);
                    refreshLabel();
                } else {
                    stop();
                }
            }

            private void refreshLabel() {
                if(getTime() == 15000){
                    Globals.horn.play();
                }
                if(getTime() == 11000){
                    Globals.horn.stop();
                }

                if(getTime() < 10000){
                    formattedTime = new SimpleDateFormat("ss.S").format(getTime());
                    formattedTime= formattedTime.substring(1, 4);
                }else if(getTime() < 60000){
                    formattedTime = new SimpleDateFormat("ss.S").format(getTime());
                    formattedTime= formattedTime.substring(0, 4);
                }else{
                    formattedTime = new SimpleDateFormat("mm:ss").format(getTime());
                }
                Globals.matchTime.timeoutLabelS.setText(String.valueOf(formattedTime));
                Globals.matchTime.timeoutLabelC.setText(String.valueOf(formattedTime));

                if(getTime()<=0){

                }
                Globals.matchTime.timeoutTextField.setPromptText(String.valueOf(Globals.timeoutRef.formattedTime));

            }
        };
        
        if (timer != null) {
            getTimer().addHandler(handler);
        }


    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer() {
        if(!isRunning){
            isRunning = true;
            Globals.scoreboardRef.stopAllTimer();
            setTime(Globals.TIMEOUT_TIME);
                
            Globals.matchTime.timeoutLabelS.setVisible(true);
            Globals.matchTime.timeoutLabelC.setVisible(true);
            Globals.matchTime.periodLabelS.setVisible(false);
            Globals.matchTime.timeLabelC.setVisible(false);
            Globals.matchTime.removeTimerButton.setVisible(true);
            timer.start();
        }
    }

    public void stop(){
        isRunning = false;
        getTimer().stop();
        Globals.matchTime.timeoutLabelS.setVisible(false);
        Globals.matchTime.timeoutLabelC.setVisible(false);
        Globals.matchTime.periodLabelS.setVisible(true);
        Globals.matchTime.timeLabelC.setVisible(true);
        Globals.matchTime.removeTimerButton.setVisible(false);

    }

    public long getTime() {
        return time;
    }

    public void setTime(long t) {
        this.time = t;
    }
    public void setTime() {
        this.time = Globals.TIMEOUT_TIME;
    }

    public void setRunning(boolean b){
        isRunning = b;
    }

    public boolean getRunning(){
        return isRunning;
    }

    public HBox getRootS() {
        return rootS;
    }

    public HBox getRootC() {
        return rootC;
    }

    public Label getTimeoutLabelS() {
        return Globals.matchTime.timeoutLabelC;
    }

    public Label getTimeoutLabelC() {
        return Globals.matchTime.timeoutLabelC;
    }
       
}
