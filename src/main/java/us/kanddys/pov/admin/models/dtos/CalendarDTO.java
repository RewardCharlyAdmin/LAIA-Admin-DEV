package us.kanddys.pov.admin.models.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class CalendarDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Integer delay;
   @JsonProperty
   private String typeDelay;
   @JsonProperty
   private Set<String> disabledDates;
   @JsonProperty
   private Set<String> workingDays;
   @JsonProperty
   private Set<String> exceptionsDates;

   public CalendarDTO() {
   }
}
