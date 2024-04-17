package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refUsers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TradeCenter extends AppCompatActivity {
    Spinner screenchanger;
    TextView Instructions1,Instructions2,RefreshShopTv,Ingredient1,Ingredient2,Ingredient3,Ingredient4,Ingredient5,Ingredient1Remaining,Ingredient2Remaining,Ingredient3Remaining,Ingredient4Remaining,Ingredient5Remaining,SpecialSlot,SpecialSlotRemaining,TimeRemaining,YourGold,amounts1,amounts2,amounts3,amounts4,amounts5,amounts6;
    Button Ingredient1Btn,Ingredient2Btn,Ingredient3Btn,Ingredient4Btn,Ingredient5Btn,SpecialSlotBtn,RefreshShopBtn;
    ImageView Ingredient1Image,Ingredient2Image,Ingredient3Image,Ingredient4Image,Ingredient5Image;
    final private String myScreen = "Trade Center";
    FirebaseAuth mAuth;
    User myUser;
    ArrayList<Pair> ingreValues = new ArrayList<Pair>();
    ArrayList<Pair> keyValues = new ArrayList<Pair>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_center);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_TradeCenter);
        mAuth = FirebaseAuth.getInstance();

        amounts1 = (TextView) findViewById(R.id.amount1);
        amounts2 = (TextView) findViewById(R.id.amount2);
        amounts3 = (TextView) findViewById(R.id.amount3);
        amounts4 = (TextView) findViewById(R.id.amount4);
        amounts5 = (TextView) findViewById(R.id.amount5);
        amounts6 = (TextView) findViewById(R.id.amount6);

        Ingredient1 = (TextView) findViewById(R.id.IngredientsSlot1);
        Ingredient1Remaining = (TextView) findViewById(R.id.IngredientsCount1);
        Ingredient1Image = (ImageView) findViewById(R.id.IngredientImage1);
        Ingredient1Btn = (Button)  findViewById(R.id.IngredientButton1);

        Ingredient2 = (TextView) findViewById(R.id.IngredientsSlot2);
        Ingredient2Remaining = (TextView) findViewById(R.id.IngredientsCount2);
        Ingredient2Image = (ImageView) findViewById(R.id.IngredientImage2);
        Ingredient2Btn = (Button)  findViewById(R.id.IngredientButton2);

        Ingredient3 = (TextView) findViewById(R.id.IngredientsSlot3);
        Ingredient3Remaining = (TextView) findViewById(R.id.IngredientsCount3);
        Ingredient3Image = (ImageView) findViewById(R.id.IngredientImage3);
        Ingredient3Btn = (Button)  findViewById(R.id.IngredientButton3);

        Ingredient4 = (TextView) findViewById(R.id.IngredientsSlot4);
        Ingredient4Remaining = (TextView) findViewById(R.id.IngredientsCount4);
        Ingredient4Image = (ImageView) findViewById(R.id.IngredientImage4);
        Ingredient4Btn = (Button)  findViewById(R.id.IngredientButton4);

        Ingredient5 = (TextView) findViewById(R.id.IngredientsSlot5);
        Ingredient5Remaining = (TextView) findViewById(R.id.IngredientsCount5);
        Ingredient5Image = (ImageView) findViewById(R.id.IngredientImage5);
        Ingredient5Btn = (Button)  findViewById(R.id.IngredientButton5);

        SpecialSlot = (TextView) findViewById(R.id.SpecialSlot);
        SpecialSlotRemaining = (TextView) findViewById(R.id.SpeicalSlotRemaining);
        SpecialSlotBtn = (Button) findViewById(R.id.SpecialSlotButton);
        TimeRemaining = (TextView) findViewById(R.id.Remainingtime);
        YourGold = (TextView) findViewById(R.id.Yourgold);
        RefreshShopBtn = (Button) findViewById(R.id.RefreshShop);
        RefreshShopTv = (TextView) findViewById(R.id.RefreshCost);
        Instructions1 = (TextView) findViewById(R.id.Instructions1);
        Instructions2 = (TextView) findViewById(R.id.Instructions2);


        SharedPreferences settings = getSharedPreferences(mAuth.getCurrentUser().getUid(),MODE_PRIVATE);
        SharedPreferences settings2 = getSharedPreferences("SHOP_INGREDIENTS_"+mAuth.getCurrentUser().getUid(),MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        SharedPreferences.Editor editor2 = settings2.edit();
        Boolean isfirsttime = settings.getBoolean("first_time",true);
        editor.putBoolean("first_time",false);
        editor.commit();



if(isfirsttime){
    refUsers.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            ArrayList<String> randomKeys = new ArrayList<String>();
            DataSnapshot dS = task.getResult();
            for (DataSnapshot userSnapshot : dS.getChildren()) {
                User testuser = userSnapshot.getValue(User.class);
                if(testuser.getUid().equals(mAuth.getCurrentUser().getUid())){
                    YourGold.setText(String.valueOf("Gold: "+testuser.getMoney()));
                    myUser=userSnapshot.getValue(User.class);
                }
            }
            Random random = new Random();
            int randomnum = random.nextInt(50);
            if(randomnum==0){
                Set<String> keySet2 = myUser.getKeypieces().keySet();
                ArrayList<String> listOfKeys = new ArrayList<String>(keySet2);
                Collection<Integer> values = myUser.getKeypieces().values();
                ArrayList<Integer> listOfValues = new ArrayList<>(values);
                int lengthCheck = listOfValues.size()-1;
                while(!listOfKeys.isEmpty()){
                    if(listOfValues.get(lengthCheck)==0) {
                        randomKeys.add(listOfKeys.remove(lengthCheck));
                    }else { listOfKeys.remove(lengthCheck); }
                    lengthCheck--;
                }
                if(randomKeys.size()>0){
                    int selectkey = random.nextInt(randomKeys.size());
                    SpecialSlot.setText(randomKeys.get(selectkey));
                    SpecialSlotRemaining.setText("1");
                    editor2.putString("keyslotname",randomKeys.get(selectkey));
                    editor2.putInt("keyslotamount",1);

                    editor2.commit();
                }
            }
        }
    });


    refIngredientsTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            ArrayList<Integer> randomNums = new ArrayList<Integer>();
            DataSnapshot dS = task.getResult();
            for (DataSnapshot userSnapshot : dS.getChildren()) {
                Pair temporary = userSnapshot.getValue(Pair.class);
                ingreValues.add(temporary);
            }
            HashSet hs = new HashSet();
            while (hs.size() < 5) {
                int num = (int) (Math.random() * ingreValues.size());
                hs.add(num);
            }
            Iterator it = hs.iterator();
            while (it.hasNext()) {
                randomNums.add((int) it.next());
            }
            Random random = new Random();
            int randomInteger1 = random.nextInt(15)+5;
            int randomInteger2 = random.nextInt(15)+5;
            int randomInteger3 = random.nextInt(15)+5;
            int randomInteger4 = random.nextInt(15)+5;
            int randomInteger5 = random.nextInt(15)+5;


            Ingredient1.setText(ingreValues.get(randomNums.get(0)).getKey());
            Ingredient2.setText(ingreValues.get(randomNums.get(1)).getKey());
            Ingredient3.setText(ingreValues.get(randomNums.get(2)).getKey());
            Ingredient4.setText(ingreValues.get(randomNums.get(3)).getKey());
            Ingredient5.setText(ingreValues.get(randomNums.get(4)).getKey());
            Ingredient1Remaining.setText(String.valueOf(randomInteger1));
            Ingredient2Remaining.setText(String.valueOf(randomInteger2));
            Ingredient3Remaining.setText(String.valueOf(randomInteger3));
            Ingredient4Remaining.setText(String.valueOf(randomInteger4));
            Ingredient5Remaining.setText(String.valueOf(randomInteger5));
            Ingredient1Image.setImageResource(R.drawable.wheat_plant);
            Ingredient2Image.setImageResource(R.drawable.wheat_plant);
            Ingredient3Image.setImageResource(R.drawable.wheat_plant);
            Ingredient4Image.setImageResource(R.drawable.wheat_plant);
            Ingredient5Image.setImageResource(R.drawable.wheat_plant);

            Ingredient1Btn.setVisibility(View.VISIBLE);
            Ingredient2Btn.setVisibility(View.VISIBLE);
            Ingredient3Btn.setVisibility(View.VISIBLE);
            Ingredient4Btn.setVisibility(View.VISIBLE);
            Ingredient5Btn.setVisibility(View.VISIBLE);
            RefreshShopTv.setVisibility(View.VISIBLE);
            RefreshShopBtn.setVisibility(View.VISIBLE);
            SpecialSlotBtn.setVisibility(View.VISIBLE);
            TimeRemaining.setVisibility(View.VISIBLE);
            Instructions1.setVisibility(View.VISIBLE);
            Instructions2.setVisibility(View.VISIBLE);
            amounts1.setVisibility(View.VISIBLE);
            amounts2.setVisibility(View.VISIBLE);
            amounts3.setVisibility(View.VISIBLE);
            amounts4.setVisibility(View.VISIBLE);
            amounts5.setVisibility(View.VISIBLE);




            editor2.putString("slotname1",ingreValues.get(randomNums.get(0)).getKey());
            editor2.putString("slotname2",ingreValues.get(randomNums.get(1)).getKey());
            editor2.putString("slotname3",ingreValues.get(randomNums.get(2)).getKey());
            editor2.putString("slotname4",ingreValues.get(randomNums.get(3)).getKey());
            editor2.putString("slotname5",ingreValues.get(randomNums.get(4)).getKey());
            editor2.putInt("slotamount1",randomInteger1);
            editor2.putInt("slotamount2",randomInteger2);
            editor2.putInt("slotamount3",randomInteger3);
            editor2.putInt("slotamount4",randomInteger4);
            editor2.putInt("slotamount5",randomInteger5);

            editor2.commit();



        }
    });

}else {
    refUsers.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            ArrayList<String> randomKeys = new ArrayList<String>();
            DataSnapshot dS = task.getResult();
            for (DataSnapshot userSnapshot : dS.getChildren()) {
                User testuser = userSnapshot.getValue(User.class);
                if(testuser.getUid().equals(mAuth.getCurrentUser().getUid())){
                    YourGold.setText(String.valueOf("Gold: "+testuser.getMoney()));
                    myUser=userSnapshot.getValue(User.class);
                }
            }
            String ingredients1 = settings2.getString("slotname1",null);
            String ingredients2 = settings2.getString("slotname2",null);
            String ingredients3 = settings2.getString("slotname3",null);
            String ingredients4 = settings2.getString("slotname4",null);
            String ingredients5 = settings2.getString("slotname5",null);
            int amount1 = settings2.getInt("slotamount1",0);
            int amount2 = settings2.getInt("slotamount2",0);
            int amount3 = settings2.getInt("slotamount3",0);
            int amount4 = settings2.getInt("slotamount4",0);
            int amount5 = settings2.getInt("slotamount5",0);
            String keyname = settings2.getString("keyslotname",null);
            int keyamount = settings2.getInt("keyslotamount",0);

            Ingredient1.setText(ingredients1);
            Ingredient2.setText(ingredients2);
            Ingredient3.setText(ingredients3);
            Ingredient4.setText(ingredients4);
            Ingredient5.setText(ingredients5);
            Ingredient1Remaining.setText(String.valueOf(amount1));
            Ingredient2Remaining.setText(String.valueOf(amount2));
            Ingredient3Remaining.setText(String.valueOf(amount3));
            Ingredient4Remaining.setText(String.valueOf(amount4));
            Ingredient5Remaining.setText(String.valueOf(amount5));
            Ingredient1Image.setImageResource(R.drawable.wheat_plant);
            Ingredient2Image.setImageResource(R.drawable.wheat_plant);
            Ingredient3Image.setImageResource(R.drawable.wheat_plant);
            Ingredient4Image.setImageResource(R.drawable.wheat_plant);
            Ingredient5Image.setImageResource(R.drawable.wheat_plant);
            SpecialSlot.setText(keyname);
            SpecialSlotRemaining.setText(String.valueOf(keyamount));
            Ingredient1Btn.setVisibility(View.VISIBLE);
            Ingredient2Btn.setVisibility(View.VISIBLE);
            Ingredient3Btn.setVisibility(View.VISIBLE);
            Ingredient4Btn.setVisibility(View.VISIBLE);
            Ingredient5Btn.setVisibility(View.VISIBLE);
            RefreshShopTv.setVisibility(View.VISIBLE);
            RefreshShopBtn.setVisibility(View.VISIBLE);
            SpecialSlotBtn.setVisibility(View.VISIBLE);
            TimeRemaining.setVisibility(View.VISIBLE);
            Instructions1.setVisibility(View.VISIBLE);
            Instructions2.setVisibility(View.VISIBLE);
            amounts1.setVisibility(View.VISIBLE);
            amounts2.setVisibility(View.VISIBLE);
            amounts3.setVisibility(View.VISIBLE);
            amounts4.setVisibility(View.VISIBLE);
            amounts5.setVisibility(View.VISIBLE);


            if(keyamount<1){
                SpecialSlotRemaining.setText("");
                SpecialSlot.setText("");
                SpecialSlotBtn.setVisibility(View.INVISIBLE);
                amounts6.setVisibility(View.INVISIBLE);
            }



        }
    });

    refIngredientsTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            DataSnapshot dS = task.getResult();
            for (DataSnapshot userSnapshot : dS.getChildren()) {
                Pair temporary = userSnapshot.getValue(Pair.class);
                ingreValues.add(temporary);
            }
        }
    });
}




        SpecialSlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = SpecialSlotRemaining.getText().toString();
                if(!getamount.isEmpty()){
                    int amount = Integer.parseInt(getamount);
                    if(myUser.getMoney()>=1000&&amount>0){
                        amount--;
                        String getitem = SpecialSlot.getText().toString();
                        HashMap<String,Integer> providemap = new HashMap<String, Integer>();
                        providemap = myUser.getKeypieces();
                        providemap.replace(getitem, 1);
                        myUser.setKeypieces(providemap);
                        myUser.setMoney(myUser.getMoney() - 1000);
                        YourGold.setText("Gold: " + myUser.getMoney());
                        SpecialSlotRemaining.setText(String.valueOf(amount));
                        refUsers.child(myUser.getUid()).setValue(myUser);
                        editor2.putInt("keyslotamount",0);
                        editor2.commit();

                    }
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Ingredient1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = Ingredient1Remaining.getText().toString();
                int amount = Integer.parseInt(getamount);
                if(myUser.getMoney()>4&&amount>0){
                    amount--;
                    String getitem = Ingredient1.getText().toString();
                    HashMap<String,Integer> providemap = new HashMap<String,Integer>();
                    providemap = myUser.getIngredients();
                    providemap.replace(getitem,myUser.getIngredients().get(getitem)+1);
                    myUser.setIngredients(providemap);
                    myUser.setMoney(myUser.getMoney() - 5);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    Ingredient1Remaining.setText(String.valueOf(amount));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    editor2.putInt("slotamount1",amount);
                    editor2.commit();
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Ingredient2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = Ingredient2Remaining.getText().toString();
                int amount = Integer.parseInt(getamount);
                if(myUser.getMoney()>4&&amount>0){
                    amount--;
                    String getitem = Ingredient2.getText().toString();
                    HashMap<String,Integer> providemap = new HashMap<String,Integer>();
                    providemap = myUser.getIngredients();
                    providemap.replace(getitem,myUser.getIngredients().get(getitem)+1);
                    myUser.setIngredients(providemap);
                    myUser.setMoney(myUser.getMoney() - 5);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    Ingredient2Remaining.setText(String.valueOf(amount));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    editor2.putInt("slotamount2",amount);
                    editor2.commit();
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Ingredient3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = Ingredient3Remaining.getText().toString();
                int amount = Integer.parseInt(getamount);
                if(myUser.getMoney()>4&&amount>0){
                    amount--;
                    String getitem = Ingredient3.getText().toString();
                    HashMap<String,Integer> providemap = new HashMap<String,Integer>();
                    providemap = myUser.getIngredients();
                    providemap.replace(getitem,myUser.getIngredients().get(getitem)+1);
                    myUser.setIngredients(providemap);
                    myUser.setMoney(myUser.getMoney() - 5);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    Ingredient3Remaining.setText(String.valueOf(amount));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    editor2.putInt("slotamount3",amount);
                    editor2.commit();
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Ingredient4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = Ingredient4Remaining.getText().toString();
                int amount = Integer.parseInt(getamount);
                if(myUser.getMoney()>4&&amount>0){
                    amount--;
                    String getitem = Ingredient4.getText().toString();
                    HashMap<String,Integer> providemap = new HashMap<String,Integer>();
                    providemap = myUser.getIngredients();
                    providemap.replace(getitem,myUser.getIngredients().get(getitem)+1);
                    myUser.setIngredients(providemap);
                    myUser.setMoney(myUser.getMoney() - 5);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    Ingredient4Remaining.setText(String.valueOf(amount));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    editor2.putInt("slotamount4",amount);
                    editor2.commit();
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Ingredient5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = Ingredient5Remaining.getText().toString();
                int amount = Integer.parseInt(getamount);
                if(myUser.getMoney()>4&&amount>0){
                    amount--;
                    String getitem = Ingredient5.getText().toString();
                    HashMap<String,Integer> providemap = new HashMap<String,Integer>();
                    providemap = myUser.getIngredients();
                    providemap.replace(getitem,myUser.getIngredients().get(getitem)+1);
                    myUser.setIngredients(providemap);
                    myUser.setMoney(myUser.getMoney() - 5);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    Ingredient5Remaining.setText(String.valueOf(amount));
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    editor2.putInt("slotamount5",amount);
                    editor2.commit();
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold or item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });



        RefreshShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialSlotRemaining.setText("");
                SpecialSlot.setText("");
                editor2.putString("keyslotname","");
                editor2.putInt("keyslotamount",0);
                SpecialSlotBtn.setVisibility(View.INVISIBLE);
                amounts6.setVisibility(View.INVISIBLE);
                if (myUser.getMoney() > 15) {
                    myUser.setMoney(myUser.getMoney()-15);
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    YourGold.setText("Gold: "+myUser.getMoney());
                    ArrayList<Integer> randomNums2 = new ArrayList<Integer>();
                    ArrayList<String> randomKeys2 = new ArrayList<String>();
                    HashSet hs2 = new HashSet();
                    while (hs2.size() < 5) {
                        int num = (int) (Math.random() * ingreValues.size());
                        hs2.add(num);
                    }
                    Iterator it = hs2.iterator();
                    while (it.hasNext()) {
                        randomNums2.add((int) it.next());
                    }
                    Random random = new Random();
                    int randomInteger1 = random.nextInt(15)+5;
                    int randomInteger2 = random.nextInt(15)+5;
                    int randomInteger3 = random.nextInt(15)+5;
                    int randomInteger4 = random.nextInt(15)+5;
                    int randomInteger5 = random.nextInt(15)+5;


                    Ingredient1.setText(ingreValues.get(randomNums2.get(0)).getKey());
                    Ingredient2.setText(ingreValues.get(randomNums2.get(1)).getKey());
                    Ingredient3.setText(ingreValues.get(randomNums2.get(2)).getKey());
                    Ingredient4.setText(ingreValues.get(randomNums2.get(3)).getKey());
                    Ingredient5.setText(ingreValues.get(randomNums2.get(4)).getKey());
                    Ingredient1Remaining.setText(String.valueOf(randomInteger1));
                    Ingredient2Remaining.setText(String.valueOf(randomInteger2));
                    Ingredient3Remaining.setText(String.valueOf(randomInteger3));
                    Ingredient4Remaining.setText(String.valueOf(randomInteger4));
                    Ingredient5Remaining.setText(String.valueOf(randomInteger5));
                    Ingredient1Image.setImageResource(R.drawable.wheat_plant);
                    Ingredient2Image.setImageResource(R.drawable.wheat_plant);
                    Ingredient3Image.setImageResource(R.drawable.wheat_plant);
                    Ingredient4Image.setImageResource(R.drawable.wheat_plant);
                    Ingredient5Image.setImageResource(R.drawable.wheat_plant);


                    editor2.putString("slotname1", ingreValues.get(randomNums2.get(0)).getKey());
                    editor2.putString("slotname2", ingreValues.get(randomNums2.get(1)).getKey());
                    editor2.putString("slotname3", ingreValues.get(randomNums2.get(2)).getKey());
                    editor2.putString("slotname4", ingreValues.get(randomNums2.get(3)).getKey());
                    editor2.putString("slotname5", ingreValues.get(randomNums2.get(4)).getKey());
                    editor2.putInt("slotamount1", randomInteger1);
                    editor2.putInt("slotamount2", randomInteger2);
                    editor2.putInt("slotamount3", randomInteger3);
                    editor2.putInt("slotamount4", randomInteger4);
                    editor2.putInt("slotamount5", randomInteger5);

                    editor2.commit();

                    int randomnum = random.nextInt(50);
                    if (randomnum == 0) {
                        Set<String> keySet2 = myUser.getKeypieces().keySet();
                        ArrayList<String> listOfKeys = new ArrayList<String>(keySet2);
                        Collection<Integer> values = myUser.getKeypieces().values();
                        ArrayList<Integer> listOfValues = new ArrayList<>(values);
                        int lengthCheck = listOfValues.size() - 1;
                        while (!listOfKeys.isEmpty()) {
                            if (listOfValues.get(lengthCheck) == 0) {
                                randomKeys2.add(listOfKeys.remove(lengthCheck));
                            } else {
                                listOfKeys.remove(lengthCheck);
                            }
                            lengthCheck--;
                        }
                        if (!randomKeys2.isEmpty()) {
                            int selectkey = random.nextInt(randomKeys2.size());
                            editor2.putString("keyslotname", randomKeys2.get(selectkey));
                            editor2.putInt("keyslotamount", 1);

                            editor2.commit();

                            String keyname = settings2.getString("keyslotname", "");
                            int keyamount = settings2.getInt("keyslotamount", 0);
                            SpecialSlotRemaining.setText(String.valueOf(keyamount));
                            SpecialSlot.setText(keyname);
                            SpecialSlotBtn.setVisibility(View.VISIBLE);
                            amounts6.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    Toast.makeText(TradeCenter.this, "Insufficient gold", Toast.LENGTH_SHORT).show();
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
    }

}