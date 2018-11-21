package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class QuanLyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTenQly,txtEmailQly,txtChucVu;
    public QuanLyViewHolder(View itemView) {
        super(itemView);
        txtTenQly=itemView.findViewById(R.id.txtTenQuanLyList);
        txtEmailQly=itemView.findViewById(R.id.txtEmailQuanLyList);
        txtChucVu=itemView.findViewById(R.id.txtChucVuQuanLyList);

    }
}
