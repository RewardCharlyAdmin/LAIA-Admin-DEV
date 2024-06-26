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
public class CollectionMiniatureDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private String[] miniatureProps;
   @JsonProperty
   private String[] miniatureHeader;
   @JsonProperty
   private String[] miniatureSubtitle;
   @JsonProperty
   private String[] miniatureTitle;

   public CollectionMiniatureDTO() {
   }
}
