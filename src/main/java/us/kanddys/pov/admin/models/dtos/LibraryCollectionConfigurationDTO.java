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
public class LibraryCollectionConfigurationDTO {
   @JsonProperty
   private String title;
   @JsonProperty
   private Integer conf;
   @JsonProperty
   private Integer ascDsc;
   @JsonProperty
   private String order;
   @JsonProperty("filter")
   private LibraryCollectionFilterDTO filter;
   @JsonProperty
   private Integer operation;

   public LibraryCollectionConfigurationDTO() {
   }
}
