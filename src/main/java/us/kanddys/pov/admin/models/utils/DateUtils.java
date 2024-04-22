package us.kanddys.pov.admin.models.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class DateUtils {
   /**
    * Convierte un String a Date con el formato yyyy-MM-dd HH:mm:ss.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param dateString
    * @return Date
    */
   public static Date convertStringToDate(String dateString) throws ParseException {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
   }

   /**
    * Convierte una fecha a una cadena de texto en el formato yyyy-MM-dd HH:mm:ss.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param date
    * @return String
    */
   public static String convertDateToString(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return dateFormat.format(date);
   }

   /**
    * Este método tiene la obligacion de devolver el String de la fecha actual
    * con el formato yyyy-MM-dd HH:mm:ss.
    *
    * @author Igirod0
    * @version 1.0.0
    * @return String
    */
   public static String getCurrentDateString() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
   }

   /**
    * Este método tiene la obligacion de devolver el String de la fecha actual
    * con el formato yyyy-MM-dd HH:mm:ss.
    *
    * @author Igirod0
    * @version 1.0.0
    * @return String
    */
   public static String getCurrentDateStringWitheoutTime() {
      return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
   }

   /**
    * Este método tiene la obligacion de devolver el String de la fecha actual
    * con el formato yyyy-MM-dd.
    *
    * @author Igirod0
    * @version 1.0.0
    * @return String
    * @throws ParseException
    */
   public static Date getCurrentDateWitheoutTime() throws ParseException {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = dateFormat.format(new Date());
      return dateFormat.parse(dateString);
   }

   /**
    * Este método tiene la obligacion de devolver la fecha actual.
    * con el formato yyyy-MM-dd HH:mm:ss.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @return Date
    * @throws ParseException
    */
   public static Date getCurrentDate() throws ParseException {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = dateFormat.format(new Date());
      return dateFormat.parse(dateString);
   }

   /**
    * Convierte un String a Date con el formato yyyy-MM-dd.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param dateString
    * @return Date
    * @throws ParseException
    */
   public static Date convertStringToDateWithoutTime(String dateString) throws ParseException {
      return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
   }

   /**
    * Convierte un objeto Date a String con el formato yyyy-MM-dd.
    *
    * @param date el objeto Date que se desea convertir
    * @return una cadena de texto que representa la fecha en formato "yyyy-MM-dd"
    */
   public static String convertDateToStringWithoutTime(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return dateFormat.format(date);
   }

   public static Date changeDateFormat(Date date) throws ParseException {
      SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String formattedDate = outputDateFormat.format(date);
      return outputDateFormat.parse(formattedDate);
   }

   /**
    * Obtiene la hora actual en formato HH:mm:ss.
    *
    * @return La hora actual en formato HH:mm:ss.
    */
   public static String getCurrentTime() {
      LocalTime currentTime = LocalTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
      return currentTime.format(formatter);
   }

   /**
    * Extrae el mes de una fecha.
    *
    * @param date la fecha de la que se desea extraer el mes
    * @return el mes de la fecha
    */
   public static Integer getMonth(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
      return Integer.parseInt(dateFormat.format(date));
   }

   public static Integer getYear(Date from) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
      return Integer.parseInt(dateFormat.format(from));
   }
}
