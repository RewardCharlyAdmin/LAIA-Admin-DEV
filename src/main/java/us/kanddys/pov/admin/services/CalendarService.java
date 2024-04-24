package us.kanddys.pov.admin.services;

import us.kanddys.pov.admin.models.dtos.CalendarDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface CalendarService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public CalendarDTO getCalendarByMerchantId(Integer year, Integer month, Integer day, Long merchantId);
}
