package com.tangtuongco.chamcong.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.Model.TinNhan;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.TinNhanChungViewHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ChatRieng extends AppCompatActivity {
    ImageButton sendbut;
    EditText messArea;
    TextView txtUser, txtMess, txtTime;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    RecyclerView lstMess;
    Toolbar toolbar;
    String sender, idnguoinhan;
    FirebaseRecyclerOptions<TinNhan> options;
    FirebaseRecyclerAdapter<TinNhan, TinNhanChungViewHolder> adapter;
    String email;
    NhanVien currentNv;

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
        idnguoinhan = getIntent().getExtras().get("receiver_id").toString();
        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mData=firebaseDatabase.getReference();

        //getData
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getData();

        sendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmess();
            }
        });

    }

    private void getData() {

        mData = firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    NhanVien a = dataSnapshot1.getValue(NhanVien.class);
                    if(a.getEmail().equals(email))
                    {
                        saveNV(a);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveNV(NhanVien a) {
        currentNv=a;
        init();
    }


    private void init() {
        mData = firebaseDatabase.getReference().child("TinNhanRieng");
        lstMess.setHasFixedSize(true);
        lstMess.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Query query = mData.child(currentNv.getManv()).child(idnguoinhan);

        options = new FirebaseRecyclerOptions.Builder<TinNhan>()
                .setQuery(query, TinNhan.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<TinNhan, TinNhanChungViewHolder>(options) {

            @NonNull
            @Override
            public TinNhanChungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mess,parent,false);
//                return new TinNhanChungViewHolder(view);
                View v;

                if (viewType == 1)
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_sent, parent, false);
                else
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_received, parent, false);

                return new TinNhanChungViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull TinNhanChungViewHolder holder, int position, @NonNull TinNhan model) {
                holder.txtMess.setText(model.getMessText());
                //Kiem tra nguoi gui
                if (!model.getMessUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    holder.txtUser.setText(model.getMessUser());
                }

                lstMess.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
            //Lay gia tri cua sender or receiver

            @Override
            public int getItemViewType(int position) {
                TinNhan tinNhan = this.getItem(position);
                Log.d("kiemtra", tinNhan.getMessUser().toString());
                if (tinNhan.getMessUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        lstMess.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        lstMess.setAdapter(adapter);
    }

    private void sendmess() {

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String mess = messArea.getText().toString();
        TinNhan a = new TinNhan();
        a.setMessText(mess);
        a.setMessUser(email);
        Date c = Calendar.getInstance().getTime();
        a.setMessDate(c);
        //Push mess len server


        String mess_sender_ref = currentNv.getManv() +"/" + idnguoinhan;
        String mess_receiver_ref=idnguoinhan +"/" + currentNv.getManv();
         DatabaseReference mTinNhan = firebaseDatabase.getReference().child("TinNhanRieng")
                                                            .child(currentNv.getManv())
                                                            .child(idnguoinhan).push();
        String mess_push_id = mTinNhan.getKey();

        Map messTextBody = new HashMap();

        messTextBody.put("messDate", a.getMessDate());
        messTextBody.put("messText",a.getMessText());
        messTextBody.put("messUser",a.getMessUser());

        Map messBody = new HashMap();
        messBody.put(mess_sender_ref+"/" +mess_push_id,messTextBody);
        messBody.put(mess_receiver_ref+"/" +mess_push_id,messTextBody);
        mData.updateChildren(messBody, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError!=null)
                {
                    Toast.makeText(ChatRieng.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });



        messArea.setText("");
        adapter.notifyDataSetChanged();
    }


    private void anhxa() {
        sendbut = findViewById(R.id.sendMessRieng);
        messArea = findViewById(R.id.editWriteMessageRieng);
        lstMess = findViewById(R.id.listMessRieng);
        toolbar = findViewById(R.id.toolbarChatRieng);
    }

}
