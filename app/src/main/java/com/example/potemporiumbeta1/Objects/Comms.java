package com.example.potemporiumbeta1.Objects;

import java.util.HashMap;

public class Comms {
    private String user1;
    private String user2;
    private String winner;
    private String user1name;
    private String user2name;
    private String user1choice;
    private String user2choice;
    private int user1hp;
    private int user2hp;
    private int wager;
    private int counter;
    private boolean isopen;
    private String attack;

    public Comms(String user1,int wager,String username){
        this.user1 = user1;
        this.user2 = "";
        this.user1name=username;
        this.user2name="";
        this.wager = wager;
        this.isopen=true;
        this.user1hp=10;
        this.user2hp=10;
        this.user1choice="";
        this.user2choice="";
        this.winner="";
        this.counter=0;
        this.attack="";

    }
    public Comms(){}


    public boolean isIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    public int getWager() {
        return wager;
    }

    public void setWager(int wager) {
        this.wager = wager;
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

    public int getUser2hp() {
        return user2hp;
    }

    public void setUser2hp(int user2hp) {
        this.user2hp = user2hp;
    }

    public int getUser1hp() {
        return user1hp;
    }

    public void setUser1hp(int user1hp) {
        this.user1hp = user1hp;
    }

    public String getUser2choice() {
        return user2choice;
    }

    public void setUser2choice(String user2choice) {
        this.user2choice = user2choice;
    }

    public String getUser1choice() {
        return user1choice;
    }

    public void setUser1choice(String user1choice) {
        this.user1choice = user1choice;
    }

    public String getUser2name() {
        return user2name;
    }

    public void setUser2name(String user2name) {
        this.user2name = user2name;
    }

    public String getUser1name() {
        return user1name;
    }

    public void setUser1name(String user1name) {
        this.user1name = user1name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }
}
