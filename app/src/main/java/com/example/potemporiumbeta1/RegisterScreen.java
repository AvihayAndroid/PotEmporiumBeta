package com.example.potemporiumbeta1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.potemporiumbeta1.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {

    EditText passwordEt,emailEt,usernameEt;
    Button createAccount,gologin;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        passwordEt = (EditText)findViewById(R.id.passwordInput);
        usernameEt = (EditText)findViewById(R.id.usernameInput);
        emailEt = (EditText)findViewById(R.id.emailInput);
        createAccount = (Button)findViewById(R.id.creationbtn);
        mAuth = FirebaseAuth.getInstance();
        gologin = (Button)findViewById(R.id.leavetologinbtn);






        createAccount.setOnClickListener(new View.OnClickListener() {

            // Reading the lists from the database.

            ArrayList<Pair> potValues = new ArrayList<Pair>();
            ArrayList<Pair> ingreValues = new ArrayList<Pair>();
            ArrayList<Pair> keypValues = new ArrayList<Pair>();
            @Override
            public void onClick(View v) {
                refPotionsTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dS = task.getResult();
                        potValues.clear();
                        for (DataSnapshot userSnapshot : dS.getChildren()) {
                            Pair temporary = userSnapshot.getValue(Pair.class);
                            potValues.add(temporary);
                        }
                    }
                });
                refIngredientsTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dS = task.getResult();
                        ingreValues.clear();
                        for (DataSnapshot userSnapshot : dS.getChildren()) {
                            Pair temporary = userSnapshot.getValue(Pair.class);
                            ingreValues.add(temporary);
                        }
                    }
                });
                refKeypiecesTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dS = task.getResult();
                        keypValues.clear();
                        for (DataSnapshot userSnapshot : dS.getChildren()) {
                            Pair temporary = userSnapshot.getValue(Pair.class);
                            keypValues.add(temporary);
                        }
                    }
                });

                // Getting edittext values.

                String email,username,password;
                email = emailEt.getText().toString();
                username = usernameEt.getText().toString();
                password = passwordEt.getText().toString();

                // Making sure fields are not empty.

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterScreen.this, "Email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterScreen.this, "Username field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterScreen.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Account creating process

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterScreen.this, "Account created", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    FirebaseUser fuser = mAuth.getCurrentUser();

                                    // Verification method.

                                    fuser.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Email sent.");
                                                    }
                                                }
                                            });
                                    String id = fuser.getUid();
                                    SharedPreferences settings = getSharedPreferences(id,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean("first_time",true);
                                    editor.commit();
                                    mAuth.signOut();

                                    // Putting all of the lists into hashmaps for user creation

                                    HashMap<String,Integer> PotionsH = new HashMap<String,Integer>();
                                    HashMap<String,Integer> IngredientsH = new HashMap<String,Integer>();
                                    HashMap<String,Integer> KeypiecesH = new HashMap<String,Integer>();

                                    for(int i=0;i<potValues.size();i++){
                                        PotionsH.put(potValues.get(i).getKey(),potValues.get(i).getAmount());
                                    }

                                    for(int j=0;j<ingreValues.size();j++){
                                        IngredientsH.put(ingreValues.get(j).getKey(),ingreValues.get(j).getAmount()+3);
                                    }

                                    for(int k=0;k<keypValues.size();k++){
                                        KeypiecesH.put(keypValues.get(k).getKey(),keypValues.get(k).getAmount());
                                    }



                                    // User creation

                                    try {
                                        Thread.sleep(1000);
                                        User s1 = new User(id,username,PotionsH,IngredientsH,KeypiecesH);
                                        refUsers.child(id).setValue(s1);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {

                                    // Failure to create an account.

                                    Toast.makeText(RegisterScreen.this, "Account creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Login screen intent
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                if(!TextUtils.isEmpty(emailEt.getText())) {
                    intent.putExtra("email",emailEt.getText().toString());
                }
                if(!TextUtils.isEmpty(passwordEt.getText())) {
                    intent.putExtra("password",passwordEt.getText().toString());
                }

                startActivity(intent);
                finish();
            }
        });
    }




}