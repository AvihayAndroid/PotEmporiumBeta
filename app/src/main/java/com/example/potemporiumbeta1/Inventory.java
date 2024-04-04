package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


public class Inventory extends AppCompatActivity {
    FirebaseAuth mAuth;
    ListView potionsLw,ingredientsLw,keypiecesLw;
    TextView goldTv;
    int[] images;
    public User helper;
    final private String myScreen = "Inventory";
    Spinner screenchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mAuth = FirebaseAuth.getInstance();
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Inventory);
        goldTv = (TextView) findViewById(R.id.goldTv);

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



        Query query = refUsers.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if(userSnapshot.getValue(User.class).getUid().equals(mAuth.getCurrentUser().getUid())){

                        ArrayList<Pair> potionsAl=new ArrayList<Pair>();
                        ArrayList<Pair> ingredientsAl=new ArrayList<Pair>();
                        ArrayList<Pair> keypiecesAl=new ArrayList<Pair>();



                        helper = userSnapshot.getValue(User.class);



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
                        potionsLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(Inventory.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
                            }
                        });



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
                        ingredientsLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(Inventory.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
                            }
                        });



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
                        keypiecesLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(Inventory.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
                            }
                        });

                        goldTv.setText("Gold: "+ String.valueOf(helper.getMoney()));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}