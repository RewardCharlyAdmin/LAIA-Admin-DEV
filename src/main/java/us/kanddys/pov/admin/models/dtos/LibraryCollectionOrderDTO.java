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
public class LibraryCollectionOrderDTO {
   @JsonProperty
   private String title;
   @JsonProperty
   private Integer ascDsc;
   @JsonProperty
   private Integer operation;
   @JsonProperty
   private Set<String> orderProps;

   public LibraryCollectionOrderDTO() {
   }
}
