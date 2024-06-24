package com.example.potemporiumbeta1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;

import java.util.ArrayList;

public class CustomBaseAdapterForLobbies extends BaseAdapter {
    Context context;
    ArrayList<User> arrayList;
    LayoutInflater inflater;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list_view_lobbies,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.goldWager);
        TextView txtView2 = (TextView) convertView.findViewById(R.id.playermessage);
        TextView txtView3 = (TextView) convertView.findViewById(R.id.playername);
        TextView txtView4 = (TextView) convertView.findViewById(R.id.playerreputation);
        txtView.setText("Gold: "+arrayList.get(position).getGoldWager());
        txtView2.setText(String.valueOf(arrayList.get(position).getBattleMessage()));
        txtView3.setText(String.valueOf(arrayList.get(position).getUsername()));
        txtView4.setText("Reputation: "+String.valueOf(arrayList.get(position).getReputation()/10+"."+arrayList.get(position).getReputation()%10));
        return convertView;
    }
    public CustomBaseAdapterForLobbies(Context ctx, ArrayList<User> arrayList){
        this.context=ctx;
        this.arrayList=arrayList;
        inflater = LayoutInflater.from(ctx);
    }
}
