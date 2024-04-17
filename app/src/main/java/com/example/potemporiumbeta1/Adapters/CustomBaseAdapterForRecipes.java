package com.example.potemporiumbeta1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.potemporiumbeta1.Objects.Pair;
import com.example.potemporiumbeta1.R;

import java.util.ArrayList;

public class CustomBaseAdapterForRecipes extends BaseAdapter {
    Context context;
    ArrayList<Pair> arrayList;
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
        convertView = inflater.inflate(R.layout.activity_custom_list_view_recipes,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.IngredientType_LW);
        TextView txtView2 = (TextView) convertView.findViewById(R.id.IngredientAmount_LW);
        txtView.setText(String.valueOf(arrayList.get(position).getKey()));
        txtView2.setText(String.valueOf(arrayList.get(position).getAmount()));
        return convertView;
    }
    public CustomBaseAdapterForRecipes(Context ctx, ArrayList<Pair> arrayList){
        this.context=ctx;
        this.arrayList=arrayList;
        inflater = LayoutInflater.from(ctx);
    }
}
