package com.tangtuongco.chamcong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tangtuongco.chamcong.Model.TinNhan;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;

import java.util.ArrayList;

public class AdapterChat extends BaseAdapter {
    Context context;
    ArrayList<TinNhan> arrayListTinNhan;
    LayoutInflater layoutInflater;

    public AdapterChat(Context context, ArrayList<TinNhan> arrayListTinNhan) {
        this.context = context;
        this.arrayListTinNhan = arrayListTinNhan;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayListTinNhan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTinNhan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView=layoutInflater.inflate(R.layout.list_mess,null);
//        TextView txtUser=convertView.findViewById(R.id.messUser);
//        TextView txtText=convertView.findViewById(R.id.messText);
//        TextView txtDate=convertView.findViewById(R.id.messText);
//
//        txtDate.setText(FormatHelper.formatNgay(arrayListTinNhan.get(position).getMessDate()));
//        txtText.setText(arrayListTinNhan.get(position).getMessText());
//        txtUser.setText(arrayListTinNhan.get(position).getMessUser());





        return convertView;
    }
}
