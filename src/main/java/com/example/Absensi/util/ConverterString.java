package com.example.Absensi.util;

import java.text.DateFormatSymbols;

public class ConverterString {
    public String convertTime(String input){
        String result, hh, mm, ss;
        String[] splits = input.split("\\.");

        hh = splits[0];
        mm = splits[1];
        ss = splits[2];

        result = hh.concat(":").concat(mm).concat(":").concat(ss);

        return result;
    }

    public String convertDate(String input){
        String result, day, mon, monFormat, year;
        String[] split = input.split("-");

        year = split[0];
        mon = getMonth(Integer.parseInt(split[1]));
        monFormat = mon.substring(0,2);
        day = split[2];

        result = day.concat(" ").concat(monFormat).concat(" ").concat(year);

        return result;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    public int countChar(String input){
        return input.length();
    }
}
