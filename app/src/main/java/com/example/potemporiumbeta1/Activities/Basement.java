package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Basement extends AppCompatActivity {
    final private String myScreen = "Basement";
    User myUser = ShopFront.myUser;
    Spinner screenchanger;
    Button unlock;
    TextView unlockedText,havekeys,donthavekeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basement);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Basement);
        unlock = (Button) findViewById(R.id.unlockBasement);
        unlockedText = (TextView) findViewById(R.id.unlockedText);
        havekeys = (TextView) findViewById(R.id.keypieceHave);
        donthavekeys = (TextView) findViewById(R.id.keypiecenotHave);


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
                        case "Arena":
                            if (ShopFront.myUser.isFightUnlocked()){
                                Intent intent5 = new Intent(getApplicationContext(), Arena.class);
                                startActivity(intent5);
                                finish();
                            }else{
                                Toast.makeText(Basement.this, "You must first unlock the basement", Toast.LENGTH_SHORT).show();
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
        arrayList.add("Arena");
        arrayList.add("Basement");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        screenchanger.setAdapter(arrayAdapter);

    }
}