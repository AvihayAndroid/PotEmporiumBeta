package com.example.potemporiumbeta1.Activities;

import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refLobbies;
import static com.example.potemporiumbeta1.FirebaseRefrence.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForItems;
import com.example.potemporiumbeta1.Adapters.CustomBaseAdapterForLobbies;
import com.example.potemporiumbeta1.Objects.Comms;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Arena extends AppCompatActivity {
    final private String myScreen = "Arena";

    Spinner screenchanger;
    ListView Lobbies;
    Button createLobby,setBattlemessage;
    EditText battlemessageEt;
    User myUser = ShopFront.myUser;
    ArrayList<User> userList = ShopFront.userList;
    Comms example;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Comms> array = new ArrayList<Comms>();
    ArrayList<User> userarray = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        screenchanger = (Spinner) findViewById(R.id.ScreenSpinner_Arena);
        Lobbies = (ListView) findViewById(R.id.LobbiesLw);
        createLobby = (Button) findViewById(R.id.createLobby);
        setBattlemessage = (Button) findViewById(R.id.setBattlemessage);
        battlemessageEt = (EditText) findViewById(R.id.battlemessageEt);


        if(!myUser.getBattleMessage().equals("")){
            battlemessageEt.setText(myUser.getBattleMessage());
        }

        Query query1 = refLobbies;
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                array.clear();
                userarray.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    example = userSnapshot.getValue(Comms.class);
                    if(example.isIsopen()) {
                        array.add(example);
                    }
                }
                for (int i=0;i<userList.size();i++){
                    for(int j=0;j<array.size();j++){
                        if(userList.get(i).getUid().equals(array.get(j).getUser1())){
                            userarray.add(userList.get(i));
                        }
                    }
                }
                CustomBaseAdapterForLobbies customBaseAdapter = new CustomBaseAdapterForLobbies(getApplicationContext(),userarray);
                Lobbies.setAdapter(customBaseAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comms comms = new Comms(myUser.getUid());
                if(myUser.getBattleMessage().equals("")){
                    Toast.makeText(Arena.this, "You must enter a battle message before creating a lobby", Toast.LENGTH_SHORT).show();
                }else{
                    refLobbies.child(myUser.getUid()).setValue(comms);
                    Intent intent = new Intent(getApplicationContext(), FightStage.class);
                    intent.putExtra("isowner",true);
                    startActivity(intent);
                    finish();
                }

            }
        });




        setBattlemessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = battlemessageEt.getText().toString();
                if(!str.equals("")) {
                    myUser.setBattleMessage(str);
                    refUsers.child(myUser.getUid()).setValue(myUser);
                    Toast.makeText(Arena.this, "Message successfully set!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Arena.this, "Must not input a blank message", Toast.LENGTH_SHORT).show();
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
                        case "Arena": Intent intent5 = new Intent(getApplicationContext(), Arena.class);
                            startActivity(intent5);
                            finish();
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