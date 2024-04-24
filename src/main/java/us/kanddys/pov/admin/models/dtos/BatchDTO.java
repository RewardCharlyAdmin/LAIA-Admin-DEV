package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Batch;

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
      this.date = (batch.getDate() == null) ? null : batch.getDate().toString();
      this.limit = (batch.getLimit() == null) ? null : batch.getLimit();
   }
}
