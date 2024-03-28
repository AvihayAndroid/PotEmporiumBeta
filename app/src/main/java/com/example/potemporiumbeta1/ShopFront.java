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
import android.widget.ListView;
import android.widget.Spinner;
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
    private Handler mHandler = new Handler();
    Button leave,goinventory;
    FirebaseAuth mAuth;
    ListView potionsLw,ingredientsLw,keypiecesLw;
    int[] images;
    private User helper;
    final private String myScreen = "ShopFront";
    Spinner screenchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_front);
        leave = (Button) findViewById(R.id.gobackBtn);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_ShopFront);
        mAuth = FirebaseAuth.getInstance();






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
    private Runnable waitsec = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), helper.getUid(), Toast.LENGTH_SHORT).show();
        }
    };



}