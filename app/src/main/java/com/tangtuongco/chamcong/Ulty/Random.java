package com.tangtuongco.chamcong.Ulty;

public class Random {

    public static int getRandomDoubleBetweenRange(double min, double max){
        int x = (int) ((Math.random()*((max-min)+1))+min);
        return x;
    }
}
