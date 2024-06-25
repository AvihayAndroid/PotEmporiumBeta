package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.Misc.FBRef.refUsers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Basement extends AppCompatActivity {
    final private String myScreen = "Basement";
    User myUser = ShopFront.myUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Spinner screenchanger;
    Button unlock;
    TextView unlockedText,havekeys,donthavekeys;
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();


    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basement);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Basement);
        unlock = (Button) findViewById(R.id.unlockBasement);
        unlockedText = (TextView) findViewById(R.id.unlockedText);
        havekeys = (TextView) findViewById(R.id.keypieceHave);
        donthavekeys = (TextView) findViewById(R.id.keypiecenotHave);

        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        }

        // making an array list of pairs for the keys.

        ArrayList<Pair> keypiecesAl=new ArrayList<Pair>();
        Set<String> keySet3 = myUser.getKeypieces().keySet();
        ArrayList<String> listOfKeys3 = new ArrayList<String>(keySet3);
        Collection<Integer> values3 = myUser.getKeypieces().values();
        ArrayList<Integer> listOfValues3 = new ArrayList<>(values3);
        int lengthCheck3 = listOfValues3.size()-1;
        while(!listOfValues3.isEmpty()){
            keypiecesAl.add(new Pair(listOfKeys3.remove(lengthCheck3),listOfValues3.remove(lengthCheck3)));
            lengthCheck3--;
        }
        int count=0;

        // sorting by what i have and what i dont.

        for (int i=0;i<keypiecesAl.size();i++){
            if(keypiecesAl.get(i).getAmount()==1){
                havekeys.append(keypiecesAl.get(i).getKey());
                havekeys.append("\n");
                count++;
            }else{
                donthavekeys.append(keypiecesAl.get(i).getKey());
                donthavekeys.append("\n");
            }
        }
        if (myUser.isFightUnlocked()){
            unlockedText.setVisibility(View.VISIBLE);
        }

        int finalCount = count;

        // unlocking the basement;
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser.isFightUnlocked()){
                    Toast.makeText(Basement.this, "Already unlocked", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(finalCount == keypiecesAl.size()){
                    myUser.setFightUnlocked(true);
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    Toast.makeText(Basement.this, "Successfully unlocked!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Basement.this, "You have not acquired all of the keys yet", Toast.LENGTH_LONG).show();
                }
            }
        });



        // screen swapper

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