package us.kanddys.pov.admin.models.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import us.kanddys.pov.admin.models.ProductSegmentShort;

/**
 * @author Igirod0
 * @version 1.0.0
 */

public class ProductSegmentShortDTO {
   @JsonProperty
   private Long productId;
   @JsonProperty
   private Integer stock;
   @JsonProperty
   private List<ProductMediaDTO> images;

   public ProductSegmentShortDTO() {
   };

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param ProductSegmentShort
    */
   public ProductSegmentShortDTO(ProductSegmentShort ProductSegmentShort) {
      super();
      this.productId = (ProductSegmentShort.getProductId() == null) ? null : ProductSegmentShort.getProductId();
      this.stock = (ProductSegmentShort.getStock() == null) ? null : ProductSegmentShort.getStock();
      this.images = (ProductSegmentShort.getImages() == null) ? null
            : ProductSegmentShort.getImages().stream().map(ProductMediaDTO::new).toList();
   }
}
