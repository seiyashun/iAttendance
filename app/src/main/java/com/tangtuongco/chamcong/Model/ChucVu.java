package com.tangtuongco.chamcong.Model;



public class ChucVu {
    public String idChucVu;
    public String tenChucVu;
    public double hesoluong;


    public ChucVu(String idChucVu, String tenChucVu, double hesoluong) {

        this.idChucVu = idChucVu;
        this.tenChucVu = tenChucVu;
        this.hesoluong = hesoluong;
    }

    public String getIdChucVu() {

        return idChucVu;
    }

    public void setIdChucVu(String idChucVu) {
        this.idChucVu = idChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public double getHesoluong() {
        return hesoluong;
    }

    public void setHesoluong(double hesoluong) {
        this.hesoluong = hesoluong;
    }

    public ChucVu() {

    }

    @Override
    public String toString() {
        return tenChucVu;
    }




}
