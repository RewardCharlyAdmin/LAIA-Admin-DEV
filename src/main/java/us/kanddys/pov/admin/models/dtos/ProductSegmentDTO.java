package us.kanddys.pov.admin.models.dtos;

import java.io.IOException;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.ProductSegment;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class ProductSegmentDTO {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @Column(name = "product_id")
   private Long productId;
   @Column(name = "title")
   private String title;
   @Column(name = "description")
   private String description;
   @Column(name = "url")
   private String url;
   @Column(name = "type")
   private String type;

   public ProductSegmentDTO() {
   }

   /**
    * Constructor personalizado se implementa en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.1
    * @throws IOException
    */
   public ProductSegmentDTO(ProductSegment ProductSegment) throws IOException {
      this.id = (ProductSegment.getId() == null) ? null : ProductSegment.getId();
      this.title = (ProductSegment.getTitle() != null) ? ProductSegment.getTitle() : null;
      this.description = (ProductSegment.getDescription() != null) ? ProductSegment.getDescription() : null;
      this.productId = (ProductSegment.getProductId() != null) ? ProductSegment.getProductId() : null;
      this.url = (ProductSegment.getUrl() != null) ? ProductSegment.getUrl() : null;
      this.type = (ProductSegment.getType() != null) ? ProductSegment.getType() : null;
   }
}
