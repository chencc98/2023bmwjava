package com.example.pipeline.inbound;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.pipeline.utils.MyUtils;

public class PlaceHolder {
    private static String ASOFDATE_KEY = "${asofDate}";
    private static String ASOFDATESTR_KEY = "${asofDateStr}";
    public static List<String> getSupportKeys() {
        List<String> list = new ArrayList<>();
        list.add(ASOFDATE_KEY);
        list.add(ASOFDATESTR_KEY);
        return list;
    }

    public static String replaceKeyWith(String key) {
        if (key.equals(ASOFDATE_KEY)) {
            return ":asofDate";
        }
        if (key.equals(ASOFDATESTR_KEY)) {
            return ":asofDateStr";
        }
        return key;
    }

    public static Object replaceValue(String key) {
        if (key.equals(ASOFDATE_KEY)) {
            return new Date();
        }
        if (key.equals(ASOFDATESTR_KEY)) {
            return MyUtils.dateToString(new Date());
        }
        return key;
    }
}
