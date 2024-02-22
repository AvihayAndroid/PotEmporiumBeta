package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
    private Handler mHandler = new Handler();

    EditText passwordEt,emailEt,usernameEt;
    Button createAccount,gologin;
    FirebaseAuth mAuth;
    FirebaseUser fuser;


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
            ArrayList<Pair> potValues = new ArrayList<Pair>();
            ArrayList<Pair> ingreValues = new ArrayList<Pair>();
            ArrayList<Pair> keypValues = new ArrayList<Pair>();
            @Override
            public void onClick(View v) {
                Query queryPots = refPotionsTable;
                queryPots.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        potValues.clear();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            Pair temporary = userSnapshot.getValue(Pair.class);
                            potValues.add(temporary);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Query queryIngre = refIngredientsTable;
                queryIngre.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ingreValues.clear();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            Pair temporary2 = userSnapshot.getValue(Pair.class);
                            ingreValues.add(temporary2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Query queryKeyp = refKeypiecesTable;
                queryKeyp.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        keypValues.clear();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            Pair temporary3 = userSnapshot.getValue(Pair.class);
                            keypValues.add(temporary3);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                mHandler.postDelayed(waitsec, 500);

                String email,username,password;
                email = emailEt.getText().toString();
                username = usernameEt.getText().toString();
                password = passwordEt.getText().toString();


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
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterScreen.this, "Account created", Toast.LENGTH_SHORT).show();
                                    FirebaseUser fuser = mAuth.getCurrentUser();
                                    String id = fuser.getUid();
                                    createUser(id,username,potValues,ingreValues,keypValues);
                                } else {
                                    Toast.makeText(RegisterScreen.this, "Account creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void createUser(String uid,String username,ArrayList<Pair> Potions,ArrayList<Pair> Ingredients,ArrayList<Pair> Keypieces){
        HashMap<String,Integer> PotionsH = new HashMap<String,Integer>();
        HashMap<String,Integer> IngredientsH = new HashMap<String,Integer>();
        HashMap<String,Integer> KeypiecesH = new HashMap<String,Integer>();

        for(int i=0;i<Potions.size();i++){
            PotionsH.put(Potions.get(i).getKey(),Potions.get(i).getAmount());
        }

        for(int j=0;j<Ingredients.size();j++){
            IngredientsH.put(Ingredients.get(j).getKey(),Ingredients.get(j).getAmount());
        }

        for(int k=0;k<Keypieces.size();k++){
            KeypiecesH.put(Keypieces.get(k).getKey(),Keypieces.get(k).getAmount());
        }

        User s1 = new User(uid,username,PotionsH,IngredientsH,KeypiecesH);
        refUsers.child(uid).setValue(s1);
    }
    private Runnable waitsec = new Runnable() {
        @Override
        public void run() {
        }
    };


}