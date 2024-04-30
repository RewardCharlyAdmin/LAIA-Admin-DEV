package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class ExceptionDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Long calendar;
   @JsonProperty
   private String fromValue;
   @JsonProperty
   private String toValue;
   @JsonProperty
   private Integer maxLimit;
   @JsonProperty
   private String title;

   public ExceptionDTO() {
   }

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    * 
    * @param exception
    * @version 1.0.0
    */
   public ExceptionDTO(us.kanddys.pov.admin.models.Exception exception) {
      super();
      this.id = exception.getId();
      this.calendar = exception.getCalendar();
      this.fromValue = (exception.getFromValue() != null) ? exception.getFromValue().toString() : null;
      this.toValue = (exception.getToValue() != null) ? exception.getToValue().toString() : null;
      this.maxLimit = (exception.getMaxLimit() != null) ? exception.getMaxLimit() : null;
      this.title = (exception.getTitle() != null) ? exception.getTitle() : null;
   }
}
