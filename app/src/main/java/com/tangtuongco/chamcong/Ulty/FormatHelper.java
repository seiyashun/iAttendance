package com.tangtuongco.chamcong.Ulty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

    public static String formatNgay(Date ngay){
        return sdf.format(ngay);
    }
    public static String formatGio(Date gio){ return  time.format(gio);}

    public static Date formatstring (String ngay) throws Exception
    {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngay);
        return date;
    }
    public static Date formatstringtime(String time) throws Exception
    {
        Date date = new SimpleDateFormat("HH:mm:ss").parse(time);
        return date;
    }

}
