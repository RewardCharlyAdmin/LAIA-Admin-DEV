package us.kanddys.pov.admin.services.impl;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Batch;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.dtos.InvoiceReservationDTO;
import us.kanddys.pov.admin.models.utils.CalendarDay;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.BatchJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.CalendarJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.DisabledDateJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.services.InvoiceReservationService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class InvoiceReservationServiceImpl implements InvoiceReservationService {

   @Autowired
   private DisabledDateJpaRepository disabledDateJpaRepository;

   @Autowired
   private BatchJpaRepository batchJpaRepository;

   @Autowired
   private CalendarJpaRepository calendarJpaRepository;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Override
   public InvoiceReservationDTO getInvoiceReservation(Long merchantId, Set<Long> articlesIds) {
      Map<String, Object> calendarAtributtes = calendarJpaRepository
            .findTypeAndDelayAndCalendarIdByMerchantId(merchantId);
      Date actuallyDate;
      Date endDate;
      Calendar calendar;
      Set<String> disabledDates;
      Long batchId = null;
      Time from = null;
      Time to = null;
      Set<Batch> batches = new HashSet<Batch>();
      Set<String> exceptionalDates;
      Long calendarId = Long.valueOf(calendarAtributtes.get("id").toString());
      try {
         actuallyDate = DateUtils.getCurrentDate();
         calendar = Calendar.getInstance();
         String[] dateActuallySplit = DateUtils.convertDateToStringWithoutTime(calendar.getTime()).split("-");
         calendar.setTime(actuallyDate);
         endDate = DateUtils.convertStringToDateWithoutTime(
               YearMonth.of(Integer.valueOf(dateActuallySplit[0]), Integer.valueOf(dateActuallySplit[1])).atEndOfMonth()
                     .toString());
         // ! Suma el mayor tiempo de fabricación de la lista de productos en caso de
         // ! que la misma no sea nula.
         if (articlesIds != null) {
            sumeMaxManufacturingTime(articlesIds, calendar);
         }
         disabledDates = disabledDateJpaRepository.findDisabedDatesByCalendarIdRange(
               DateUtils.changeDateFormat(actuallyDate),
               endDate, calendarId);
         // ! Evaluar si la fecha de envío es una fecha deshabilitada.
         while (disabledDates.contains(DateUtils.convertDateToStringWithoutTime(calendar.getTime()))) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
         }
         exceptionalDates = batchJpaRepository.findExceptionBatchesDatesByCalendarIdAndDateNotNull(calendarId,
               DateUtils.convertStringToDateWithoutTime(DateUtils.convertDateToStringWithoutTime(calendar.getTime())),
               endDate);
         // ! Evaluar si la fecha de envío es una fecha excepcional.
         if (exceptionalDates.contains(DateUtils.convertDateToStringWithoutTime(calendar.getTime()))) {
            batches = batchJpaRepository.findExceptionlBatchesByCalendarId(calendarId, actuallyDate, endDate);
         } else {
            batches = batchJpaRepository.findNormalBatchesByCalendarId(calendarId);
         }
      } catch (NumberFormatException e) {
         throw new RuntimeException("Error al convertir la fecha");
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha");
      }
      Map<String, Object> batchData = settingBatchAtributes(calendarAtributtes.get("type").toString(), calendar,
            Integer.valueOf(calendarAtributtes.get("delay").toString()), batches, batchId, from, to);
      return new InvoiceReservationDTO(Long.valueOf(batchData.get("batchId").toString()),
            batchData.get("from").toString(), batchData.get("to").toString(),
            DateUtils.convertDateToStringWithoutTime(calendar.getTime()), calendarAtributtes.get("type").toString(),
            Integer.valueOf(calendarAtributtes.get("delay").toString()));
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void sumeMaxManufacturingTime(Set<Long> productIds, Calendar calendar) {
      var manufacturingTime = 0;
      var manufacturingType = "";
      Set<Product> manufacturingProducts = productJpaRepository.findAllByProductIds(productIds);
      for (Product manufacturingProduct : manufacturingProducts) {
         if (manufacturingProduct.getManufacturingType().toString().equals("MH")) {
            if (manufacturingTime < manufacturingProduct.getManufacturing()) {
               manufacturingTime = manufacturingProduct.getManufacturing();
               manufacturingType = manufacturingProduct.getManufacturingType().toString();
            }
         }
      }
      if (manufacturingTime == 0 && manufacturingType.equals("")) {
         for (Product manufacturingProduct : manufacturingProducts) {
            if (manufacturingProduct.getManufacturingType().toString().equals("DY")) {
               if (manufacturingTime < manufacturingProduct.getManufacturing()) {
                  manufacturingTime = manufacturingProduct.getManufacturing();
                  manufacturingType = manufacturingProduct.getManufacturingType().toString();
               }
            }
         }
      }
      if (manufacturingTime == 0 && manufacturingType.equals("")) {
         for (Product manufacturingProduct : manufacturingProducts) {
            if (manufacturingProduct.getManufacturingType().toString().equals("HR")) {
               if (manufacturingTime < manufacturingProduct.getManufacturing()) {
                  manufacturingTime = manufacturingProduct.getManufacturing();
                  manufacturingType = manufacturingProduct.getManufacturingType().toString();
               }
            }
         }
      }
      if (manufacturingTime == 0 && manufacturingType.equals("")) {
         for (Product manufacturingProduct : manufacturingProducts) {
            if (manufacturingProduct.getManufacturingType().toString().equals("MN")) {
               if (manufacturingTime < manufacturingProduct.getManufacturing()) {
                  manufacturingTime = manufacturingProduct.getManufacturing();
                  manufacturingType = manufacturingProduct.getManufacturingType().toString();
               }
            }
         }
      }
      switch (manufacturingType) {
         case "MH":
            calendar.add(Calendar.MONTH, manufacturingTime);
            break;
         case "DY":
            calendar.add(Calendar.DAY_OF_YEAR, manufacturingTime);
            break;
         case "HR":
            calendar.add(Calendar.HOUR, manufacturingTime);
            break;
         case "MN":
            calendar.add(Calendar.MINUTE, manufacturingTime);
            break;
      }
   }

   /**
    * Este método se encarga de setear los datos del batch disponible en cuanto
    * a la fecha actual, depediendo del tipo y delay del calendario.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param type
    * @param calendar
    * @param delay
    * @param batches
    * @param batchId
    * @param from
    * @param to
    */
   private Map<String, Object> settingBatchAtributes(String type, Calendar calendar, Integer delay, Set<Batch> batches,
         Long batchId,
         Time from, Time to) {
      Set<String> workingDays = CalendarDay.getDays(batches.stream().map(Batch::getDays).collect(Collectors.toSet()));
      if (type.equals("HR") || type.equals("MN")) {
         if (type.equals("HR"))
            calendar.add(Calendar.HOUR, delay);
         else
            calendar.add(Calendar.MINUTE, delay);
         for (Batch batch : batches) {
            LocalTime actuallyDateTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY),
                  calendar.get(Calendar.MINUTE),
                  calendar.get(Calendar.SECOND));
            if (batch.getFrom().toLocalTime().isBefore(actuallyDateTime)
                  && batch.getTo().toLocalTime().isAfter(actuallyDateTime)
                  &&
                  String.valueOf(batch.getDays()).contains(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)))) {
               return Map.of("batchId", batch.getId(), "from", batch.getFrom(), "to",
                     batch.getTo());
            }
         }
         if (batchId == null) {
            for (String day : workingDays) {
               if (!day.equals(CalendarDay.getDayNumber(calendar.get(Calendar.DAY_OF_WEEK)))) {
                  calendar.add(Calendar.DAY_OF_YEAR, 1);
                  break;
               }
            }
            for (Batch batch : batches) {
               if (batch.getDays().toString().contains(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)))) {
                  return Map.of("batchId", batch.getId(), "from", batch.getFrom(), "to",
                        batch.getTo());
               }
            }
         }
      }
      if (type.equals("DY")) {
         calendar.add(Calendar.DAY_OF_YEAR, delay);
         for (Batch batch : batches) {
            if ((batch.getDays().toString().contains(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK))))) {
               return Map.of("batchId", batch.getId(), "from", batch.getFrom(), "to",
                     batch.getTo());
            }
         }
      }
      if (type.equals("MH")) {
         calendar.add(Calendar.MONTH, delay);
         for (Batch batch : batches) {
            if ((batch.getDays().toString().contains(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK))))) {
               return Map.of("batchId", batch.getId(), "from", batch.getFrom(), "to",
                     batch.getTo());
            }
         }
      }
      return Map.of("batchId", 0, "from", "null", "to", "null");
   }
}
