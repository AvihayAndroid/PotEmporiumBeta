package com.example.potemporiumbeta1;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBRef {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refUsers = FBDB.getReference("Users");
    public static DatabaseReference refOpenLobbies = FBDB.getReference("Comms").child("openLobbies");
    public static DatabaseReference refClosedLobbies = FBDB.getReference("Comms").child("closedLobbies");
    public static DatabaseReference refPotionsTable = FBDB.getReference("Tables").child("Potions");
    public static DatabaseReference refIngredientsTable = FBDB.getReference("Tables").child("Ingredients");
    public static DatabaseReference refKeypiecesTable = FBDB.getReference("Tables").child("Keypieces");

}