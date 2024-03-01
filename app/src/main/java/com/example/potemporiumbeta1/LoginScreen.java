package com.example.potemporiumbeta1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.potemporiumbeta1.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {
    EditText emailEt, passEt;
    Button loginbtn, registerbtn,testbtn;
    FirebaseAuth mAuth;

    // Sign the user in automatically if they meet the requirements.

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            Intent intent = new Intent(getApplicationContext(), ShopFront.class);
            startActivity(intent);
            finish();
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
        mAuth = FirebaseAuth.getInstance();



// For updating database lists.

/*        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Pair> TEST = new ArrayList<Pair>();
                TEST.add(new Pair("potion1",0));
                TEST.add(new Pair("potion2",0));
                TEST.add(new Pair("potion3",0));
                TEST.add(new Pair("potion4",0));
                TEST.add(new Pair("potion5",0));
                ArrayList<Pair> TEST2 = new ArrayList<Pair>();
                TEST2.add(new Pair("ingredient1",0));
                TEST2.add(new Pair("ingredient2",0));
                TEST2.add(new Pair("ingredient3",0));
                TEST2.add(new Pair("ingredient4",0));
                TEST2.add(new Pair("ingredient5",0));
                ArrayList<Pair> TEST3 = new ArrayList<Pair>();
                TEST3.add(new Pair("keypiece1",0));
                TEST3.add(new Pair("keypiece2",0));
                TEST3.add(new Pair("keypiece3",0));
                TEST3.add(new Pair("keypiece4",0));
                TEST3.add(new Pair("keypiece5",0));

                refPotionsTable.setValue(TEST);
                refIngredientsTable.setValue(TEST2);
                refKeypiecesTable.setValue(TEST3);





            }
        });*/









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