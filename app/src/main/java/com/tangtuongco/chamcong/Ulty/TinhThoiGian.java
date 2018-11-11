package com.tangtuongco.chamcong.Ulty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TinhThoiGian {


    public static String GioRaTruGioVao (String giovao,String giora) throws ParseException {
        DateFormat df=new SimpleDateFormat("HH:mm:ss");
        Date d1=df.parse(giovao);
        Date d2=df.parse(giora);
        long diff= d2.getTime()-d1.getTime();
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
                TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
                TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));
        return hms;
    }
}
