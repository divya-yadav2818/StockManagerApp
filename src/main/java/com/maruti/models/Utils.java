package com.maruti.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {
    public static String formatTimestamp(Timestamp ts) {
        if(ts == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(ts);
    }
}
