package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.models.dtos.CalendarDTO;
import us.kanddys.pov.admin.models.utils.CalendarDay;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.BatchJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.CalendarJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.DisabledDateJpaRepository;
import us.kanddys.pov.admin.services.CalendarService;

@Service
public class CalendarServiceImpl implements CalendarService {

   @Autowired
   private CalendarJpaRepository calendarJpaRepository;

   @Autowired
   private DisabledDateJpaRepository disabledDateJpaRepository;

   @Autowired
   private BatchJpaRepository batchJpaRepository;

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public CalendarDTO getCalendarByMerchantId(Integer year, Integer month, Integer day, Long merchantId) {
      try {
         var startDate = year.toString() + "-" + month.toString() + "-" + day.toString();
         var endDate = DateUtils.convertStringToDateWithoutTime(YearMonth.of(year, month).atEndOfMonth().toString());
         Map<String, Object> calendar = calendarJpaRepository.findTypeAndDelayAndCalendarIdByMerchantId(merchantId);
         var calendarId = Long.valueOf((Integer) calendar.get("id"));
         return new CalendarDTO(calendarId, (Integer) calendar.get("delay"), (String) calendar.get("type"),
               disabledDateJpaRepository.findDisabedDatesByCalendarIdRange(
                     DateUtils.convertStringToDateWithoutTime(startDate), endDate, calendarId),
               CalendarDay.getDays(batchJpaRepository.findDaysByCalendarId(calendarId)),
               batchJpaRepository.findExceptionBatchesDatesByCalendarIdAndDateNotNull(calendarId,
                     DateUtils.convertStringToDateWithoutTime(startDate), endDate));
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha");
      }
   }

}
