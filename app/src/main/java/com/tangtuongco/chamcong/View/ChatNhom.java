package com.tangtuongco.chamcong.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.TinNhan;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.TinNhanChungViewHolder;

import java.util.Calendar;
import java.util.Date;

public class ChatNhom extends AppCompatActivity {
    ImageButton sendbut;
    EditText messArea;
    TextView txtUser,txtMess,txtTime;
    DatabaseReference dataTinNhan;
    FirebaseDatabase firebaseDatabase;
    RecyclerView lstMess;
    Toolbar toolbar;
    FirebaseRecyclerOptions<TinNhan> options;
    FirebaseRecyclerAdapter<TinNhan,TinNhanChungViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_nhom);
        anhxa();
        //Toolbar
        toolbar.setTitle("Tin Nhắn Chung");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataTinNhan=firebaseDatabase.getReference().child("TinNhan");
        sendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmess();
            }
        });
        init();

    }

    private void init() {
        lstMess.setHasFixedSize(true);
        lstMess.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        options=new FirebaseRecyclerOptions.Builder<TinNhan>()
                .setQuery(dataTinNhan,TinNhan.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<TinNhan, TinNhanChungViewHolder>(options) {

            @NonNull
            @Override
            public TinNhanChungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mess,parent,false);
//                return new TinNhanChungViewHolder(view);
                View v ;

                if(viewType==1)
                        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_sent, parent, false);
                else
                        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_received, parent, false);

                return  new TinNhanChungViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull TinNhanChungViewHolder holder, int position, @NonNull TinNhan model) {
                holder.txtMess.setText(model.getMessText());
                //Kiem tra nguoi gui
                if(!model.getMessUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                {
                    holder.txtUser.setText(model.getMessUser());
                }

                lstMess.smoothScrollToPosition(adapter.getItemCount()-1);
            }
            //Lay gia tri cua sender or receiver

            @Override
            public int getItemViewType(int position) {
                TinNhan tinNhan = this.getItem(position);
                Log.d("kiemtra",tinNhan.getMessUser().toString());
                if(tinNhan.getMessUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        lstMess.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        lstMess.setAdapter(adapter);



    }




    private void sendmess() {
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String mess=messArea.getText().toString();
        TinNhan a= new TinNhan();
        a.setMessText(mess);
        a.setMessUser(email);
        Date c= Calendar.getInstance().getTime();
        a.setMessDate(c);
        dataTinNhan.push().setValue(a);
        messArea.setText("");
        adapter.notifyDataSetChanged();
    }


    private void anhxa() {
        sendbut=findViewById(R.id.sendMess);
        messArea=findViewById(R.id.editWriteMessage);
        lstMess=findViewById(R.id.listMessNhom);
        toolbar=findViewById(R.id.toolbarChatNhom);
    }
}
