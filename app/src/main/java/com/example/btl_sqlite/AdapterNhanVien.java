package com.example.btl_sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_sqlite.CSDL.Database_nhanVien;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
Context context;
ArrayList<NhanVien> listNhanVien;

    public AdapterNhanVien(Context context, ArrayList<NhanVien> listNhanVien) {
        this.context = context;
        this.listNhanVien = listNhanVien;
    }

    @Override
    public int getCount() {
        return listNhanVien.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row, null);
        }

        ImageView imgAnhDaiDien = view.findViewById(R.id.iimgAnhDaiDien);
        TextView tvId = view.findViewById(R.id.tv_idNhanVien);
        TextView tvhoTen = view.findViewById(R.id.tv_hotenNhanVien);
        TextView tvNamSinh = view.findViewById(R.id.tv_namSinhNhanVien);
        TextView tvSdt = view.findViewById(R.id.tv_soDienThoaiNV);
        TextView tvQueQuan = view.findViewById(R.id.tv_queQuanNhanVien);
        TextView tvChucVu = view.findViewById(R.id.tv_chucVuNV);
        Button btnXoa = view.findViewById(R.id.btn_xoa);
        Button btnSua = view.findViewById(R.id.btn_sua);


        NhanVien nhanVien = listNhanVien.get(i);
        tvId.setText("Id: "+nhanVien.id);
        tvhoTen.setText("Name: "+nhanVien.hoTen);
        tvNamSinh.setText("Age: "+nhanVien.namSinh);
        tvSdt.setText("Number: "+nhanVien.soDienThoai);
        tvChucVu.setText("Position: "+nhanVien.chucVu);
        tvQueQuan.setText("Address: "+nhanVien.queQuan);
        Bitmap bmAnhDaiDien = BitmapFactory.decodeByteArray(nhanVien.hinhAnh, 0, nhanVien.hinhAnh.length);
        imgAnhDaiDien.setImageBitmap(bmAnhDaiDien);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, suaNhanVien.class);
                myIntent.putExtra("id", nhanVien.id);
                context.startActivity(myIntent);

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn có chắc muốn xoá nhân viên này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(nhanVien.id);
                        Toast.makeText(context,"Xoá nhân viên thành công!",Toast.LENGTH_SHORT).show();

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

        return view;
    }
    private void delete(int idNhanVien){

        Database_nhanVien database = new Database_nhanVien(context);
        SQLiteDatabase db =database.getDatabase();


        String table = "qlyNV";

        db.delete(table, "id = ?", new String[]{String.valueOf(idNhanVien)});

        listNhanVien.clear();
        // xoá thì phải cập nhật
        Cursor c = db.rawQuery("SELECT * FROM qlyNV",null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String hoten = c.getString(1);
            String namsinh = c.getString(2);
            String sodienthoai = c.getString(3);
            String chucvu = c.getString(4);
            String quequan = c.getString(5);
            byte[] anh = c.getBlob(6);
            listNhanVien.add(new NhanVien(id,hoten,namsinh,sodienthoai,chucvu,quequan,anh));
        }
        notifyDataSetChanged();


    }
}
