package vn.edu.vitacademy.common.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeHelper {

  private static Logger LOGGER = LoggerFactory.getLogger(DateTimeHelper.class);
  public static String formatCurrentDateAs(String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }

  public static long calculateDays(String startDate, String endDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date start = null;
    try {
      start = sdf.parse(startDate);
    } catch (Exception e) {
      LOGGER.error("Error parsing start date: {}. Root cause: {}", startDate, e.getMessage());
      return -1;
    }
    Date end = null;
    try {
      end = sdf.parse(endDate);
    } catch (Exception e) {
      LOGGER.error("Error parsing end date: {}. Root cause: {}", startDate, e.getMessage());
      return -1;
    }
    long diff = end.getTime() - start.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
  }

}
