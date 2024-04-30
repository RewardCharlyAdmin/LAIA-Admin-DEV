package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.enums.ManufacturingTypeEnum;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class ProductDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Long merchant;
   @JsonProperty
   private String frontPage;
   @JsonProperty
   private String title;
   @JsonProperty
   private Double price;
   @JsonProperty
   private Integer stock;
   @JsonProperty
   private StockTypeEnum stockType;
   @JsonProperty
   private ManufacturingTypeEnum manufacturingType;
   @JsonProperty
   private Integer manufacturing;
   @JsonProperty
   private String createAt;
   @JsonProperty
   private String updatedAt;
   @JsonProperty
   private Integer status;
   @JsonProperty
   private Integer sales;

   public ProductDTO() {
   }

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param product
    */
   public ProductDTO(Product product) {
      super();
      this.id = (product.getId() != null ? product.getId() : null);
      this.merchant = (product.getMerchant() != null ? product.getMerchant() : null);
      this.frontPage = (product.getFrontPage() != null ? product.getFrontPage() : null);
      this.title = (product.getTitle() != null ? product.getTitle() : null);
      this.price = (product.getPrice() != null ? product.getPrice() : null);
      this.stock = (product.getStock() != null ? product.getStock() : null);
      this.stockType = (product.getStockType() != null ? product.getStockType() : null);
      this.manufacturingType = (product.getManufacturingType() != null ? product.getManufacturingType() : null);
      this.manufacturing = (product.getManufacturing() != null ? product.getManufacturing() : null);
      this.createAt = (product.getCreated() != null ? DateUtils.convertDateToString(product.getCreated()) : null);
      this.updatedAt = (product.getUpdated() != null ? DateUtils.convertDateToString(product.getUpdated()) : null);
      this.status = (product.getStatus() != null ? product.getStatus() : null);
      this.sales = (product.getSales() != null ? product.getSales() : null);
   }
}
