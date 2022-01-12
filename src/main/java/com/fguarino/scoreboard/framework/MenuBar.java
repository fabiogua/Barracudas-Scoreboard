package com.fguarino.scoreboard.framework;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;

public class MenuBar {
 
    
    static Menu settingsMenu = new Menu("Settings");
    //static Menu matchMenu = new Menu("Match");
    static javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
    static MenuItem matchMenuItem = new MenuItem("Match");
    //static MenuItem playerMenuItem = new MenuItem("Player");
    //static MenuItem timeMenuItem = new MenuItem("Time");

    public MenuBar(){
        
        menuBar.getMenus().addAll(settingsMenu/*, matchMenu*/);
        //playerMenuItem.setOnAction(e -> Globals.matchSettings.open());
        //timeMenuItem.setOnAction(e -> Globals.matchSettings.open());
        matchMenuItem.setOnAction(e -> Globals.matchSettings.open());
        settingsMenu.getItems().add(matchMenuItem);
        //matchMenu.getItems().addAll(resetMenuItem, playerMenuItem);  
    }

    public javafx.scene.control.MenuBar getRoot(){
        return menuBar;
    }
}
