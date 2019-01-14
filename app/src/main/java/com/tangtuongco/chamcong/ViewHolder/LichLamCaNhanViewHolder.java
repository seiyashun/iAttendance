package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class LichLamCaNhanViewHolder extends RecyclerView.ViewHolder {
    public TextView txtNgay,txtCa;
    public LichLamCaNhanViewHolder(View itemView) {
        super(itemView);
        txtCa=itemView.findViewById(R.id.txtCaLamCaNhan);
        txtNgay=itemView.findViewById(R.id.txtNgayLamCaNhan);
    }
}
