package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.Misc.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.potemporiumbeta1.Receivers.AlarmReceiver;
import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.*;

public class ShopFront extends AppCompatActivity {
    Button leave, SellPotion;
    TextView yourGold, InstructionsMoney, DemandType, DemandInsturct, PotionAmount, Reputation;
    HashMap<String, Integer> PotionsH = new HashMap<String, Integer>();
    HashMap<String, Integer> IngredientsH = new HashMap<String, Integer>();
    HashMap<String, Integer> KeypiecesH = new HashMap<String, Integer>();

    ImageView potionImage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static User myUser;
    public static ArrayList<Pair> potValues = new ArrayList<Pair>();
    public static ArrayList<Pair> ingreValues = new ArrayList<Pair>();
    public static ArrayList<Pair> keypValues = new ArrayList<Pair>();
    public static ArrayList<User> userList = new ArrayList<User>();
    final private String myScreen = "ShopFront";
    Spinner screenchanger, PotionSpinner;
    Boolean firstRead = true;
    boolean keychange, ingrechange, potchange = false;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private int ALARM_RQST_CODE = 1;
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();


    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }






    @SuppressLint("ScheduleExactAlarm")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_front);
        leave = (Button) findViewById(R.id.gobackBtn);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_ShopFront);
        mAuth = FirebaseAuth.getInstance();
        PotionSpinner = (Spinner) findViewById(R.id.PotionSpinner);
        InstructionsMoney = (TextView) findViewById(R.id.InstructionShopFront);
        DemandType = (TextView) findViewById(R.id.CurrentBonusType);
        DemandInsturct = (TextView) findViewById(R.id.CurrentBonusTv);
        yourGold = (TextView) findViewById(R.id.goldTvShopFront);
        SellPotion = (Button) findViewById(R.id.TurnInPotion);
        potionImage = (ImageView) findViewById(R.id.CurrentBonusImage);
        PotionAmount = (TextView) findViewById(R.id.PotionAmount);
        Reputation = (TextView) findViewById(R.id.reputationTvShopFront);

        Intent intentget = getIntent();
        boolean getextra = intentget.getBooleanExtra("getgold",false);
        if (getextra){
            myUser.setMoney(myUser.getMoney()+200);
            refUsers.child(myUser.getUid()).setValue(myUser);
            Toast.makeText(this, "You have received 200 gold!", Toast.LENGTH_SHORT).show();
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(getIntent().getIntExtra("id",0));
        }


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        }





        ALARM_RQST_CODE++;
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_RQST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.setTimeInMillis(System.currentTimeMillis());
        calSet.set(Calendar.HOUR_OF_DAY, 16);
        calSet.set(Calendar.MINUTE, 30);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }
