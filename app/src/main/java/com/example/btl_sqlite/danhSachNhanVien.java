package com.example.btl_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btl_sqlite.CSDL.Database_nhanVien;

import java.util.ArrayList;

public class danhSachNhanVien extends AppCompatActivity {

    private static final String DATABASE_NAME = "NhanVien.db";
    private ListView listView;
    private Button btn_Them;
    private Button btn_timkiem;
    private Button btn_exit;
    private ArrayList<NhanVien> listNhanVien;
    private AdapterNhanVien adapterNhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nhan_vien);
        addControls();
        readData();
    }



    private void addControls() {
        listView = findViewById(R.id.listView);
        btn_Them = findViewById(R.id.btn_themNv);
        btn_exit = findViewById(R.id.btn_exit);
        btn_timkiem= findViewById(R.id.btn_timkiem);
        listNhanVien = new ArrayList<>();
        adapterNhanVien = new AdapterNhanVien(danhSachNhanVien.this, listNhanVien);
        listView.setAdapter(adapterNhanVien);
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(danhSachNhanVien.this, themNhanVien.class);
                startActivity(myIntent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(danhSachNhanVien.this);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn có chắc muốn thoát không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btn_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentImkiem = new Intent(danhSachNhanVien.this, formTimKiem.class);
                startActivityForResult(intentImkiem,68);
            }
        });
    }
    private void readData() {
        Database_nhanVien database = new Database_nhanVien(this);
        SQLiteDatabase db = database.getDatabase();

        Cursor cursor = db.query("qlyNV", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String hoTen = cursor.getString(1);
                String namSinh = cursor.getString(2);
                String soDienThoai = cursor.getString(3);
                String chucVu = cursor.getString(4);
                String queQuan = cursor.getString(5);
                byte[] anh = cursor.getBlob(6);

                listNhanVien.add(new NhanVien(id, hoTen, namSinh, soDienThoai, chucVu, queQuan, anh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        adapterNhanVien.notifyDataSetChanged();
    }

    // menuSearch
}