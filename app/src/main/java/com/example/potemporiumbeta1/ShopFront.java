package com.example.potemporiumbeta1;

import static com.example.potemporiumbeta1.FBRef.refPotionsTable;
import static com.example.potemporiumbeta1.FBRef.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
    Button leave;
    FirebaseAuth mAuth;
    ListView potionsLw,ingredientsLw,keypiecesLw;
    int[] images;
    private User helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_front);
        leave = (Button) findViewById(R.id.gobackBtn);
        mAuth = FirebaseAuth.getInstance();


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
                        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),potionsAl,null);
                        potionsLw.setAdapter(customBaseAdapter);
                        potionsLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(ShopFront.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
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
                        CustomBaseAdapter customBaseAdapter2 = new CustomBaseAdapter(getApplicationContext(),ingredientsAl,null);
                        ingredientsLw.setAdapter(customBaseAdapter2);
                        ingredientsLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(ShopFront.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
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
                        CustomBaseAdapter customBaseAdapter3 = new CustomBaseAdapter(getApplicationContext(),keypiecesAl,null);
                        keypiecesLw.setAdapter(customBaseAdapter3);
                        keypiecesLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(ShopFront.this, "clicked position "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
                            }
                        });















                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        // Sign out button and intent to login screen.

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