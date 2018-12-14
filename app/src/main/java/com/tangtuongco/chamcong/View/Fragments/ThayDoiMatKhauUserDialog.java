package com.tangtuongco.chamcong.View.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.R;

public class ThayDoiMatKhauUserDialog extends AppCompatDialogFragment {
    EditText edtPass1,edtPass2,edtPassOld;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_edit_matkhau,null);
        anhxa(view);
        builder.setView(view)
                .setTitle("Thay Đổi Mật Khẩu")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdatePassword(view);
                    }
                });
        return builder.create();




    }

    private void UpdatePassword(final View view) {
        final String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(!edtPass2.getText().toString().equals(edtPass1.getText().toString()))
        {
            Toast.makeText(getActivity(), "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
        }
        else if(edtPass1.getText().toString().equals("") || edtPass2.getText().toString().equals("")  ||edtPassOld.getText().toString().equals("")  )
        {
            Toast.makeText(getActivity(), "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
        }
        else

        {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, edtPassOld.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        user.updatePassword(edtPass2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), "Thay đổi không mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void anhxa(View view) {
        edtPassOld=view.findViewById(R.id.edtPassOld);
        edtPass1=view.findViewById(R.id.edtPassUserEdit);
        edtPass2=view.findViewById(R.id.edtPassUserEdit2);
    }
}
