package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.Misc.FBRef.refLobbies;
import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Misc.NotificationHelper;
import com.example.potemporiumbeta1.Objects.Comms;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class FightingStage extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Comms comms = new Comms();
    TextView wait,TimeRemaining,myhealth,enemyhealth,mychoice,opp;
    User myUser = ShopFront.myUser;
    Button rock,paper,scissors;
    private CountDownTimer cdt;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 20000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_stage);
        wait = (TextView) findViewById(R.id.waitPlayer);
        rock = (Button) findViewById(R.id.rockBtn);
        paper = (Button) findViewById(R.id.paperBtn);
        scissors = (Button) findViewById(R.id.scissorsBtn);
        TimeRemaining = (TextView) findViewById(R.id.timeFight);
        myhealth = (TextView) findViewById(R.id.myHealth);
        enemyhealth = (TextView) findViewById(R.id.enemyHealth);
        mychoice = (TextView) findViewById(R.id.myChoice);
        opp = (TextView) findViewById(R.id.oppName);


        Intent intent = getIntent();

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comms.getUser1().equals(myUser.getUid())){
                    comms.setUser1choice("Rock");
                }else{
                    comms.setUser2choice("Rock");
                }
                mychoice.setText("Choice: Rock");
                refLobbies.child(comms.getUser1()).setValue(comms);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comms.getUser1().equals(myUser.getUid())){
                    comms.setUser1choice("Paper");
                }else{
                    comms.setUser2choice("Paper");
                }
                mychoice.setText("Choice: Paper");
                refLobbies.child(comms.getUser1()).setValue(comms);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comms.getUser1().equals(myUser.getUid())){
                    comms.setUser1choice("Scissors");
                }else{
                    comms.setUser2choice("Scissors");
                }
                mychoice.setText("Choice: Scissors");
                refLobbies.child(comms.getUser1()).setValue(comms);
            }
        });







        Query query1 = refLobbies;
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    comms = userSnapshot.getValue(Comms.class);
                    if(comms.getUser1().equals(intent.getStringExtra("id"))||comms.getUser2().equals(intent.getStringExtra("id"))){
                        if(!comms.getUser1().isEmpty() &&!comms.getUser2().isEmpty()){
                            wait.setVisibility(View.INVISIBLE);
                            myhealth.setVisibility(View.VISIBLE);
                            mychoice.setVisibility(View.VISIBLE);
                            enemyhealth.setVisibility(View.VISIBLE);
                            TimeRemaining.setVisibility(View.VISIBLE);
                            rock.setVisibility(View.VISIBLE);
                            paper.setVisibility(View.VISIBLE);
                            scissors.setVisibility(View.VISIBLE);
                            opp.setVisibility(View.VISIBLE);

                            if (comms.getWinner().equals("user1")){
                                Toast.makeText(FightingStage.this, comms.getUser1name()+" Won!", Toast.LENGTH_SHORT).show();
                            }

                            if (comms.getWinner().equals("user2")){
                                Toast.makeText(FightingStage.this, comms.getUser2name()+" Won!", Toast.LENGTH_SHORT).show();
                            }

                            if (comms.getWinner().equals("noone")){
                                Toast.makeText(FightingStage.this, "Round Tied!", Toast.LENGTH_SHORT).show();
                            }



                            if(!mTimerRunning){
                                resetTimer();
                                startTimer();
                            }

                            if(myUser.getUid().equals(comms.getUser1())){
                                myhealth.setText("My Health: "+comms.getUser1hp());
                                enemyhealth.setText("Enemy's Health: "+comms.getUser2hp());
                                opp.setText("Opponent's Name: "+comms.getUser2name());
                            }else{
                                myhealth.setText("My Health: "+comms.getUser2hp());
                                enemyhealth.setText("Enemy's Health: "+comms.getUser1hp());
                                opp.setText("Opponent's Name: "+comms.getUser1name());
                            }

                            // when a win occurs

                            if(comms.getUser1hp()==0){
                                pauseTimer();
                                Intent intent2 = new Intent(getApplicationContext(), UndergroundTown.class);
                                if(myUser.getUid().equals(comms.getUser1())){
                                    myUser.setMoney(myUser.getMoney()-comms.getWager());
                                    intent2.putExtra("status","loser");
                                    intent2.putExtra("amount",comms.getWager());
                                }else{
                                    myUser.setMoney(myUser.getMoney()+comms.getWager());
                                    intent2.putExtra("status","winner");
                                    intent2.putExtra("amount",comms.getWager());
                                }
                                refLobbies.child(comms.getUser1()).removeValue();
                                refUsers.child(myUser.getUid()).setValue(myUser);
                                startActivity(intent2);
                                finish();
                            }

                            if(comms.getUser2hp()==0){
                                pauseTimer();
                                Intent intent2 = new Intent(getApplicationContext(), UndergroundTown.class);
                                if(myUser.getUid().equals(comms.getUser1())){
                                    myUser.setMoney(myUser.getMoney()+comms.getWager());
                                    intent2.putExtra("status","winner");
                                    intent2.putExtra("amount",comms.getWager());
                                }else{
                                    myUser.setMoney(myUser.getMoney()-comms.getWager());
                                    intent2.putExtra("status","loser");
                                    intent2.putExtra("amount",comms.getWager());
                                }
                                refLobbies.child(comms.getUser1()).removeValue();
                                refUsers.child(myUser.getUid()).setValue(myUser);
                                startActivity(intent2);
                                finish();
                            }






                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void startTimer(){

        cdt = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCdtt();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                if (!myUser.getUid().equals(comms.getUser1())) {
                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Rock")) {
                        comms.setWinner("noone");
                    }

                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Paper")) {
                        comms.setUser1hp(comms.getUser1hp() - 1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Scissors")) {
                        comms.setUser2hp(comms.getUser2hp() - 1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Rock")) {
                        comms.setUser2hp(comms.getUser2hp() - 1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Paper")) {
                        comms.setWinner("noone");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Scissors")) {
                        comms.setUser1hp(comms.getUser1hp() - 1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Rock")) {
                        comms.setUser1hp(comms.getUser1hp() - 1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Paper")) {
                        comms.setUser2hp(comms.getUser2hp() - 1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Scissors")) {
                        comms.setWinner("noone");
                    }

                    refLobbies.child(comms.getUser1()).setValue(comms);
                }
            }

        }.start();

        mTimerRunning = true;

    }
    private void pauseTimer(){
        cdt.cancel();
        mTimerRunning = false;

    }
    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCdtt();

    }
    private void updateCdtt(){
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        TimeRemaining.setText(timeLeft);

    }









}