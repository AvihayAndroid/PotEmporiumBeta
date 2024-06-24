package com.example.potemporiumbeta1.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLbGold;
import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLbPots;
import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLobbies;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;

import java.util.ArrayList;

public class LeaderBoards extends AppCompatActivity {
    final private String myScreen = "Leaderboards";

    ListView gold,potions;
    Spinner screenchanger;

    ArrayList<User> userlist = ShopFront.userList;
    ArrayList<Integer> place = new ArrayList<Integer>();
    ArrayList<User> goldlisthelp = (ArrayList<User>) userlist.clone();
    ArrayList<User> potionslisthelp = (ArrayList<User>) userlist.clone();
    ArrayList<User> goldlist = new ArrayList<User>();
    ArrayList<User> potionlist = new ArrayList<User>();
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        gold = (ListView) findViewById(R.id.lbUsergold);
        potions = (ListView) findViewById(R.id.lbUserpot);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_LeaderBoards);

        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);



        for (int i = 0;i<userlist.size();i++){
            place.add(i+1);
        }


        for(int j = 0;j<userlist.size();j++) {
            User temp = goldlisthelp.get(0);
            for (int i = 0; i < goldlisthelp.size(); i++) {
                if (temp.getMoney() < goldlisthelp.get(i).getMoney())
                    temp = goldlisthelp.get(i);
            }
            goldlist.add(temp);
            goldlisthelp.remove(temp);
        }

        for(int j = 0;j<userlist.size();j++) {
            User temp = potionslisthelp.get(0);
            for (int i = 0; i < potionslisthelp.size(); i++) {
                if (temp.getPotionssold() < potionslisthelp.get(i).getPotionssold())
                    temp = potionslisthelp.get(i);
            }
            potionlist.add(temp);
            potionslisthelp.remove(temp);
        }





        CustomBaseAdapterForLbPots customBaseAdapterForLbPots = new CustomBaseAdapterForLbPots(getApplicationContext(),potionlist,place);
        CustomBaseAdapterForLbGold customBaseAdapterForLbGold = new CustomBaseAdapterForLbGold(getApplicationContext(),goldlist,place);
        gold.setAdapter(customBaseAdapterForLbGold);
        potions.setAdapter(customBaseAdapterForLbPots);


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



    }
}