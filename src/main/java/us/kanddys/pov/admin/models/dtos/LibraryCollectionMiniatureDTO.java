package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LibraryCollectionMiniatureDTO {
   @JsonProperty
   private String title;
   @JsonProperty
   private Integer operation;
   @JsonProperty("collection")
   private CollectionMiniatureDTO collectionMiniature;

   public LibraryCollectionMiniatureDTO() {
   }
}
