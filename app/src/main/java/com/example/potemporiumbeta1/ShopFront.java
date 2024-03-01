package com.example.potemporiumbeta1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShopFront extends AppCompatActivity {
    private Handler mHandler = new Handler();
    Button leave;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_front);
        leave = (Button) findViewById(R.id.gobackBtn);
        mAuth = FirebaseAuth.getInstance();

        // Sign out button and intent to login screen.

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }



}