// Set inexact repeating & interval to AlarmManager.INTERVAL_DAY
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);






        ArrayList<String> PotionValues = new ArrayList<String>();
        ArrayList<String> PotValuesRandom = new ArrayList<String>();



        SharedPreferences settings = getSharedPreferences(mAuth.getCurrentUser().getUid(), MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Boolean isfirsttime = settings.getBoolean("first_time_potiondemand", true);
        editor.putBoolean("first_time_potiondemand", false);
        editor.commit();


        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);


        Query query = refUsers;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User helper = userSnapshot.getValue(User.class);
                    userList.add(helper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        Query query1 = refUsers.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getValue(User.class).getUid().equals(mAuth.getCurrentUser().getUid())) {
                        myUser = userSnapshot.getValue(User.class);

                        if (firstRead) {

                            refPotionsTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    DataSnapshot dS = task.getResult();
                                    potValues.clear();
                                    for (DataSnapshot userSnapshot : dS.getChildren()) {
                                        Pair temporary = userSnapshot.getValue(Pair.class);
                                        potValues.add(temporary);
                                    }


                                    if (myUser.getPotions().size() != potValues.size()) {
                                        potchange = true;
                                        for (int i = 0; i < potValues.size(); i++) {
                                            if (myUser.getPotions().containsKey(potValues.get(i).getKey())) {
                                                potValues.set(i, new Pair(potValues.get(i).getKey(), myUser.getPotions().get(potValues.get(i).getKey())));

                                            }
                                        }
                                        for (int i = 0; i < potValues.size(); i++) {
                                            PotionsH.put(potValues.get(i).getKey(), potValues.get(i).getAmount());
                                        }
                                        myUser.setPotions(PotionsH);
                                        refUsers.child(myUser.getUid()).setValue(myUser);
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


                                    if (myUser.getIngredients().size() != ingreValues.size()) {
                                        ingrechange = true;
                                        for (int i = 0; i < ingreValues.size(); i++) {
                                            if (myUser.getIngredients().containsKey(ingreValues.get(i).getKey())) {
                                                ingreValues.set(i, new Pair(ingreValues.get(i).getKey(), myUser.getIngredients().get(ingreValues.get(i).getKey())));
                                            }
                                        }

                                        for (int j = 0; j < ingreValues.size(); j++) {
                                            IngredientsH.put(ingreValues.get(j).getKey(), ingreValues.get(j).getAmount());
                                        }
                                        myUser.setIngredients(IngredientsH);
                                        refUsers.child(myUser.getUid()).setValue(myUser);
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


                                    if (myUser.getKeypieces().size() != keypValues.size()) {
                                        keychange = true;
                                        for (int i = 0; i < keypValues.size(); i++) {
                                            if (myUser.getKeypieces().containsKey(keypValues.get(i).getKey())) {
                                                keypValues.set(i, new Pair(keypValues.get(i).getKey(), myUser.getKeypieces().get(keypValues.get(i).getKey())));
                                            }

                                        }
                                        for (int k = 0; k < keypValues.size(); k++) {
                                            KeypiecesH.put(keypValues.get(k).getKey(), keypValues.get(k).getAmount());
                                        }
                                        myUser.setKeypieces(KeypiecesH);
                                        refUsers.child(myUser.getUid()).setValue(myUser);
                                    }
                                }

                            });


                            PotionValues.add("Choose Potion");
                            Set<String> keySetPotions = myUser.getPotions().keySet();
                            ArrayList<String> listOfKeysPotions = new ArrayList<String>(keySetPotions);
                            int lengthCheckPotions = listOfKeysPotions.size() - 1;
                            while (!listOfKeysPotions.isEmpty()) {
                                PotionValues.add(listOfKeysPotions.get(lengthCheckPotions));
                                PotValuesRandom.add(listOfKeysPotions.remove(lengthCheckPotions));
                                lengthCheckPotions--;
                            }

                            ArrayAdapter<String> arrayAdapterPotions = new ArrayAdapter<String>(ShopFront.this, android.R.layout.simple_spinner_item, PotionValues);
                            arrayAdapterPotions.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            PotionSpinner.setAdapter(arrayAdapterPotions);


                            if (isfirsttime) {
                                Random random = new Random();
                                int randomnum = random.nextInt(PotValuesRandom.size());
                                DemandType.setText(PotValuesRandom.get(randomnum));
                                editor.putString("potion_type", PotValuesRandom.get(randomnum));
                                editor.commit();
                            } else {
                                DemandType.setText(settings.getString("potion_type", ""));
                            }
                        }
                        yourGold.setText("Gold: " + String.valueOf(myUser.getMoney()));
                        Reputation.setText("Reputation: " + String.valueOf(myUser.getReputation() / 10) + "." + String.valueOf(myUser.getReputation() % 10));

                        leave.setVisibility(View.VISIBLE);
                        PotionSpinner.setVisibility(View.VISIBLE);
                        InstructionsMoney.setVisibility(View.VISIBLE);
                        DemandType.setVisibility(View.VISIBLE);
                        DemandInsturct.setVisibility(View.VISIBLE);
                        yourGold.setVisibility(View.VISIBLE);
                        SellPotion.setVisibility(View.VISIBLE);
                        potionImage.setVisibility(View.VISIBLE);
                        PotionAmount.setVisibility(View.VISIBLE);
                        Reputation.setVisibility(View.VISIBLE);


                        firstRead = false;

                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SellPotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int plus = 0;
                String spinnerName = PotionSpinner.getSelectedItem().toString();
                if (spinnerName != "Choose Potion") {
                    if (myUser.getPotions().get(spinnerName) > 0) {
                        if (spinnerName.equals(DemandType.getText().toString())) {
                            plus = plus + 25;
                            Random random = new Random();
                            int randomnum = random.nextInt(PotValuesRandom.size());
                            DemandType.setText(PotValuesRandom.get(randomnum));
                            editor.putString("potion_type", PotValuesRandom.get(randomnum));
                            editor.commit();
                        }
                        plus = plus + 25;
                        int multiplier = (int) Math.floor(myUser.getReputation());
                        multiplier = multiplier / 10;
                        myUser.setMoney(myUser.getMoney() + plus * multiplier);
                        myUser.setReputation(myUser.getReputation() + 1);
                        Reputation.setText("Reputation: " + String.valueOf(myUser.getReputation() / 10) + "." + String.valueOf(myUser.getReputation() % 10));
                        int potionamount = myUser.getPotions().get(spinnerName);
                        HashMap<String, Integer> copymap = new HashMap<String, Integer>();
                        copymap = myUser.getPotions();
                        copymap.replace(spinnerName, potionamount - 1);
                        myUser.setPotions(copymap);
                        myUser.setPotionssold(myUser.getPotionssold()+1);
                        refUsers.child(myUser.getUid()).setValue(myUser);
                        potionamount = potionamount - 1;
                        PotionAmount.setText("You have " + potionamount + " of this potion");
                        Toast.makeText(ShopFront.this, "You have sold " + spinnerName + " for " + plus * multiplier + " gold", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShopFront.this, "You dont have any of this potion", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShopFront.this, "Choose a potion", Toast.LENGTH_SHORT).show();
                }
            }
        });


        PotionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (PotionSpinner.getSelectedItem().toString() != "Choose Potion") {
                    int amount = myUser.getPotions().get(PotionSpinner.getSelectedItem().toString());
                    PotionAmount.setText("You have " + amount + " of this potion");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        screenchanger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals(myScreen)) {
                    switch (item){
                        case "ShopFront": Intent intent1 = new Intent(getApplicationContext(), ShopFront.class);
                            startActivity(intent1);
                            finish();
                            break;
                        case "Inventory": Intent intent2 = new Intent(getApplicationContext(), Inventory.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case "Trade Center": Intent intent3 = new Intent(getApplicationContext(), TradeCenter.class);
                            startActivity(intent3);
                            finish();
                            break;
                        case "Brewery": Intent intent4 = new Intent(getApplicationContext(), Brewery.class);
                            startActivity(intent4);
                            finish();
                            break;
                        case "Underground Town":
                            if (ShopFront.myUser.isFightUnlocked()){
                                Intent intent5 = new Intent(getApplicationContext(), UndergroundTown.class);
                                startActivity(intent5);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "You must first unlock the basement", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case "Basement": Intent intent6 = new Intent(getApplicationContext(), Basement.class);
                            startActivity(intent6);
                            finish();
                            break;
                        case "Leaderboards": Intent intent7 = new Intent(getApplicationContext(), LeaderBoards.class);
                            startActivity(intent7);
                            finish();
                            break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Select Screen");
        arrayList.add("ShopFront");
        arrayList.add("Inventory");
        arrayList.add("Trade Center");
        arrayList.add("Brewery");
        arrayList.add("Underground Town");
        arrayList.add("Basement");
        arrayList.add("Leaderboards");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        screenchanger.setAdapter(arrayAdapter);



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