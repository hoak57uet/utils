package com.wingmedia.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alan's on 3/21/2017.
 */

public class DateUtils {
  public static String toStringDate(long milisec, String pattern) {
    SimpleDateFormat df = new SimpleDateFormat(pattern);
    return df.format(milisec);
  }
  public static Date fromString(String date, String pattern) throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat(pattern);
    return df.parse(date);
  }
}
