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
public class CreateCollectionBodyDTO {
   @JsonProperty("libraryId")
   private Long libraryId;
   @JsonProperty("userId")
   private Long userId;
   @JsonProperty("collectionId")
   private Long collectionId;

   public CreateCollectionBodyDTO() {
   }
}
