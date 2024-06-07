package com.example.potemporiumbeta1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.Objects.User;
import com.example.potemporiumbeta1.R;

import java.util.ArrayList;

public class CustomBaseAdapterForLbGold extends BaseAdapter {
    Context context;
    ArrayList<User> arrayList;
    ArrayList<Integer> arrayList2;
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
        convertView = inflater.inflate(R.layout.custom_list_view_lbgold,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.playerplace);
        TextView txtView2 = (TextView) convertView.findViewById(R.id.playergold);
        TextView txtView3 = (TextView) convertView.findViewById(R.id.playername);
        txtView.setText(String.valueOf(arrayList2.get(position)));
        txtView2.setText("Amount of Gold: "+String.valueOf(arrayList.get(position).getMoney()));
        txtView3.setText(String.valueOf(arrayList.get(position).getUsername()));
        return convertView;
    }
    public CustomBaseAdapterForLbGold(Context ctx, ArrayList<User> arrayList,ArrayList<Integer> arrayList2){
        this.context=ctx;
        this.arrayList=arrayList;
        this.arrayList2=arrayList2;
        inflater = LayoutInflater.from(ctx);
    }
}