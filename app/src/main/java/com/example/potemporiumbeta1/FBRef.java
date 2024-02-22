package com.example.potemporiumbeta1;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBRef {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUser = FBDB.getReference("User");
    public static DatabaseReference refOpenLobbies = FBDB.getReference("Comms").child("openLobbies");
    public static DatabaseReference refClosedLobbies = FBDB.getReference("Comms").child("closedLobbies");
    public static DatabaseReference refPotionTable = FBDB.getReference("Tables").child("Potions");
    public static DatabaseReference refIngredients = FBDB.getReference("Tables").child("Ingredients");
    public static DatabaseReference refKeypieces = FBDB.getReference("Tables").child("Keypieces");

}