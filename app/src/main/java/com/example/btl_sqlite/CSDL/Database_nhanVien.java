package com.example.btl_sqlite.CSDL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database_nhanVien {
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "NhanVien.db";
    private Context context;
    private SQLiteDatabase database;

    public Database_nhanVien(Context context) {
        this.context = context;
        database = openDatabase();
    }

    private SQLiteDatabase openDatabase() {
        String dbPath = context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        File file = new File(dbPath);
        if (!file.exists()) {
            try {
                copyDatabaseFromAsset();
                Toast.makeText(context, "Copy thành công database từ asset", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDatabaseFromAsset() throws IOException {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
