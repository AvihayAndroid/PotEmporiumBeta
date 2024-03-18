package com.example.potemporiumbeta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Pair> arrayList;
    int[] listImages;
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
        convertView = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.PotionsTextView_LW);
        TextView txtView2 = (TextView) convertView.findViewById(R.id.PotionsTextView_LW2);
        ImageView image = (ImageView) convertView.findViewById(R.id.ImageIcon);
        txtView.setText(String.valueOf(arrayList.get(position).getKey()));
        txtView2.setText(String.valueOf(arrayList.get(position).getAmount()));
        /*image.setImageResource(listImages[position]);*/
        return convertView;
    }
    public CustomBaseAdapter(Context ctx, ArrayList<Pair> arrayList,int [] listImages){
        this.context=ctx;
        this.arrayList=arrayList;
        inflater = LayoutInflater.from(ctx);
        this.listImages=listImages;

    }
}