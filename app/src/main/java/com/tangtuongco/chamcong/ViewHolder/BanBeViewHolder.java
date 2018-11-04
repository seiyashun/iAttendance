package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tangtuongco.chamcong.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BanBeViewHolder extends RecyclerView.ViewHolder{
    public TextView txtTenBanBe,txtEmailBanBe;
    public CircleImageView imaAvaBanBe;

    public BanBeViewHolder(final View itemView) {
        super(itemView);
        txtEmailBanBe=itemView.findViewById(R.id.txtEmailBanBe);
        txtTenBanBe=itemView.findViewById(R.id.txtTenBanBe);
        imaAvaBanBe=itemView.findViewById(R.id.imgAvaBanBe);



    }

}
