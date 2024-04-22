package us.kanddys.pov.admin.models.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class NewProductDTO {
   @JsonProperty
   private Long productId;
   @JsonProperty
   private List<ProductImageDTO> medias;
   @JsonProperty
   private ProductImageDTO segmentMedia;

   public NewProductDTO() {
   }
}
