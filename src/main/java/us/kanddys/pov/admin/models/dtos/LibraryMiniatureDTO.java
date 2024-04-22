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
public class LibraryMiniatureDTO {
   @JsonProperty
   private String title;
   @JsonProperty
   private String[] miniatureProps;
   @JsonProperty
   private String[] miniatureHeader;
   @JsonProperty
   private String[] miniatureTitle;
   @JsonProperty
   private String[] miniatureSubtitle;
   @JsonProperty
   private Integer operation;

   public LibraryMiniatureDTO() {
   }
}
