package com.wingmedia.utils.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alan's on 3/21/2017.
 */

public class StringUtils {
  private static final String EMAIL_PATTERN =
      "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  public static boolean validateEmail(final String hex) {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(hex);
    return matcher.matches();
  }

  public static void formatPhone(String strLocale, String string) {
    Locale locale = new Locale(strLocale);
    final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(locale);
    //Format our data to two decimal places for brightness change.
    decimalFormat.format(string);
  }

  public static String getPhoneNumber(String s) {
    StringBuffer sBuffer = new StringBuffer();
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(s);
    while (m.find()) {
      sBuffer.append(m.group());
    }
    return sBuffer.toString();
  }
}
