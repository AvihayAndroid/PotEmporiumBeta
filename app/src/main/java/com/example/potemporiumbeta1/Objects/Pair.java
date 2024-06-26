package com.example.potemporiumbeta1.Objects;

public class Pair {

    // Pair class is used to make custom arraylists to save information in the database.

    private String key;
    private Integer amount;
    public Pair(String key,Integer amount){
        this.key=key;
        this.amount=amount;
    }
    public Pair(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
