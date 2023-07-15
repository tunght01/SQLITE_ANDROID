package com.example.btl_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_sqlite.CSDL.Database_login;

public class formSignup extends AppCompatActivity {
    Button btn_back, btn_signup;
    EditText edtuser, edtpass, edtconfirm;
    String user="";
    String pass="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_signup);
        edtuser = findViewById(R.id.edtusername1);
        edtpass=findViewById(R.id.edtpassword1);
        edtconfirm=findViewById(R.id.edt_confirm);
        btn_signup = findViewById(R.id.btn_signup1);
        btn_back = findViewById(R.id.btn_thoat);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                String user = edtuser.getText().toString();
                String pass = edtpass.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("user",user);
                contentValues.put("pass",pass);
                Database_login datalog = new Database_login(formSignup.this);
                SQLiteDatabase db = datalog.getDatabase();
                db.query("login",null,null,null,null,null,null);
                Cursor c = db.query("login",null,null,null,null,null,null);
                if (c.moveToFirst()){
                    do {
                        user = c.getString(c.getColumnIndex("user"));
                    }while (c.moveToNext());
                    c.close();
                    db.close();
                }
                String checkuser = edtuser.getText().toString();
                String checkpass1=edtpass.getText().toString();
                String checkpass= edtconfirm.getText().toString();
                if (checkuser.equals(user)){
                    Toast.makeText(formSignup.this,"Tài khoản đã tồn tại",Toast.LENGTH_SHORT).show();
                } else if (!checkpass1.equals(checkpass)) {
                    Toast.makeText(formSignup.this,"Hai mật khẩu bạn nhập không khớp!",Toast.LENGTH_SHORT).show();

                } else {
                    db.insert("login",null,contentValues);

                    AlertDialog.Builder builder = new AlertDialog.Builder(formSignup.this);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Bạn đã đăng ký thành công, quay về đăng nhập!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



}