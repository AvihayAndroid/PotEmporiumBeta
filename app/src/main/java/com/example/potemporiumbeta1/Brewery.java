package com.example.potemporiumbeta1;

import android.content.Intent;
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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Brewery extends AppCompatActivity {
    Spinner screenchanger,ingredient1Spinner,ingredient2Spinner,ingredient3Spinner,recipeSpinner;
    ImageButton ingredient1Plus,ingredient2Plus,ingredient3Plus,ingredient1Minus,ingredient2Minus,ingredient3Minus;
    Button createPotion;
    ListView recipeLw;
    TextView ingredient1Amount,ingredient2Amount,ingredient3Amount;

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
        ingredient3Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ingredient3Amount.getText().toString();
                int amount = Integer.parseInt(str);
                ingredient3Amount.setText(String.valueOf(amount+1));
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