package com.example.potemporiumbeta1.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.potemporiumbeta1.Misc.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refRecipes;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {
    EditText emailEt, passEt,forgotEt;
    TextView spanText;
    Button loginbtn, registerbtn,testbtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ConstraintLayout mydialog;
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
    boolean turnreceiver = true;


    // Sign the user in automatically if they meet the requirements.

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
        Intent intent2 = getIntent();
        if(intent2.getBooleanExtra("internet",false)==true){
            mAuth.signOut();
            turnreceiver = false;
        }
        if (!turnreceiver){
            unregisterReceiver(networkStateReceiver);
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            Intent intent = new Intent(getApplicationContext(), ShopFront.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (turnreceiver){
            unregisterReceiver(networkStateReceiver);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        emailEt = (EditText) findViewById(R.id.emailEt);
        passEt = (EditText) findViewById(R.id.passwordEt);
        loginbtn = (Button) findViewById(R.id.lgnbtn);
        registerbtn = (Button) findViewById(R.id.createbtn);
        testbtn = (Button) findViewById(R.id.testbtn);
        spanText = (TextView) findViewById(R.id.spanText);
        mAuth = FirebaseAuth.getInstance();
        mydialog=(ConstraintLayout) getLayoutInflater().inflate(R.layout.forgot_password_dialog,null);
        forgotEt = (EditText) mydialog.findViewById(R.id.passwordresetEt);




        try {
            Intent intent = getIntent();
            if(!intent.getStringExtra("email").equals(null)){
                emailEt.setText(intent.getStringExtra("email"));
            }
            if(!intent.getStringExtra("password").equals(null)){
                passEt.setText(intent.getStringExtra("password"));
            }
        }catch (Exception e){
        }

        DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    if (!TextUtils.isEmpty(forgotEt.getText().toString())) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        String emailAddress = forgotEt.getText().toString();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(LoginScreen.this, "Please enter a valid mail", Toast.LENGTH_SHORT).show();
                    }
                }
                if(which == DialogInterface.BUTTON_NEGATIVE){
                    dialog.cancel();
                }
            }
        };

        SpannableString ss = new SpannableString(spanText.getText().toString());
        ClickableSpan cS = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                mydialog=(ConstraintLayout) getLayoutInflater().inflate(R.layout.forgot_password_dialog,null);
                forgotEt = (EditText) mydialog.findViewById(R.id.passwordresetEt);
                AlertDialog.Builder adb = new AlertDialog.Builder(LoginScreen.this);
                adb.setView(mydialog);
                mydialog.setBackgroundColor(Color.WHITE);
                adb.setTitle("Password reset");
                adb.setPositiveButton("Send email",myclick);
                adb.setNegativeButton("Cancel",myclick);
                adb.show();
            }
        };
        ss.setSpan(cS,0,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setText(ss);
        spanText.setMovementMethod(LinkMovementMethod.getInstance());



// For updating database lists.

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Pair> TEST = new ArrayList<Pair>();
                TEST.add(new Pair("Cosmic Sight",0));
                TEST.add(new Pair("Elixir of Despair",0));
                TEST.add(new Pair("Potion of Strength",0));
                TEST.add(new Pair("Explosion in a Vial",0));
                TEST.add(new Pair("Fatal Concoction",0));

                ArrayList<Pair> TEST2 = new ArrayList<Pair>();
                TEST2.add(new Pair("Geqiali",0));
                TEST2.add(new Pair("Annelic",0));
                TEST2.add(new Pair("Saqutro",0));
                TEST2.add(new Pair("Onnoron",0));
                TEST2.add(new Pair("Pogiram",0));
                TEST2.add(new Pair("Bagasop",0));
                TEST2.add(new Pair("Hivisley",0));
                TEST2.add(new Pair("Araron",0));
                TEST2.add(new Pair("Baddege",0));
                TEST2.add(new Pair("Focrumeric",0));

                ArrayList<Pair> TEST3 = new ArrayList<Pair>();
                TEST3.add(new Pair("Key Piece 1",0));
                TEST3.add(new Pair("Key Piece 2",0));
                TEST3.add(new Pair("Key Piece 3",0));
                TEST3.add(new Pair("Key Piece 4",0));
                TEST3.add(new Pair("Key Piece 5",0));


                ArrayList<Pair> potion1 = new ArrayList<Pair>();
                ArrayList<Pair> potion2 = new ArrayList<Pair>();
                ArrayList<Pair> potion3 = new ArrayList<Pair>();
                ArrayList<Pair> potion4 = new ArrayList<Pair>();
                ArrayList<Pair> potion5 = new ArrayList<Pair>();

                potion1.add(new Pair("Geqiali",2));
                potion1.add(new Pair("Annelic",1));
                potion1.add(new Pair("Onnoron",3));

                potion2.add(new Pair("Saqutro",3));
                potion2.add(new Pair("Onnoron",2));
                potion2.add(new Pair("Focrumeric",2));

                potion3.add(new Pair("Pogiram",1));
                potion3.add(new Pair("Bagasop",3));
                potion3.add(new Pair("Geqiali",1));

                potion4.add(new Pair("Hivisley",2));
                potion4.add(new Pair("Araron",2));
                potion4.add(new Pair("Baddege",3));

                potion5.add(new Pair("Baddege",1));
                potion5.add(new Pair("Focrumeric",1));
                potion5.add(new Pair("Saqutro",2));






                refPotionsTable.setValue(TEST);
                refIngredientsTable.setValue(TEST2);
                refKeypiecesTable.setValue(TEST3);
                refRecipes.child("Cosmic Sight").setValue(potion1);
                refRecipes.child("Elixir of Despair").setValue(potion2);
                refRecipes.child("Potion of Strength").setValue(potion3);
                refRecipes.child("Explosion in a Vial").setValue(potion4);
                refRecipes.child("Fatal Concoction").setValue(potion5);





            }
        });









        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting edittext values.

                String user,password;
                user = emailEt.getText().toString();
                password = passEt.getText().toString();

                // Making sure fields are not empty

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(LoginScreen.this, "Email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginScreen.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in method.

                mAuth.signInWithEmailAndPassword(user, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    FirebaseUser fuser = mAuth.getCurrentUser();
                                    if(fuser.isEmailVerified()){
                                        Toast.makeText(LoginScreen.this, "Login successful.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ShopFront.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    // Failure to log in exceptions.

                                    else{
                                        Toast.makeText(LoginScreen.this, "Your email is not verified, please verify to proceed", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                {
                                    try{
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e){
                                        Toast.makeText(LoginScreen.this, "Email is not registered, create an account with that Email", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e){
                                        Toast.makeText(LoginScreen.this, "Some or all of the credentials are incorrect", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseNetworkException e){
                                        Toast.makeText(LoginScreen.this, "There has been an issue related with the network", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e){
                                        Toast.makeText(LoginScreen.this, "Something went wrong while trying to log in", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
            }
        });

        // Register screen intent.

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(intent);
                finish();
            }
        });




    }



}