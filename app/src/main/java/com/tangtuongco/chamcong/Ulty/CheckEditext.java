package com.tangtuongco.chamcong.Ulty;

public class CheckEditext {
    public static boolean isEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String s) {
        if (s.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTime(String s) {


        String vitri3 = String.valueOf(s.charAt(2));
        String vitri5 = String.valueOf(s.charAt(5));

        if (s.length() != 8) {
            return false;
        } else {
            if (!vitri3.equals(":")) {
                return false;
            } else if (!vitri5.equals(":")) {
                return false;
            } else {
                String hh, mm, ss;
                hh = s.substring(0, 2);
                mm = s.substring(3, 5);
                ss = s.substring(6, 8);
                int HH, MM, SS;
                HH = Integer.valueOf(hh);
                MM = Integer.valueOf(mm);
                SS = Integer.valueOf(ss);
                if (0 < HH && HH > 24) {
                    return false;
                } else if (0 < MM && MM >= 60) {
                    return false;
                } else if (0 < SS && SS >= 60) {
                    return false;
                }
                return true;
            }
        }


    }

}
