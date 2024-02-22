package com.example.potemporiumbeta1;

import com.example.potemporiumbeta1.FBRef;

import java.util.HashMap;

public class User {
    public class User {
        private String uid;
        private String username;
        private int money;
        private int reputation;
        private HashMap<String , Integer> potions;
        private HashMap<String , Integer> ingredients;
        private HashMap<String , Integer> keypieces;

        public User(){

        }
        public User(String uid,String username,HashMap<String , Integer> potions, HashMap<String , Integer> keypieces, HashMap<String , Integer> ingredients){
            this.username=username;
            this.uid=uid;
            this.money=10;
            this.reputation=0;
            this.potions = potions;
            this.ingredients=ingredients;
            this.keypieces=keypieces;
        }

    }
}
