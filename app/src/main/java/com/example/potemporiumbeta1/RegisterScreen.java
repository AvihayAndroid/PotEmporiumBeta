package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterScreen extends AppCompatActivity {

    EditText userEt2,passEt2;
    Button loginbtn2,leave;
    FirebaseAuth mAuth;
    FirebaseUser fuser;
    User s1;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userEt2 = (EditText)findViewById(R.id.registeruserTv);
        passEt2 = (EditText)findViewById(R.id.registerpassTv);
        loginbtn2 = (Button)findViewById(R.id.registerlgnbtn);
        mAuth = FirebaseAuth.getInstance();
        leave = (Button)findViewById(R.id.leavescreen);


        loginbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user,password;
                user = userEt2.getText().toString();
                password = passEt2.getText().toString();

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(MainActivity2.this, "Email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                };
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity2.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(user, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity2.this, "Account created",
                                            Toast.LENGTH_SHORT).show();
                                    createUser(user.substring(0,user.length()-4),password);
                                } else {
                                    Toast.makeText(RegisterScreen.this, "Account creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void createUser(String user,String password){
        User s1 = new User(uid,user,potions,ingredients,keypieces);
        refUser.child(String.valueOf(s1.getUsername())).setValue(s1);
    }


}