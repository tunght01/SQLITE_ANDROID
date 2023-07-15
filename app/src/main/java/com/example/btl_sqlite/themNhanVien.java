package com.example.btl_sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.btl_sqlite.CSDL.Database_nhanVien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class themNhanVien extends AppCompatActivity {

    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    EditText edtid, edtten, edttuoi, edtsdt, edtdiachi, edtchucvu;
    ImageView imgNv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        controls();
    }

    private void controls() {
        // button
        btnChonHinh = findViewById(R.id.btn_chonHinh);
        btnChupHinh = findViewById(R.id.btn_chupHinh);
        btnLuu = findViewById(R.id.btn_Luuupdate);
        btnHuy = findViewById(R.id.btn_huyupdate);

        // edittext
        edtid = findViewById(R.id.edt_id_sua);
        edtten= findViewById(R.id.edt_ten_sua);
        edttuoi= findViewById(R.id.edt_tuoi_sua);
        edtsdt= findViewById(R.id.edt_sdt_sua);
        edtchucvu= findViewById(R.id.edt_chucvu_sua);
        edtdiachi= findViewById(R.id.edt_diachi_sua);
        // image
        imgNv = findViewById(R.id.img_NhanVien);

        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }


        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosePicture();
            }


        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private  void insert(){
        String ten = edtten.getText().toString();
        String namsinh = edttuoi.getText().toString();
        String sodienthoai = edtsdt.getText().toString();
        String chucvu = edtchucvu.getText().toString();
        String quequan = edtdiachi.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgNv);
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", ten);
        contentValues.put("namsinh", namsinh);
        contentValues.put("sodienthoai", sodienthoai);
        contentValues.put("chucvu", chucvu);
        contentValues.put("quequan", quequan);
        contentValues.put("anh", anh);

        Database_nhanVien database = new Database_nhanVien(this);
        SQLiteDatabase db = database.getDatabase();

        String table = "qlyNV";
        db.insert(table, null,contentValues);
        Intent intent = new Intent(this, danhSachNhanVien.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(themNhanVien.this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage("Thêm nhân viên thành công!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
    private byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void takePicture() {

        Intent myIntent5 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myIntent5,2);
    }
    private void chosePicture() {
        Intent myintent4 = new Intent(Intent.ACTION_PICK);
        myintent4.setType("image/*");
        startActivityForResult(myintent4,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgNv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (requestCode == 2) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgNv.setImageBitmap(bitmap);

            }
        }
    }
}