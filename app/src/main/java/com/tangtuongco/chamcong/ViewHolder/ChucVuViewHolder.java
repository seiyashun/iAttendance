package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class ChucVuViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName,txtID,txtHeSoLuong;

    public ChucVuViewHolder(View itemView) {
        super(itemView);
        txtName=itemView.findViewById(R.id.txtTenChucVuAdmin);
        txtID=itemView.findViewById(R.id.txtIdChucVuAdmin);
        txtHeSoLuong=itemView.findViewById(R.id.txtHeSoLuong);
    }
}
