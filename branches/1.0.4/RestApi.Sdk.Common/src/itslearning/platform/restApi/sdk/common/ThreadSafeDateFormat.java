package itslearning.platform.restApi.sdk.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Wraps SimpleDateFormat for thread safety
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class ThreadSafeDateFormat  {
private SimpleDateFormat sdf;

 public ThreadSafeDateFormat(String format) {
     this.sdf = new SimpleDateFormat(format);
     //this.sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
 }

 public void setTimeZone(TimeZone tz){
     this.sdf.setTimeZone(tz);
 }

 public synchronized String format(Date date) {
     return this.sdf.format(date);
 }

 public synchronized Date parse(String string) throws ParseException {
     return this.sdf.parse(string);
 }
}