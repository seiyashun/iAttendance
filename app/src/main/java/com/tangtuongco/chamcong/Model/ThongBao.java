package com.tangtuongco.chamcong.Model;

import java.util.Date;

public class ThongBao {
    public String notiTitle;
    public String notiId;
    public String notiUser;
    public Date notiDate;
    public String notiMess;

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }

    public String getNotiUser() {
        return notiUser;
    }

    public void setNotiUser(String notiUser) {
        this.notiUser = notiUser;
    }

    public Date getNotiDate() {
        return notiDate;
    }

    public void setNotiDate(Date notiDate) {
        this.notiDate = notiDate;
    }

    public String getNotiMess() {
        return notiMess;
    }

    public void setNotiMess(String notiMess) {
        this.notiMess = notiMess;
    }

    public ThongBao() {

    }

    public ThongBao(String notiTitle, String notiId, String notiUser, Date notiDate, String notiMess) {

        this.notiTitle = notiTitle;
        this.notiId = notiId;
        this.notiUser = notiUser;
        this.notiDate = notiDate;
        this.notiMess = notiMess;
    }
}
