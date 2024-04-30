package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Batch;
import us.kanddys.pov.admin.models.Exception;
import us.kanddys.pov.admin.models.utils.DateUtils;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class BatchDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Long calendarId;
   @JsonProperty
   private Integer limit;
   @JsonProperty
   private String date;
   @JsonProperty
   private String from;
   @JsonProperty
   private String to;
   @JsonProperty
   private String title;
   @JsonProperty
   private Long exceptionId;

   public BatchDTO() {
   }

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.0
    */
   public BatchDTO(Batch batch) {
      super();
      this.id = batch.getId();
      this.calendarId = (batch.getCalendarId() == null) ? null : batch.getCalendarId();
      this.from = (batch.getFrom() == null) ? null : batch.getFrom().toString();
      this.to = (batch.getTo() == null) ? null : batch.getTo().toString();
      this.title = (batch.getTitle() == null) ? null : batch.getTitle();
      this.limit = (batch.getLimit() == null) ? null : batch.getLimit();
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public BatchDTO(Exception exception) {
      super();
      this.id = null;
      this.exceptionId = (exception.getId() == null) ? null : exception.getId();
      this.calendarId = (exception.getCalendar() == null) ? null : exception.getCalendar();
      this.from = (exception.getFromValue() == null) ? null : exception.getFromValue().toString();
      this.to = (exception.getToValue() == null) ? null : exception.getToValue().toString();
      this.title = (exception.getTitle() == null) ? null : exception.getTitle();
      this.date = (exception.getDate() == null) ? null : DateUtils.convertDateToStringWithoutTime(exception.getDate());
      this.limit = (exception.getMaxLimit() == null) ? null : exception.getMaxLimit();
   }
}
