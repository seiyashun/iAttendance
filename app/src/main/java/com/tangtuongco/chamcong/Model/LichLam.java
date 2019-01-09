package com.tangtuongco.chamcong.Model;

public class LichLam {
    private String MaNV;
    private String CaLam;
    private String NgayLam;

    public LichLam() {
    }

    public String getMaNV() {

        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getCaLam() {
        return CaLam;
    }

    public void setCaLam(String caLam) {
        CaLam = caLam;
    }

    public String getNgayLam() {
        return NgayLam;
    }

    public void setNgayLam(String ngayLam) {
        NgayLam = ngayLam;
    }

    public LichLam(String maNV, String caLam, String ngayLam) {
        MaNV = maNV;
        CaLam = caLam;
        NgayLam = ngayLam;
    }
}
