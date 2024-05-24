package com.example.potemporiumbeta1.Objects;

import java.util.HashMap;

public class Comms {
    private String user1;
    private String user2;
    private int player1hp;
    private int player2hp;
    private int player1choice;
    private int player2choice;
    private String decision;
    private boolean isopen;

    public Comms(String user1){
        this.user1 = user1;
        this.user2 = "";
        this.player1hp = 20;
        this.player2hp = 20;
        this.player1choice = 0;
        this.player2choice = 0;
        this.decision="";
        this.isopen=true;
    }
    public Comms(){}


    public int getPlayer2hp() {
        return player2hp;
    }

    public void setPlayer2hp(int player2hp) {
        this.player2hp = player2hp;
    }

    public int getPlayer1hp() {
        return player1hp;
    }

    public void setPlayer1hp(int player1hp) {
        this.player1hp = player1hp;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getPlayer2choice() {
        return player2choice;
    }

    public void setPlayer2choice(int player2choice) {
        this.player2choice = player2choice;
    }

    public int getPlayer1choice() {
        return player1choice;
    }

    public void setPlayer1choice(int player1choice) {
        this.player1choice = player1choice;
    }

    public boolean isIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }
}
