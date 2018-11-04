package com.tangtuongco.chamcong.Model;

import java.util.ArrayList;
import java.util.Date;

public class TinNhan {
    public String messText;
    public String messUser;
    public Date messDate;
    ArrayList<TinNhan> dsTinNhat;

    public TinNhan() {
    }

    public String getMessText() {

        return messText;
    }

    public void setMessText(String messText) {
        this.messText = messText;
    }

    public String getMessUser() {
        return messUser;
    }

    public void setMessUser(String messUser) {
        this.messUser = messUser;
    }

    public Date getMessDate() {
        return messDate;
    }

    public void setMessDate(Date messDate) {
        this.messDate = messDate;
    }

    public ArrayList<TinNhan> getDsTinNhat() {
        return dsTinNhat;
    }

    public void setDsTinNhat(ArrayList<TinNhan> dsTinNhat) {
        this.dsTinNhat = dsTinNhat;
    }

    public TinNhan(String messText, String messUser, Date messDate, ArrayList<TinNhan> dsTinNhat) {

        this.messText = messText;
        this.messUser = messUser;
        this.messDate = messDate;
        this.dsTinNhat = dsTinNhat;
    }

}
