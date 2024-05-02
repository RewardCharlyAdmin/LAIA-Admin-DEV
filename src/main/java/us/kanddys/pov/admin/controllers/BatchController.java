package us.kanddys.pov.admin.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.BatchDTO;
import us.kanddys.pov.admin.services.BatchService;

@Controller
public class BatchController {

   @Autowired
   private BatchService batchService;

   @QueryMapping("gBatches")
   public Set<BatchDTO> getBatchesByCalendarId(@Argument Long calendarId, @Argument String day, @Argument String date,
         @Argument Optional<Integer> exceptionalDate) {
      return batchService.getBatchesByCalendarId(calendarId, day, date, exceptionalDate);
   }

   @MutationMapping("cBatch")
   public Integer createBatch(@Argument Long calendarId, @Argument Optional<Integer> days,
         @Argument Optional<String> fromTime, @Argument Optional<String> toTime,
         @Argument Optional<Integer> maxLimit, @Argument Optional<String> title) {
      return batchService.createBatch(calendarId, days, fromTime, toTime, maxLimit, title);
   }

   @MutationMapping("uBatch")
   public Integer updateBatch(@Argument Long batchId, @Argument Optional<Integer> days,
         @Argument Optional<String> fromTime, @Argument Optional<String> toTime, @Argument Optional<Integer> maxLimit,
         @Argument Optional<String> title) {
      return batchService.updateBatch(batchId, days, fromTime, toTime, maxLimit, title);
   }

   @MutationMapping("dBatch")
   public Integer deleteBatch(@Argument Long batchId) {
      return batchService.deleteBatch(batchId);
   }
}
