package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.Misc.FBRef.refLobbies;
import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLobbies;
import com.example.potemporiumbeta1.Objects.Comms;
import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UndergroundTown extends AppCompatActivity {
    final private String myScreen = "Underground Town";

    Spinner screenchanger;
    ListView Lobbies;
    Button createLobby,setBattlemessage;
    EditText battlemessageEt,wager;
    User myUser = ShopFront.myUser;
    ArrayList<User> userList = ShopFront.userList;
    Comms example;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Comms> array = new ArrayList<Comms>();
    ArrayList<User> userarray = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_underground_town);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Arena);
        Lobbies = (ListView) findViewById(R.id.LobbiesLw);
        createLobby = (Button) findViewById(R.id.createLobby);
        setBattlemessage = (Button) findViewById(R.id.setBattlemessage);
        battlemessageEt = (EditText) findViewById(R.id.battlemessageEt);
        wager = (EditText) findViewById(R.id.wagerEt);

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);


        try {
            Intent intent = getIntent();
            if(intent.getStringExtra("status").equals("winner")){
                Toast.makeText(this, "You have won the match and won "+intent.getIntExtra("amount",0)+" gold!", Toast.LENGTH_SHORT).show();
            }
            if(intent.getStringExtra("status").equals("loser")){
                Toast.makeText(this, "You have lost the match and lost "+intent.getIntExtra("amount",0)+" gold!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
        }



        if(!myUser.getBattleMessage().isEmpty()){
            battlemessageEt.setText(myUser.getBattleMessage());
        }
        if(myUser.getGoldWager()!=0){
            wager.setText(String.valueOf(myUser.getGoldWager()));
        }

        Query query1 = refLobbies;
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                array.clear();
                userarray.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    example = userSnapshot.getValue(Comms.class);
                    if(example.isIsopen()) {
                        array.add(example);
                    }
                }
                for (int i=0;i<userList.size();i++){
                    for(int j=0;j<array.size();j++){
                        if(userList.get(i).getUid().equals(array.get(j).getUser1())){
                            userarray.add(userList.get(i));
                        }
                    }
                }
                CustomBaseAdapterForLobbies customBaseAdapter = new CustomBaseAdapterForLobbies(getApplicationContext(),userarray);
                Lobbies.setAdapter(customBaseAdapter);
                Lobbies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (myUser.getMoney()>=userarray.get(position).getGoldWager()) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(UndergroundTown.this);
                            builder.setTitle("Join Lobby?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(), FightingStage.class);
                                            intent.putExtra("id", userarray.get(position).getUid());
                                            for (int i = 0; i < array.size(); i++) {
                                                if (array.get(i).getUser1().equals(userarray.get(position).getUid())) {
                                                    array.get(i).setUser2(myUser.getUid());
                                                    array.get(i).setIsopen(false);
                                                    array.get(i).setUser2name(myUser.getUsername());
                                                    refLobbies.child(array.get(i).getUser1()).setValue(array.get(i));
                                                }
                                            }
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }else{
                            Toast.makeText(UndergroundTown.this, "Not enough gold to participate", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wager.getText().toString().isEmpty()) {
                    myUser.setGoldWager(Integer.parseInt(wager.getText().toString()));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    if (wager.getText().toString().isEmpty() || myUser.getGoldWager() == 0 || myUser.getMoney() < Integer.parseInt(wager.getText().toString())) {
                        Toast.makeText(UndergroundTown.this, "Must input a wager that is higher than 0 and within your gold limit", Toast.LENGTH_SHORT).show();
                    } else {
                        Comms comms = new Comms(myUser.getUid(), Integer.parseInt(wager.getText().toString()),myUser.getUsername());
                        if (myUser.getBattleMessage().isEmpty()) {
                            Toast.makeText(UndergroundTown.this, "You must enter a message before creating a lobby", Toast.LENGTH_SHORT).show();
                        } else {
                            refLobbies.child(myUser.getUid()).setValue(comms);
                            Intent intent = new Intent(getApplicationContext(), FightingStage.class);
                            intent.putExtra("id",myUser.getUid());
                            startActivity(intent);
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(UndergroundTown.this, "Must input a wager that is higher than 0 and within your gold limit", Toast.LENGTH_SHORT).show();
                }

            }
        });




        setBattlemessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = battlemessageEt.getText().toString();
                if(!str.equals("")) {
                    myUser.setBattleMessage(str);
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    Toast.makeText(UndergroundTown.this, "Message successfully set!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UndergroundTown.this, "Must not input a blank message", Toast.LENGTH_SHORT).show();
                }
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
                        case "Underground Town": Intent intent5 = new Intent(getApplicationContext(), UndergroundTown.class);
                            startActivity(intent5);
                            finish();
                            break;
                        case "Basement": Intent intent6 = new Intent(getApplicationContext(), Basement.class);
                            startActivity(intent6);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        screenchanger.setAdapter(arrayAdapter);
    }
}