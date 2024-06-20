package com.example.potemporiumbeta1.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.potemporiumbeta1.Misc.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {

    EditText passwordEt,emailEt,usernameEt;
    Button createAccount,gologin;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


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


        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);

        FirebaseUser currentUser = mAuth.getCurrentUser();






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

                                    String errorMessage = getFirebaseAuthErrorMessage(task.getException());
                                    showErrorMessage(errorMessage);
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

    private String getFirebaseAuthErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthException) {
            FirebaseAuthException authException = (FirebaseAuthException) exception;
            switch (authException.getErrorCode()) {
                case "ERROR_INVALID_CUSTOM_TOKEN":
                    return "The custom token format is incorrect. Please check the documentation.";
                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                    return "The custom token corresponds to a different audience.";
                case "ERROR_INVALID_CREDENTIAL":
                    return "The supplied auth credential is malformed or has expired.";
                case "ERROR_INVALID_EMAIL":
                    return "The email address is badly formatted.";
                case "ERROR_WRONG_PASSWORD":
                    return "The password is invalid or the user does not have a password.";
                case "ERROR_USER_MISMATCH":
                    return "The supplied credentials do not correspond to the previously signed in user.";
                case "ERROR_REQUIRES_RECENT_LOGIN":
                    return "This operation is sensitive and requires recent authentication. Log in again before retrying this request.";
                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                    return "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.";
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    return "The email address is already in use by another account.";
                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                    return "This credential is already associated with a different user account.";
                case "ERROR_USER_DISABLED":
                    return "The user account has been disabled by an administrator.";
                case "ERROR_USER_TOKEN_EXPIRED":
                    return "The user\\'s credential is no longer valid. The user must sign in again.";
                case "ERROR_USER_NOT_FOUND":
                    return "There is no user record corresponding to this identifier. The user may have been deleted.";
                case "ERROR_INVALID_USER_TOKEN":
                    return "The user\\'s credential is no longer valid. The user must sign in again.";
                case "ERROR_OPERATION_NOT_ALLOWED":
                    return "This operation is not allowed. You must enable this service in the console.";
                case "ERROR_WEAK_PASSWORD":
                    return "The given password must be 6+ letters.";
                default:
                    return "Authentication failed. Please try again.";
            }
        } else {
            return "An unexpected error occurred. Please try again.";
        }
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }





}