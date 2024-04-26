package us.kanddys.pov.admin.models.dtos.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class LongDTO {
   @JsonProperty
   private Long value;

   public LongDTO() {
   }
}
