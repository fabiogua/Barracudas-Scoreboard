package com.fguarino.scoreboard.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/*
 * Simple Timer class with the following features:
 *   - Multiple handlers (specified by FunctionPtr objects) can be added
 *     or removed dynamically.
 *   - At the end of each clock tick, defined by the duration argument of the
 *     Timer constructor (default 1/10th second), all associated handlers
 *     will be invoked.
 */
public class Timer {

    private Timeline timeline;
    private Duration duration;
    /*
     * All handlers associated with the Timer are placed in this ArrayList
     */
    private ArrayList<FunctionPtr> handlers;

    public Timer() {
        this(Duration.millis(1));
    }

    public Timer(Duration duration) {
        this.duration = duration;
        handlers = new ArrayList<FunctionPtr>();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyFrame kf = new KeyFrame(duration,
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (FunctionPtr handler : handlers) {
                        handler.invoke();
                    }
                }
            }
        );
        timeline.getKeyFrames().add(kf);
    }

    /*
     * Add handler, if it is not already present, to the list of
     * handlers to run.
     */
    public void addHandler(FunctionPtr handler) {
        if (handler != null) {
            for (FunctionPtr h : handlers) {
                if (handler == h) {
                    return;
                }
            }
            handlers.add(handler);
        }
    }

    /*
     * Remove handler if it exists in the handler list.
     */
    public void removeHandler(FunctionPtr handler) {
        boolean somethingToRemove = false;
        for (FunctionPtr h : handlers) {
            if (handler == h) {
                somethingToRemove = true;
            }
        }
        if (somethingToRemove) {
            handlers.remove(handler);
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}