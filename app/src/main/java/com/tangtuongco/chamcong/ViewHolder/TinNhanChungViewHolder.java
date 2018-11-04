package com.tangtuongco.chamcong.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TinNhanChungViewHolder extends RecyclerView.ViewHolder {
    public  TextView txtUser,txtMess,txtDate;


    public TinNhanChungViewHolder(View itemView) {
        super(itemView);

        txtUser=itemView.findViewById(R.id.text_message_name);
        txtMess=itemView.findViewById(R.id.text_message_body);



    }

}
