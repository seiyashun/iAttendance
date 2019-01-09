package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class LichLamViewHolder extends RecyclerView.ViewHolder {
    public TextView txtNgay,txtCa;
    public LichLamViewHolder(View itemView) {
        super(itemView);
        txtCa=itemView.findViewById(R.id.txtCaDangKy);
        txtNgay=itemView.findViewById(R.id.txtNgayDangKy);
    }
}
