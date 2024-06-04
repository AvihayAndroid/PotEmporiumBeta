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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForItems;
import com.example.potemporiumbeta1.Receivers.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


public class Inventory extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ListView potionsLw,ingredientsLw,keypiecesLw;
    TextView goldTv;
    User helper = ShopFront.myUser;
    final private String myScreen = "Inventory";
    Spinner screenchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mAuth = FirebaseAuth.getInstance();
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Inventory);
        goldTv = (TextView) findViewById(R.id.goldTv);

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        }


        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);

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
                                Toast.makeText(Inventory.this, "You must first unlock the basement", Toast.LENGTH_SHORT).show();
                            }

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






                        ArrayList<Pair> potionsAl=new ArrayList<Pair>();
                        ArrayList<Pair> ingredientsAl=new ArrayList<Pair>();
                        ArrayList<Pair> keypiecesAl=new ArrayList<Pair>();




                        Set<String> keySet = helper.getPotions().keySet();
                        ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
                        Collection<Integer> values = helper.getPotions().values();
                        ArrayList<Integer> listOfValues = new ArrayList<>(values);
                        int lengthCheck = listOfValues.size()-1;
                        while(!listOfValues.isEmpty()){
                            potionsAl.add(new Pair(listOfKeys.remove(lengthCheck),listOfValues.remove(lengthCheck)));
                            lengthCheck--;
                        }
                        potionsLw = (ListView) findViewById(R.id.potionsLw);
                        CustomBaseAdapterForItems customBaseAdapter = new CustomBaseAdapterForItems(getApplicationContext(),potionsAl,null);
                        potionsLw.setAdapter(customBaseAdapter);




                        Set<String> keySet2 = helper.getIngredients().keySet();
                        ArrayList<String> listOfKeys2 = new ArrayList<String>(keySet2);
                        Collection<Integer> values2 = helper.getIngredients().values();
                        ArrayList<Integer> listOfValues2 = new ArrayList<>(values2);
                        int lengthCheck2 = listOfValues2.size()-1;
                        while(!listOfValues2.isEmpty()){
                            ingredientsAl.add(new Pair(listOfKeys2.remove(lengthCheck2),listOfValues2.remove(lengthCheck2)));
                            lengthCheck2--;
                        }
                        ingredientsLw = (ListView) findViewById(R.id.ingredientsLw);
                        CustomBaseAdapterForItems customBaseAdapter2 = new CustomBaseAdapterForItems(getApplicationContext(),ingredientsAl,null);
                        ingredientsLw.setAdapter(customBaseAdapter2);




                        Set<String> keySet3 = helper.getKeypieces().keySet();
                        ArrayList<String> listOfKeys3 = new ArrayList<String>(keySet3);
                        Collection<Integer> values3 = helper.getKeypieces().values();
                        ArrayList<Integer> listOfValues3 = new ArrayList<>(values3);
                        int lengthCheck3 = listOfValues3.size()-1;
                        while(!listOfValues3.isEmpty()){
                            keypiecesAl.add(new Pair(listOfKeys3.remove(lengthCheck3),listOfValues3.remove(lengthCheck3)));
                            lengthCheck3--;
                        }
                        keypiecesLw = (ListView) findViewById(R.id.keypiecesLw);
                        CustomBaseAdapterForItems customBaseAdapter3 = new CustomBaseAdapterForItems(getApplicationContext(),keypiecesAl,null);
                        keypiecesLw.setAdapter(customBaseAdapter3);

                        goldTv.setText("Gold: "+ String.valueOf(helper.getMoney()));








    }
}