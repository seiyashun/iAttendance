package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class LichDangKyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTen,txtCa;
    public LichDangKyViewHolder(View itemView) {
        super(itemView);
        txtTen=itemView.findViewById(R.id.txtTenNhanVienDangKy);
        txtCa=itemView.findViewById(R.id.txtCaNhanVienDangKy);
    }
}
