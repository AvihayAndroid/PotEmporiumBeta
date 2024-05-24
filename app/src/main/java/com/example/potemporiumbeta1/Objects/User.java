package com.example.potemporiumbeta1.Objects;

import java.util.HashMap;
    public class User {

        // User class.

        private String uid;
        private String username;
        private String battleMessage;
        private int money;
        private int reputation;
        private boolean fightUnlocked;
        private HashMap<String , Integer> potions;
        private HashMap<String , Integer> ingredients;
        private HashMap<String , Integer> keypieces;

        public User(String uid,String username,HashMap<String,Integer> potions, HashMap<String,Integer> ingredients, HashMap<String,Integer> keypieces){
            this.username = username;
            this.battleMessage = "";
            this.uid = uid;
            this.money = 100;
            this.reputation = 10;
            this.fightUnlocked = false;
            this.potions = potions;
            this.ingredients = ingredients;
            this.keypieces = keypieces;
        }
        public User(){}

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public HashMap<String, Integer> getPotions() {
            return potions;
        }

        public void setPotions(HashMap<String, Integer> potions) {
            this.potions = potions;
        }

        public HashMap<String, Integer> getIngredients() {
            return ingredients;
        }

        public void setIngredients(HashMap<String, Integer> ingredients) {
            this.ingredients = ingredients;
        }

        public HashMap<String, Integer> getKeypieces() {
            return keypieces;
        }

        public void setKeypieces(HashMap<String, Integer> keypieces) {
            this.keypieces = keypieces;
        }

        public boolean isFightUnlocked() {
            return fightUnlocked;
        }

        public void setFightUnlocked(boolean fightUnlocked) {
            this.fightUnlocked = fightUnlocked;
        }

        public String getBattleMessage() {
            return battleMessage;
        }

        public void setBattleMessage(String battleMessage) {
            this.battleMessage = battleMessage;
        }
    }
