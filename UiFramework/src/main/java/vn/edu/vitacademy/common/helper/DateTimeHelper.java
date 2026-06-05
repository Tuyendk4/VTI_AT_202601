package vn.edu.vitacademy.common.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

  public static String formatCurrentDateAs(String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }
}
