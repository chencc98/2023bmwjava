package com.example.pipeline.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.pipeline.inbound.PullDatabaseAttr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyUtils {
    private static ObjectMapper om = new ObjectMapper();

    public static Date datePlusBySecond(Date date, int n) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }

    public static boolean isDateAroundMin(Date base, Date guess, int n) {
        long baseMs = base.getTime();
        Date minDate = datePlusBySecond(guess, n * 60 * -1);
        long minMs = minDate.getTime();
        Date maxDate = datePlusBySecond(guess, n * 60);
        long maxMs = maxDate.getTime();
        if (baseMs >= minMs && baseMs <= maxMs) {
            return true;
        }
        return false;
    }

    public static boolean isDateEarlyMin(Date base, Date guess, int n) {
        long baseMs = base.getTime();
        Date minDate = datePlusBySecond(base, n * 60 * -1);
        long minMs = minDate.getTime();
        long guessMs = guess.getTime();
        if (guessMs >= minMs && guessMs <= baseMs) {
            return true;
        }
        return false;
    }

    public static Date longToDate(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        return cal.getTime();
    }

    public static String dateToString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static String dateToFullString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(d);
    }

    public static PullDatabaseAttr strToPullDatabaseAttr(String attr) throws JsonMappingException, JsonProcessingException {
        PullDatabaseAttr t = om.readValue(attr, PullDatabaseAttr.class);
        return t;
    }

    public static String obj2Str(Object obj) throws JsonProcessingException {
        return om.writeValueAsString(obj);
    }

    public static String getFilePrefix(String file) {
        int index = file.indexOf(".");
        if (index < 0) {
            return file;
        }
        if (index == 0) {
            return "";
        }
        return file.substring(0, index);
    }

    public static String getFileSuffix(String file) {
        int index = file.indexOf(".");
        if (index < 0) {
            return "tmp";
        }
        if (index == 0) {
            return "tmp";
        }
        return file.substring(index + 1);
    }
}
