package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.Misc.FBRef.refLobbies;
import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Objects.Comms;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.Locale;

public class FightingStage extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Comms comms = new Comms();
    TextView wait,TimeRemaining,myhealth,enemyhealth,mychoice,opp,whoturn;
    User myUser = ShopFront.myUser;
    Button rock,paper,scissors,leave,resign,attack,enhance,heal;
    private Query query1;
    private ValueEventListener listener;
    private CountDownTimer cdt;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 15000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    int multiplier = 1;
    boolean isturn = false;
    boolean firstime = true;

    @Override
    public void onDestroy(){
        super.onDestroy();
        query1.removeEventListener(listener);
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighting_stage);
        wait = (TextView) findViewById(R.id.waitPlayer);
        rock = (Button) findViewById(R.id.rockBtn);
        paper = (Button) findViewById(R.id.paperBtn);
        scissors = (Button) findViewById(R.id.scissorsBtn);
        TimeRemaining = (TextView) findViewById(R.id.timeFight);
        myhealth = (TextView) findViewById(R.id.myHealth);
        enemyhealth = (TextView) findViewById(R.id.enemyHealth);
        mychoice = (TextView) findViewById(R.id.myChoice);
        opp = (TextView) findViewById(R.id.oppName);
        leave = (Button) findViewById(R.id.leaveLobby);
        resign = (Button) findViewById(R.id.resign);
        attack = (Button) findViewById(R.id.attackBtn);
        heal = (Button) findViewById(R.id.healBtn);
        enhance = (Button) findViewById(R.id.enhanceBtn);
        whoturn = (TextView) findViewById(R.id.enemyturn);

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);



        Intent intent = getIntent();

        resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser.getUid().equals(comms.getUser1())){
                    comms.setUser1hp(0);
                }else{
                    comms.setUser2hp(0);
                }
                refLobbies.child(comms.getUser1()).setValue(comms);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refLobbies.child(comms.getUser1()).removeValue();
                Intent intent3 = new Intent(getApplicationContext(), UndergroundTown.class);
                startActivity(intent3);
                finish();

            }
        });

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comms.getUser1().equals(myUser.getUid())){
                    comms.setUser1choice("Rock");
                }else{
                    comms.setUser2choice("Rock");
                }
                mychoice.setText("Choice: Rock");
                comms.setWinner("");
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
                comms.setWinner("");
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
                comms.setWinner("");
                refLobbies.child(comms.getUser1()).setValue(comms);
            }
        });

        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser.getUid().equals(comms.getUser1())){
                    comms.setUser2hp(comms.getUser2hp()-1*multiplier);
                    comms.setWinner("user2text");
                    comms.setAttack("You got attacked for "+multiplier+" damage!");
                }
                if(myUser.getUid().equals(comms.getUser2())){
                    comms.setUser1hp(comms.getUser1hp()-1*multiplier);
                    comms.setWinner("user1text");
                    comms.setAttack("You got attacked for "+multiplier+" damage!");
                }
                isturn=true;
                refLobbies.child(comms.getUser1()).setValue(comms);
                attack.setVisibility(View.INVISIBLE);
                heal.setVisibility(View.INVISIBLE);
                enhance.setVisibility(View.INVISIBLE);
                mychoice.setVisibility(View.VISIBLE);
                Toast.makeText(FightingStage.this, "You attacked for "+1*multiplier+" damage!", Toast.LENGTH_SHORT).show();
            }
        });
        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myUser.getUid().equals(comms.getUser1())){
                    comms.setUser1hp(comms.getUser1hp()+2);
                    comms.setWinner("user2text");
                    comms.setAttack("Enemy healed for 2 health!");
                }
                if(myUser.getUid().equals(comms.getUser2())){
                    comms.setUser2hp(comms.getUser2hp()+2);
                    comms.setWinner("user1text");
                    comms.setAttack("Enemy healed for 2 health!");
                }
                isturn=true;
                refLobbies.child(comms.getUser1()).setValue(comms);
                attack.setVisibility(View.INVISIBLE);
                heal.setVisibility(View.INVISIBLE);
                enhance.setVisibility(View.INVISIBLE);
                mychoice.setVisibility(View.VISIBLE);
                Toast.makeText(FightingStage.this, "You healed for 2 health!", Toast.LENGTH_SHORT).show();

            }
        });
        enhance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser.getUid().equals(comms.getUser1())){
                    comms.setWinner("user2text");
                }
                if(myUser.getUid().equals(comms.getUser2())){
                    comms.setWinner("user1text");
                }
                multiplier = multiplier*2;
                comms.setAttack("Enemy has risen their damage multiplier to "+multiplier);
                isturn=true;
                comms.setCounter(comms.getCounter()+1);
                refLobbies.child(comms.getUser1()).setValue(comms);
                attack.setVisibility(View.INVISIBLE);
                heal.setVisibility(View.INVISIBLE);
                enhance.setVisibility(View.INVISIBLE);
                mychoice.setVisibility(View.VISIBLE);
                Toast.makeText(FightingStage.this, "You increased your damage multiplier to "+multiplier, Toast.LENGTH_SHORT).show();
            }
        });








        query1 = refLobbies;
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    comms = userSnapshot.getValue(Comms.class);
                    if(comms.getUser1().equals(intent.getStringExtra("id"))||comms.getUser2().equals(intent.getStringExtra("id"))){
                        if(!comms.getUser1().isEmpty() &&!comms.getUser2().isEmpty()){
                            if (firstime){
                                firstime = false;
                                startTimer();
                            }
                            String user1 = comms.getUser1();
                            String user2 = comms.getUser2();
                            wait.setVisibility(View.INVISIBLE);
                            leave.setVisibility(View.INVISIBLE);
                            myhealth.setVisibility(View.VISIBLE);
                            mychoice.setVisibility(View.VISIBLE);
                            enemyhealth.setVisibility(View.VISIBLE);
                            TimeRemaining.setVisibility(View.VISIBLE);
                            rock.setVisibility(View.VISIBLE);
                            paper.setVisibility(View.VISIBLE);
                            scissors.setVisibility(View.VISIBLE);
                            opp.setVisibility(View.VISIBLE);
                            resign.setVisibility(View.VISIBLE);

                            if (myUser.getUid().equals(user1)&&comms.getWinner().equals("user1text")){
                                Toast.makeText(FightingStage.this, comms.getAttack(), Toast.LENGTH_SHORT).show();
                                isturn=true;
                                comms.setAttack("");
                                comms.setWinner("");
                                whoturn.setVisibility(View.INVISIBLE);
                                mychoice.setVisibility(View.VISIBLE);
                            }

                            if (myUser.getUid().equals(user2)&&comms.getWinner().equals("user2text")){
                                Toast.makeText(FightingStage.this, comms.getAttack(), Toast.LENGTH_SHORT).show();
                                isturn=true;
                                comms.setAttack("");
                                comms.setWinner("");
                                whoturn.setVisibility(View.INVISIBLE);
                                mychoice.setVisibility(View.VISIBLE);
                            }

                            if (comms.getWinner().equals("user1")){
                                Toast.makeText(FightingStage.this, comms.getUser1name()+" Won this round!", Toast.LENGTH_SHORT).show();
                                if(myUser.getUid().equals(user1)){
                                    attack.setVisibility(View.VISIBLE);
                                    heal.setVisibility(View.VISIBLE);
                                    enhance.setVisibility(View.VISIBLE);
                                    mychoice.setVisibility(View.INVISIBLE);
                                }else{
                                    whoturn.setVisibility(View.VISIBLE);
                                    mychoice.setVisibility(View.INVISIBLE);
                                }
                                rock.setVisibility(View.INVISIBLE);
                                paper.setVisibility(View.INVISIBLE);
                                scissors.setVisibility(View.INVISIBLE);
                                TimeRemaining.setVisibility(View.INVISIBLE);
                                comms.setWinner("");
                            }

                            if (comms.getWinner().equals("user2")){
                                Toast.makeText(FightingStage.this, comms.getUser2name()+" Won this round!", Toast.LENGTH_SHORT).show();
                                if (myUser.getUid().equals(user2)){
                                    attack.setVisibility(View.VISIBLE);
                                    heal.setVisibility(View.VISIBLE);
                                    enhance.setVisibility(View.VISIBLE);
                                    mychoice.setVisibility(View.INVISIBLE);
                                }else{
                                    whoturn.setVisibility(View.VISIBLE);
                                    mychoice.setVisibility(View.INVISIBLE);
                                }
                                rock.setVisibility(View.INVISIBLE);
                                paper.setVisibility(View.INVISIBLE);
                                scissors.setVisibility(View.INVISIBLE);
                                TimeRemaining.setVisibility(View.INVISIBLE);
                                comms.setWinner("");
                            }

                            if (comms.getWinner().equals("noone")){
                                Toast.makeText(FightingStage.this, "Round Tied!", Toast.LENGTH_SHORT).show();
                                resetTimer();
                                startTimer();
                            }

                            if (comms.getWinner().equals("nochoose")){
                                Toast.makeText(FightingStage.this, "No one selected an item", Toast.LENGTH_SHORT).show();
                                resetTimer();
                                startTimer();
                            }



                            if(isturn){
                                resetTimer();
                                startTimer();
                                isturn=false;
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

                            if(comms.getUser1hp()<=0){
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

                            if(comms.getUser2hp()<=0){
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
        };
        query1.addValueEventListener(listener);


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

                if(comms.getUser1choice().equals("") && comms.getUser2choice().equals("")){
                    comms.setWinner("nochoose");
                    comms.setCounter(comms.getCounter()+1);
                    refLobbies.child(comms.getUser1()).setValue(comms);
                }

                if(comms.getUser2choice().equals("")&&!comms.getUser1choice().isEmpty()){
                    comms.setUser2hp(0);
                    comms.setWinner("");
                    refLobbies.child(comms.getUser1()).setValue(comms);
                }

                if(comms.getUser1choice().equals("")&&!comms.getUser2choice().isEmpty()){
                    comms.setUser1hp(0);
                    comms.setWinner("");
                    refLobbies.child(comms.getUser1()).setValue(comms);
                }




                if (!myUser.getUid().equals(comms.getUser1())&&!comms.getUser2choice().isEmpty()&&!comms.getUser1choice().isEmpty()) {
                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Rock")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("noone");
                    }

                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Paper")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Rock") && comms.getUser2choice().equals("Scissors")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Rock")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Paper")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("noone");
                    }

                    if (comms.getUser1choice().equals("Paper") && comms.getUser2choice().equals("Scissors")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Rock")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user2");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Paper")) {
                        comms.setCounter(comms.getCounter()+1);
                        comms.setWinner("user1");
                    }

                    if (comms.getUser1choice().equals("Scissors") && comms.getUser2choice().equals("Scissors")) {
                        comms.setCounter(comms.getCounter()+1);
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