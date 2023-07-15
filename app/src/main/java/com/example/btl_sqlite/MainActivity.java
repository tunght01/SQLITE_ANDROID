package com.example.btl_sqlite;
import com.example.btl_sqlite.CSDL.Database_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnlogin, btnsignup, btnthoatct;
    EditText edtuser, edtpass;
    String user="";
    String pass="";




    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtuser = findViewById(R.id.edtusername);
        edtpass=findViewById(R.id.edtpassword);
        btnlogin = findViewById(R.id.btn_login);
        btnsignup = findViewById(R.id.btn_signup);
        btnthoatct= findViewById(R.id.btn_thoatct);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Database_login datalog = new Database_login(MainActivity.this);
                SQLiteDatabase db = datalog.getDatabase();
                Cursor c = db.query("login",null,null,null,null,null,null);
                if (c.moveToFirst()){
                    do {
                        user = c.getString(c.getColumnIndex("user"));
                        pass = c.getString(c.getColumnIndex("pass"));
                    }while (c.moveToNext());
                    c.close();
                    db.close();
                }
                String checkuser = edtuser.getText().toString();
                String checkpass = edtpass.getText().toString();
                if (checkuser.isEmpty()){
                    if (checkpass.isEmpty()){
                        Toast.makeText(MainActivity.this,"Bạn chưa nhập tài khoản và mật khẩu",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,"Bạn chưa nhập tài khoản",Toast.LENGTH_SHORT).show();
                    }
                }else if (!checkuser.isEmpty() && checkpass.isEmpty()){
                    Toast.makeText(MainActivity.this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show();
                }else  if (checkuser.equals(user) == true && checkpass.equals(pass)==true){
                    Intent myIntent = new Intent(MainActivity.this, danhSachNhanVien.class);
                    startActivity(myIntent);
                }else {
                    Toast.makeText(MainActivity.this,"Tài khoản mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, formSignup.class);
                startActivity(myIntent);
            }
        });
        btnthoatct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn có chắc muốn thoát không?");
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}