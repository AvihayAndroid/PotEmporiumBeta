package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Button leave,SellPotion;
    TextView yourGold,InstructionsMoney,DemandType,DemandInsturct;
    ImageView potionImage;
    FirebaseAuth mAuth;
    User myUser;
    final private String myScreen = "ShopFront";
    Spinner screenchanger,PotionSpinner;
    Boolean firstRead = true;

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

        ArrayList<String> PotValues = new ArrayList<String>();
        ArrayList<String> PotValuesRandom = new ArrayList<String>();


        SharedPreferences settings = getSharedPreferences(mAuth.getCurrentUser().getUid(),MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Boolean isfirsttime = settings.getBoolean("first_time_potiondemand",true);
        editor.putBoolean("first_time_potiondemand",false);
        editor.commit();






        Query query1 = refUsers.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if(userSnapshot.getValue(User.class).getUid().equals(mAuth.getCurrentUser().getUid())) {
                        myUser = userSnapshot.getValue(User.class);

                        if(firstRead){
                            PotValues.add("Choose Potion");
                            Set<String> keySetPotions = myUser.getPotions().keySet();
                            ArrayList<String> listOfKeysPotions = new ArrayList<String>(keySetPotions);
                            int lengthCheckPotions = listOfKeysPotions.size()-1;
                            while(!listOfKeysPotions.isEmpty()){
                                PotValues.add(listOfKeysPotions.get(lengthCheckPotions));
                                PotValuesRandom.add(listOfKeysPotions.remove(lengthCheckPotions));
                                lengthCheckPotions--;
                            }

                            ArrayAdapter<String> arrayAdapterPotions = new ArrayAdapter<String>(ShopFront.this, android.R.layout.simple_spinner_item, PotValues);
                            arrayAdapterPotions.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            PotionSpinner.setAdapter(arrayAdapterPotions);



                            if (isfirsttime){
                                Random random = new Random();
                                int randomnum = random.nextInt(PotValuesRandom.size());
                                DemandType.setText(PotValuesRandom.get(randomnum));
                                editor.putString("potion_type",PotValuesRandom.get(randomnum));
                                editor.commit();
                            }else {
                                DemandType.setText(settings.getString("potion_type",""));
                            }
                        }
                        yourGold.setText("Gold: "+String.valueOf(myUser.getMoney()));

                        leave.setVisibility(View.VISIBLE);
                        PotionSpinner.setVisibility(View.VISIBLE);
                        InstructionsMoney.setVisibility(View.VISIBLE);
                        DemandType.setVisibility(View.VISIBLE);
                        DemandInsturct.setVisibility(View.VISIBLE);
                        yourGold.setVisibility(View.VISIBLE);
                        SellPotion.setVisibility(View.VISIBLE);
                        potionImage.setVisibility(View.VISIBLE);








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
                String spinnerName = PotionSpinner.getSelectedItem().toString();
                if (spinnerName != "Choose Potion") {
                    if (myUser.getPotions().get(spinnerName) > 0) {
                        if (spinnerName.equals(DemandType.getText().toString())) {
                            myUser.setMoney(myUser.getMoney() + 25);
                            Random random = new Random();
                            int randomnum = random.nextInt(PotValuesRandom.size());
                            DemandType.setText(PotValuesRandom.get(randomnum));
                            editor.putString("potion_type", PotValuesRandom.get(randomnum));
                            editor.commit();
                        }
                        myUser.setMoney(myUser.getMoney() + 25);
                        int potionamount = myUser.getPotions().get(spinnerName);
                        HashMap<String, Integer> copymap = new HashMap<String, Integer>();
                        copymap = myUser.getPotions();
                        copymap.replace(spinnerName, potionamount - 1);
                        myUser.setPotions(copymap);
                        refUsers.child(myUser.getUid()).setValue(myUser);
                    } else {
                        Toast.makeText(ShopFront.this, "You dont have any of this potion", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ShopFront.this, "Choose a potion", Toast.LENGTH_SHORT).show();
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
//                    case "Arena": Intent intent5 = new Intent(getApplicationContext(), Arena.class);
//                        startActivity(intent5);
//                        finish();
//                        break;
//                    case "Basement": Intent intent6 = new Intent(getApplicationContext(), Basement.class);
//                        startActivity(intent6);
//                        finish();
//                        break;
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
        arrayList.add("Arena");
        arrayList.add("Basement");
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