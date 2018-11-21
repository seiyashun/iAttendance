package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

public class GioCongViewHolder extends RecyclerView.ViewHolder {
    public  TextView txtNgay,txtGioVao,txtGioRa,txtGioThucTe;

    public GioCongViewHolder(View itemView) {
        super(itemView);
        txtNgay=itemView.findViewById(R.id.txtNgayTheoDoiCong);
        txtGioVao=itemView.findViewById(R.id.txtGioVaoTheoDoiCong);
        txtGioRa=itemView.findViewById(R.id.txtGioRaTheDoiCong);
        txtGioThucTe=itemView.findViewById(R.id.txtSoGioCongTheoDoi1Ngay);

    }
}
