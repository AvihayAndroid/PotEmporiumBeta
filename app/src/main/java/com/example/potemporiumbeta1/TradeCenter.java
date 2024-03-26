package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refIngredientsTable;
import static com.example.potemporiumbeta1.FBRef.refKeypiecesTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import java.math.*;
import java.util.Set;

public class TradeCenter extends AppCompatActivity {
    Spinner screenchanger;
    TextView Ingredient1,Ingredient2,Ingredient3,Ingredient4,Ingredient5,Ingredient1Remaining,Ingredient2Remaining,Ingredient3Remaining,Ingredient4Remaining,Ingredient5Remaining,SpecialSlot,SpecialSlotRemaining,TimeRemaining,YourGold;
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
                int randomnum = random.nextInt(1);
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
                int randomInteger1 = random.nextInt(20);
                int randomInteger2 = random.nextInt(20);
                int randomInteger3 = random.nextInt(20);
                int randomInteger4 = random.nextInt(20);
                int randomInteger5 = random.nextInt(20);


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
            }
        });





        SpecialSlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getamount = SpecialSlotRemaining.getText().toString();
                if(!getamount.isEmpty()){
                    int amount = Integer.parseInt(getamount);
                    if(myUser.getMoney()>=5&&amount>0){
                        amount--;
                        String getitem = SpecialSlot.getText().toString();
                        HashMap<String, Integer> providemap = new HashMap<String, Integer>();
                        providemap = myUser.getKeypieces();
                        providemap.replace(getitem, 1);
                        myUser.setKeypieces(providemap);
                        myUser.setMoney(myUser.getMoney() - 5);
                        YourGold.setText("Gold: " + myUser.getMoney());
                        SpecialSlotRemaining.setText(String.valueOf(amount));
                        refUsers.child(myUser.getUid()).setValue(myUser);

                    }
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
//                    case "Brewery": Intent intent4 = new Intent(getApplicationContext(), Brewery.class);
//                        startActivity(intent4);
//                        finish();
//                        break;
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