package com.tangtuongco.chamcong.Ulty;

public class CheckEditext {
    public static boolean isEmail(String email)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.matches(emailPattern) && email.length()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean isEmpty(String s)
    {
        if(s.length()==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
