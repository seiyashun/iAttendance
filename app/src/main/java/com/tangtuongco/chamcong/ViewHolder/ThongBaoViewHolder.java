package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle,txtDate;
    public ThongBaoViewHolder(View itemView) {
        super(itemView);
        txtDate=itemView.findViewById(R.id.txtThoiGianThongBao);
        txtTitle=itemView.findViewById(R.id.txtTieuDeThongBao);
    }
}
