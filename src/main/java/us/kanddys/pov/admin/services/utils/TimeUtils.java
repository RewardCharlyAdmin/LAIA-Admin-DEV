package us.kanddys.pov.admin.services.utils;

import java.sql.Time;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class TimeUtils {
   /**
    * Este metodo convierte una cadena de texto en una hora.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param time
    * @return Time
    */
   public static Time convertStringToTime(String time) {
      return Time.valueOf(time);
   }
}
