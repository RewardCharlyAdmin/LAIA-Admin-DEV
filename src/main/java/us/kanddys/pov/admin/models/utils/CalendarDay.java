package us.kanddys.pov.admin.models.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class CalendarDay {

   /**
    * Este método analiza las combinaciones de los batches y registra
    * los días que trabaja el usuario.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param startDay
    * @return List<String>
    */
   public static Set<String> getDays(Set<Integer> combinations) {
      Set<String> days = new HashSet<>();
      for (Integer combination : combinations) {
         String combinationStr = Integer.toString(combination);
         for (char ch : combinationStr.toCharArray()) {
            Integer dayNumber = Character.getNumericValue(ch);
            String day = null;
            switch (dayNumber) {
               case 1:
                  day = "SUN";
                  break;
               case 2:
                  day = "MON";
                  break;
               case 3:
                  day = "TUE";
                  break;
               case 4:
                  day = "WED";
                  break;
               case 5:
                  day = "THU";
                  break;
               case 6:
                  day = "FRI";
                  break;
               case 7:
                  day = "SAT";
                  break;
               default:
                  System.out.println("Número de día inválido: " + dayNumber);
            }
            if (day != null && !days.contains(day)) {
               days.add(day);
            }
         }
      }
      return days;
   }

   /**
    * Este método tiene la obligacion de devolver el dia correspondiente a la
    * cadena
    * de caracteres pasada por parametro.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param day
    * @return Integer
    */
   public static Integer getDayNumber(String day) {
      Integer dayNumber = null;
      switch (day) {
         case "SUN":
            dayNumber = 1;
            break;
         case "MON":
            dayNumber = 2;
            break;
         case "TUE":
            dayNumber = 3;
            break;
         case "WED":
            dayNumber = 4;
            break;
         case "THU":
            dayNumber = 5;
            break;
         case "FRI":
            dayNumber = 6;
            break;
         case "SAT":
            dayNumber = 7;
            break;
         default:
            System.out.println("Día inválido: " + day);
      }
      return dayNumber;
   }

   /**
    * Este método tiene la obligacion de devolver el dia correspondiente a la
    * cadena de caracteres pasada por parametro.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param numberDay
    * @return String
    */
   public static String getDayNumber(Integer numberDay) {
      String dayNumber = null;
      switch (numberDay) {
         case 1:
            dayNumber = "SUN";
            break;
         case 2:
            dayNumber = "MON";
            break;
         case 3:
            dayNumber = "TUE";
            break;
         case 4:
            dayNumber = "WED";
            break;
         case 5:
            dayNumber = "THU";
            break;
         case 6:
            dayNumber = "FRI";
            break;
         case 7:
            dayNumber = "SAT";
            break;
         default:
            System.out.println("Día inválido: " + numberDay);
      }
      return dayNumber;
   }
}
