package org.example.Utils;

import java.sql.Timestamp;

public class DateUtils {

    public static String getEpoch() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
    }
}
