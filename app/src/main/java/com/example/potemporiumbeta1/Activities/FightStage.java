package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refLobbies;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLobbies;
import com.example.potemporiumbeta1.Objects.Comms;
import com.example.potemporiumbeta1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FightStage extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Comms comms = new Comms();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_stage);

        Query query1 = refLobbies.orderByChild("user1").equalTo(mAuth.getCurrentUser().getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comms = snapshot.getValue(Comms.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query2 = refLobbies.orderByChild("user2").equalTo(mAuth.getCurrentUser().getUid());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comms = snapshot.getValue(Comms.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}