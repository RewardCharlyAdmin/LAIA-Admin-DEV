package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Optional;

import us.kanddys.pov.admin.models.dtos.BatchDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface BatchService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public List<BatchDTO> getBatchesByCalendarId(Long calendarId, String day, String date,
         Optional<Integer> exceptionalDate);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer createBatch(Long calendarId, Optional<Integer> days, Optional<String> date, Optional<String> fromTime,
         Optional<String> toTime, Optional<Integer> maxLimit, Optional<String> title);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer updateBatch(Long batchId, Optional<Integer> days, Optional<String> date, Optional<String> fromTime,
         Optional<String> toTime, Optional<Integer> maxLimit, Optional<String> title);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer deleteBatch(Long batchId);
}
