package com.example.btl_sqlite;

public class NhanVien {
    public int id;
    public String hoTen;
    public String namSinh;
    public String soDienThoai;
    public String chucVu;
    public String queQuan;
    public byte[] hinhAnh;

    public NhanVien(int id, String hoTen, String namSinh, String soDienThoai, String chucVu, String queQuan, byte[] hinhAnh) {
        this.id = id;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.queQuan = queQuan;
        this.hinhAnh = hinhAnh;
    }
}
