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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.TinNhan;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.TinNhanChungViewHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatRieng extends AppCompatActivity {
    ImageButton sendbut;
    EditText messArea;
    TextView txtUser,txtMess,txtTime;
    DatabaseReference dataTinNhanRieng;
    FirebaseDatabase firebaseDatabase;
    RecyclerView lstMess;
    Toolbar toolbar;
    String sender,idnguoinhan;
    FirebaseRecyclerOptions<TinNhan> options;
    FirebaseRecyclerAdapter<TinNhan,TinNhanChungViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rieng);

        anhxa();

        toolbar.setTitle("Tin nhắn");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Lay id nguoi nhan
        idnguoinhan= getIntent().getExtras().get("receiver_id").toString();
        //Firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataTinNhanRieng=firebaseDatabase.getReference().child("TinNhanRieng");
        sender=FirebaseAuth.getInstance().getCurrentUser().getEmail();

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
        Query query=dataTinNhanRieng.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(idnguoinhan);

        options=new FirebaseRecyclerOptions.Builder<TinNhan>()
                .setQuery(query,TinNhan.class)
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
        //Push mess len server
        dataTinNhanRieng.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(idnguoinhan).push().setValue(a);


        messArea.setText("");
        adapter.notifyDataSetChanged();
    }


    private void anhxa() {
        sendbut=findViewById(R.id.sendMessRieng);
        messArea=findViewById(R.id.editWriteMessageRieng);
        lstMess=findViewById(R.id.listMessRieng);
        toolbar=findViewById(R.id.toolbarChatRieng);
    }

}
