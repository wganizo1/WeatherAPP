package com.global.kinetic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Model> arrayList;
    private TextView main, description;
    private ImageView icon;
    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.weather_info, parent, false);
        main = convertView.findViewById(R.id.main);
        description = convertView.findViewById(R.id.description);
        icon = convertView.findViewById(R.id.icon);
        main.setText(arrayList.get(position).getMain());
        description.setText(arrayList.get(position).getDescription());

//        Glide.with(context)
//                .asBitmap()
//                .placeholder(R.mipmap.header)
//                .load(arrayList.get(position).getIcon())
//                .into(icon);
        return convertView;
    }

}