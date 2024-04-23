package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.ProductMedia;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class ProductMediaDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Long product;
   @JsonProperty
   private String url;
   @JsonProperty
   private String type;

   public ProductMediaDTO() {
   }

   /**
    * Constructor personalizado que se utiliza en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.1
    * @param productMedia
    */
   public ProductMediaDTO(ProductMedia productMedia) {
      super();
      this.id = (productMedia.getId() == null ? null : productMedia.getId());
      this.product = (productMedia.getProduct() == null ? null : productMedia.getProduct());
      this.url = (productMedia.getUrl() == null ? null : productMedia.getUrl());
      this.type = (productMedia.getType() == null ? null : productMedia.getType());
   }
}
