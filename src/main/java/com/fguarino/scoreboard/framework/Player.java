package com.fguarino.scoreboard.framework;

public class Player {
    TeamEnum team;
    int number, goal, penalty;
    String name;
    boolean isPenaltied;

    public Player(TeamEnum team, int number){
        this(team, number, " ");
    }
    
    public Player(TeamEnum team, int number, String name){
        this.team = team;
        this.number = number;
        this.name = name;
    }

    public TeamEnum getTeam() {
        return team;
    }

    public void setPenaltied(boolean b) {
        isPenaltied = b;
    }

    public boolean getPenaltied() {
        return isPenaltied;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}

