package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Batch;
import us.kanddys.pov.admin.models.dtos.BatchDTO;
import us.kanddys.pov.admin.models.dtos.BatchDateDTO;
import us.kanddys.pov.admin.models.utils.CalendarDay;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.BatchJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ExceptionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ReservationJpaRepository;
import us.kanddys.pov.admin.services.BatchService;
import us.kanddys.pov.admin.services.utils.TimeUtils;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class BatchServiceImpl implements BatchService {

   @Autowired
   private BatchJpaRepository batchJpaRepository;

   @Autowired
   private ReservationJpaRepository reservationJpaRepository;

   @Autowired
   private ExceptionJpaRepository exceptionJpaRepository;

   @Override
   public Set<BatchDTO> getBatchesByCalendarId(Long calendarId, String day, String date,
         Optional<Integer> exceptionalDate) {
      List<BatchDTO> batches;
      List<BatchDateDTO> reservations;
      var dateSplitted = date.split("-");
      Date startDate;
      Date endDate;
      try {
         startDate = DateUtils.convertStringToDateWithoutTime(date);
         endDate = DateUtils.convertStringToDateWithoutTime(
               YearMonth.of(Integer.parseInt(dateSplitted[0]), Integer.parseInt(dateSplitted[1])).atEndOfMonth()
                     .toString());
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha");
      }

      batches = (exceptionalDate.isPresent()
            ? exceptionJpaRepository.findExceptionsByCalendarIdAndDateNotNull(calendarId,
                  startDate).stream().map(BatchDTO::new).toList()
            : batchJpaRepository
                  .findByCalendarIdAndDaysContaining(calendarId, CalendarDay.getDayNumber(day)).stream()
                  .map(batch -> new BatchDTO(batch)).toList());
      reservations = reservationJpaRepository.countRecordsByBatchIdsAndDate(
            batches.stream().map(BatchDTO::getId).toList(), startDate, endDate)
            .stream().map(BatchDateDTO::new).toList();
      return ((reservations.isEmpty()) ? batches
            : filterBatches(batches, reservations, date)).stream().collect(Collectors.toSet());
   }

   private List<BatchDTO> filterBatches(List<BatchDTO> batches, List<BatchDateDTO> reservations, String date) {
      List<BatchDTO> batchesFiltered = new ArrayList<BatchDTO>();
      for (int i = 0; i < batches.size(); i++) {
         BatchDTO batch = batches.get(i);
         for (BatchDateDTO reservation : reservations) {
            if (reservation.getDate().equals(date)) {
               batchesFiltered.add(batch);
            }
         }
      }
      return batches.stream().filter(batch -> !batchesFiltered.contains(batch)).toList();
   }

   @Override
   public Integer createBatch(Long calendarId, Optional<Integer> days, Optional<String> fromTime,
         Optional<String> toTime, Optional<Integer> maxLimit, Optional<String> title) {
      batchJpaRepository.save(new Batch(null, calendarId,
            (days.isPresent() ? days.get() : null),
            (fromTime.isPresent() ? TimeUtils.convertStringToTime(fromTime.get()) : null),
            (toTime.isPresent() ? TimeUtils.convertStringToTime(toTime.get()) : null),
            (maxLimit.isPresent() ? maxLimit.get() : null), (title.isPresent() ? title.get() : null)));
      return 1;
   }

   @Override
   public Integer updateBatch(Long batchId, Optional<Integer> days, Optional<String> fromTime,
         Optional<String> toTime, Optional<Integer> maxLimit, Optional<String> title) {
      var batch = batchJpaRepository.findById(batchId);
      if (batch.isPresent()) {
         var updateBatch = batch.get();
         updateBatch.setDays(days.isPresent() ? days.get() : updateBatch.getDays());
         updateBatch.setFrom(fromTime.isPresent() ? TimeUtils.convertStringToTime(fromTime.get())
               : updateBatch.getFrom());
         updateBatch.setTo(toTime.isPresent() ? TimeUtils.convertStringToTime(toTime.get()) : updateBatch.getTo());
         updateBatch.setLimit(maxLimit.isPresent() ? maxLimit.get() : updateBatch.getLimit());
         updateBatch.setTitle(title.isPresent() ? title.get() : updateBatch.getTitle());
         batchJpaRepository.save(updateBatch);
         return 1;
      }
      return 0;
   }

   @Override
   public Integer deleteBatch(Long batchId) {
      batchJpaRepository.deleteById(batchId);
      return 1;
   }
}
