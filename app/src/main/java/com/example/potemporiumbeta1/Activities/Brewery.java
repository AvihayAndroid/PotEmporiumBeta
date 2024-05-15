package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refRecipes;
import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refUsers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForRecipes;
import com.example.potemporiumbeta1.Objects.NetworkStateReceiver;
import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;
import com.example.potemporiumbeta1.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Brewery extends AppCompatActivity {
    Spinner screenchanger,ingredient1Spinner,ingredient2Spinner,ingredient3Spinner,recipeSpinner;
    ImageButton ingredient1Plus,ingredient2Plus,ingredient3Plus,ingredient1Minus,ingredient2Minus,ingredient3Minus;
    Button createPotion;
    ListView recipeLw;
    TextView ingredient1Amount,ingredient2Amount,ingredient3Amount,Instruction;
    FirebaseAuth mAuth;
    User myUser;
    ArrayList<ArrayList<Pair>> helpList = new ArrayList<ArrayList<Pair>>();
    ArrayList<String> names = new ArrayList<String>();

    final private String myScreen = "Brewery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Brewery);
        ingredient1Amount = (TextView) findViewById(R.id.ingredient1Amount);
        ingredient2Amount = (TextView) findViewById(R.id.ingredient2Amount);
        ingredient3Amount = (TextView) findViewById(R.id.ingredient3Amount);
        ingredient1Plus = (ImageButton) findViewById(R.id.ingredient1Plus);
        ingredient2Plus = (ImageButton) findViewById(R.id.ingredient2Plus);
        ingredient3Plus = (ImageButton) findViewById(R.id.ingredient3Plus);
        ingredient1Minus = (ImageButton) findViewById(R.id.ingredient1Minus);
        ingredient2Minus = (ImageButton) findViewById(R.id.ingredient2Minus);
        ingredient3Minus = (ImageButton) findViewById(R.id.ingredient3Minus);
        ingredient1Spinner = (Spinner) findViewById(R.id.SpinnerIngredient1);
        ingredient2Spinner = (Spinner) findViewById(R.id.SpinnerIngredient2);
        ingredient3Spinner = (Spinner) findViewById(R.id.SpinnerIngredient3);
        recipeSpinner = (Spinner) findViewById(R.id.potionSelect);
        createPotion = (Button) findViewById(R.id.createPotion);
        recipeLw = (ListView) findViewById(R.id.RecipeLw);
        mAuth = FirebaseAuth.getInstance();
        Instruction = (TextView) findViewById(R.id.InstructionTv);



        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);




        Query query1 = refUsers.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if(userSnapshot.getValue(User.class).getUid().equals(mAuth.getCurrentUser().getUid())) {
                        myUser = userSnapshot.getValue(User.class);
                        ArrayList<String> IngreValues = new ArrayList<String>();
                        ArrayList<String> PotValues = new ArrayList<String>();
                        IngreValues.add("Choose Ingredient");
                        PotValues.add("Choose Potion");




                        Set<String> keySetIngredients = myUser.getIngredients().keySet();
                        ArrayList<String> listOfKeysIngredients = new ArrayList<String>(keySetIngredients);
                        int lengthCheckIngredients = listOfKeysIngredients.size()-1;
                        while(!listOfKeysIngredients.isEmpty()){
                            IngreValues.add(listOfKeysIngredients.remove(lengthCheckIngredients));
                            lengthCheckIngredients--;
                        }

                        Set<String> keySetPotions = myUser.getPotions().keySet();
                        ArrayList<String> listOfKeysPotions = new ArrayList<String>(keySetPotions);
                        int lengthCheckPotions = listOfKeysPotions.size()-1;
                        while(!listOfKeysPotions.isEmpty()){
                            PotValues.add(listOfKeysPotions.remove(lengthCheckPotions));
                            lengthCheckPotions--;
                        }

                        ArrayAdapter<String> arrayAdapterIngredients = new ArrayAdapter<String>(Brewery.this, android.R.layout.simple_spinner_item, IngreValues);
                        arrayAdapterIngredients.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        ingredient1Spinner.setAdapter(arrayAdapterIngredients);
                        ingredient2Spinner.setAdapter(arrayAdapterIngredients);
                        ingredient3Spinner.setAdapter(arrayAdapterIngredients);

                        ArrayAdapter<String> arrayAdapterPotions = new ArrayAdapter<String>(Brewery.this, android.R.layout.simple_spinner_item, PotValues);
                        arrayAdapterPotions.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        recipeSpinner.setAdapter(arrayAdapterPotions);


                        ingredient1Amount.setVisibility(View.VISIBLE);
                        ingredient1Minus.setVisibility(View.VISIBLE);
                        ingredient1Plus.setVisibility(View.VISIBLE);
                        ingredient1Spinner.setVisibility(View.VISIBLE);
                        ingredient2Amount.setVisibility(View.VISIBLE);
                        ingredient2Minus.setVisibility(View.VISIBLE);
                        ingredient2Plus.setVisibility(View.VISIBLE);
                        ingredient2Spinner.setVisibility(View.VISIBLE);
                        ingredient3Amount.setVisibility(View.VISIBLE);
                        ingredient3Minus.setVisibility(View.VISIBLE);
                        ingredient3Plus.setVisibility(View.VISIBLE);
                        ingredient3Spinner.setVisibility(View.VISIBLE);
                        createPotion.setVisibility(View.VISIBLE);
                        recipeLw.setVisibility(View.VISIBLE);
                        recipeSpinner.setVisibility(View.VISIBLE);
                        Instruction.setVisibility(View.VISIBLE);


                    }
                    }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        refRecipes.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dS = task.getResult();
                GenericTypeIndicator<ArrayList<Pair>> t = new GenericTypeIndicator<ArrayList<Pair>>() {};
                for (DataSnapshot userSnapshot : dS.getChildren()) {
                    helpList.add(userSnapshot.getValue(t));
                    names.add(userSnapshot.getKey());
                }

                recipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();

                        CustomBaseAdapterForRecipes customBaseAdapter2 = new CustomBaseAdapterForRecipes(getApplicationContext(),new ArrayList<Pair>());
                        recipeLw.setAdapter(customBaseAdapter2);

                        for(int i = 0;i<names.size();i++){
                            if(item.equals(names.get(i))){
                                CustomBaseAdapterForRecipes customBaseAdapter = new CustomBaseAdapterForRecipes(getApplicationContext(),helpList.get(i));
                                recipeLw.setAdapter(customBaseAdapter);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


        ingredient1Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient1Amount.getText().toString();
                int amount = Integer.parseInt(str);
                ingredient1Amount.setText(String.valueOf(amount+1));
            }
        });
        ingredient2Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient2Amount.getText().toString();
                int amount = Integer.parseInt(str);
                ingredient2Amount.setText(String.valueOf(amount+1));
            }
        });
        ingredient1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient1Amount.getText().toString();
                int amount = Integer.parseInt(str);
                if(amount>0){
                    ingredient1Amount.setText(String.valueOf(amount-1));
                }else{
                    Toast.makeText(Brewery.this, "Number must be higher than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ingredient2Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient2Amount.getText().toString();
                int amount = Integer.parseInt(str);
                if(amount>0){
                    ingredient2Amount.setText(String.valueOf(amount-1));
                }else{
                    Toast.makeText(Brewery.this, "Number must be higher than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ingredient3Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient3Amount.getText().toString();
                int amount = Integer.parseInt(str);
                ingredient3Amount.setText(String.valueOf(amount+1));
            }
        });
        ingredient3Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient3Amount.getText().toString();
                int amount = Integer.parseInt(str);
                if(amount>0){
                    ingredient3Amount.setText(String.valueOf(amount-1));
                }else{
                    Toast.makeText(Brewery.this, "Number must be higher than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createPotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                while(counter<names.size()){
                    if(names.get(counter).equals(recipeSpinner.getSelectedItem().toString())){
                        int amount1 = Integer.parseInt(ingredient1Amount.getText().toString());
                        int amount2 = Integer.parseInt(ingredient2Amount.getText().toString());
                        int amount3 = Integer.parseInt(ingredient3Amount.getText().toString());
                        String name1 = ingredient1Spinner.getSelectedItem().toString();
                        String name2 = ingredient2Spinner.getSelectedItem().toString();
                        String name3 = ingredient3Spinner.getSelectedItem().toString();
                        if(amount1 == helpList.get(counter).get(0).getAmount() && amount2 == helpList.get(counter).get(1).getAmount() && amount3 == helpList.get(counter).get(2).getAmount() && name1.equals(helpList.get(counter).get(0).getKey()) && name2.equals(helpList.get(counter).get(1).getKey()) && name3.equals(helpList.get(counter).get(2).getKey())){
                            HashMap<String,Integer> providemap1 = new HashMap<String,Integer>();
                            HashMap<String,Integer> providemap2 = new HashMap<String,Integer>();
                            providemap1 = myUser.getPotions();
                            providemap2 = myUser.getIngredients();
                            
                            
                            String keyIng1 = helpList.get(counter).get(0).getKey();
                            String keyIng2 = helpList.get(counter).get(1).getKey();
                            String keyIng3 = helpList.get(counter).get(2).getKey();
                            int oldvalueIng1 = myUser.getIngredients().get(keyIng1);
                            int oldvalueIng2 = myUser.getIngredients().get(keyIng2);
                            int oldvalueIng3 = myUser.getIngredients().get(keyIng3);
                            
                            if(oldvalueIng3>=amount3 && oldvalueIng2>=amount2 && oldvalueIng1>=amount1){
                                providemap2.replace(keyIng1,oldvalueIng1-amount1);
                                providemap2.replace(keyIng2,oldvalueIng2-amount2);
                                providemap2.replace(keyIng3,oldvalueIng3-amount3);

                                int oldvaluePot = myUser.getPotions().get(names.get(counter));
                                String keyPot = names.get(counter);
                                providemap1.replace(keyPot,oldvaluePot+1);
                                
                                myUser.setPotions(providemap1);
                                myUser.setIngredients(providemap2);
                                refUsers.child(myUser.getUid()).setValue(myUser);
                                Toast.makeText(Brewery.this, "Successfully crafted "+names.get(counter), Toast.LENGTH_LONG).show();
                                return;
                                
                            }else {
                                Toast.makeText(Brewery.this, "Insufficient ingredients", Toast.LENGTH_LONG).show();
                                return;
                            }
                            
                            
                        }

                    }
                    counter++;
                }
                if (counter == names.size()){
                    Toast.makeText(Brewery.this, "Crafting failed", Toast.LENGTH_LONG).show();
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