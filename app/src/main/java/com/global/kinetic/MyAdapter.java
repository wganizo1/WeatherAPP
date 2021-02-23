package com.global.kinetic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyData> arrayList;
    private TextView serialNum, name, contactNum;
    private ImageView icon;
    public MyAdapter(Context context, ArrayList<MyData> arrayList) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        serialNum = convertView.findViewById(R.id.serailNumber);
        icon = convertView.findViewById(R.id.icon);
        name = convertView.findViewById(R.id.studentName);
        contactNum = convertView.findViewById(R.id.mobileNum);
        contactNum.setText(arrayList.get(position).getID());
        serialNum.setText(arrayList.get(position).getDescription());
        name.setText(arrayList.get(position).getMain());

        Glide.with(context)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .load(arrayList.get(position).getIcon())
                .into(icon);
        return convertView;
    }
    public static String takeLast(String value, int count) {
        if (value == null || value.trim().length() == 0 || count < 1) {
            return "";
        }

        if (value.length() > count) {
            return value.substring(value.length() - count);
        } else {
            return value;
        }
    }
}