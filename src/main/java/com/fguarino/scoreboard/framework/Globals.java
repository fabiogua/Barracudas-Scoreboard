package com.fguarino.scoreboard.framework;

import java.io.InputStream;
import java.util.ArrayList;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

public class Globals {
    private static final double PENALTY_FONT_SIZE = 60;
    public static final Font PENALTY_FONT = new Font("Arial", PENALTY_FONT_SIZE);
    public static final Paint PENALTY_COLOR = Color.WHITE;

    private static final double PERIOD_FONT_SIZE = 80;
    public static final Font PERIOD_FONT = new Font("Arial", PERIOD_FONT_SIZE);
    public static final Paint PERIOD_COLOR = Color.WHITE;

    private static final double MAINTIME_FONT_SIZE = 150;
    public static final Font MAINTIME_FONT = new Font("Arial", MAINTIME_FONT_SIZE);
    public static final Paint MAINTIME_COLOR = Color.WHITE;

    private static final double PLAYER_FONT_SIZE = 40;

    public static final Font PLAYER_FONT = new Font("Arial", PLAYER_FONT_SIZE);
    public static final Paint PLAYER_COLOR = Color.WHITE;
    public static final Paint PLAYER_BOX_COLOR = Color.TRANSPARENT;
    public static final Paint PLAYER_NUMBER_COLOR = Color.WHITE;
    public static final Paint TRANSPARENT_COLOR = Color.TRANSPARENT;
    public static final Paint EXCLUDED_COLOR = Color.RED;

    private static final double SCORE_SIZE = 150;
    public static final Font SCORE_FONT = new Font("Arial", SCORE_SIZE);
    public static final Paint SCORE_COLOR = Color.WHITE;

    private static final double TIMEOUT_SIZE = 90;
    public static final Font TIMEOUT_FONT = new Font("Arial", TIMEOUT_SIZE);
    public static final Paint TIMEOUT_COLOR = Color.WHITE;

    private static final double TEAM_SIZE = 60;
    public static final Font TEAM_FONT = new Font("Arial", TEAM_SIZE);
    public static final Paint TEAM_COLOR = Color.WHITE;

    private static final double MATCHNAME_SIZE = 100;
    public static final Font MATCHNAME_FONT = new Font("Arial", MATCHNAME_SIZE);
    public static final Paint MATCHNAME_COLOR = Color.WHITE;

    private static final double DEFAULT_SIZE = 40;
    public static final Font DEFAULT_FONT = new Font("Arial", DEFAULT_SIZE);;
    public static final Paint DEFAULT_COLOR = Color.WHITE;
    public static final Paint BACKGROUND_COLOR = 
      new RadialGradient(
        0, 0, 500, 500, 700,
        false,                  //sizing
        CycleMethod.NO_CYCLE,                 //cycling
        new Stop(0, Color.web("#4f7eff")),    //colors
        new Stop(1, Color.web("#1c306a")));

        /*
        public static final Paint BACKGROUND_COLOR = new RadialGradient(
        0, 10, 0, 1, 1, true,                  //sizing
        CycleMethod.NO_CYCLE,                 //cycling
        new Stop(0, Color.web("#1c306a")),    //colors
        new Stop(1, Color.web("#263ecf")));
*/
    public static final Paint BACKGROUND_CORRECT_COLOR = new RadialGradient(
        0, 0, 3, 0, 0, true,                  //sizing
        CycleMethod.NO_CYCLE,                 //cycling
        new Stop(0, Color.web("#eb5e34")),    //colors
        new Stop(1, Color.web("#fc2803")));
    public static Background BACKGROUND = new Background(new BackgroundFill(BACKGROUND_COLOR, null, null));
    public static Background CORRECT_BACKGROUND = new Background(new BackgroundFill(BACKGROUND_CORRECT_COLOR, null, null));


    static ArrayList<PlayerAbs> homePlayers = new ArrayList<PlayerAbs>();
    static ArrayList<PlayerAbs> guestPlayers = new ArrayList<PlayerAbs>();
    static ArrayList<Penalty> penalties = new ArrayList<Penalty>();
    
    
    static long PERIOD_TIME = 480000;
    static long PENALTY_TIME = 20000;
    static long TIMEOUT_TIME = 60000;
    static long SHORT_PAUSE_TIME = 120000;
    static long LONG_PAUSE_TIME = 180000;

    static InputStream homeLogoInput;
    static InputStream guestLogoInput;

    static Scoreboard scoreboardRef;
    static Timeout timeoutRef;
    static MatchTime matchTime;
    static GuestTeam guestTeam;
    static HomeTeam homeTeam;
    static CenterBox centerBox;
    static MatchSettings matchSettings;
    static Horn horn;

    static DropShadow playerShadow; 
    static DropShadow playerBoxShadow; 

    public static void setPeriodTime(long t) {
        PERIOD_TIME = t;
    }

    public static void setLongPauseTime(long t){
        LONG_PAUSE_TIME = t;
    }

    public static void setShortPauseTime(long t){
        SHORT_PAUSE_TIME = t;
    }
}
