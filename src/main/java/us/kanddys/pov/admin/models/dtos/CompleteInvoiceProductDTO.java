package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import us.kanddys.pov.admin.models.InvoiceProduct;

public class CompleteInvoiceProductDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private Integer productCount;
   @JsonProperty
   private String productTitle;
   @JsonProperty
   private Double productPrice;
   @JsonProperty
   private String productUnit;
   @JsonProperty
   private String productMedia;

   public CompleteInvoiceProductDTO() {
   }

   /**
    * Constructor personlizado utilizado en diferentes servicios.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param invoiceProduct
    */
   public CompleteInvoiceProductDTO(InvoiceProduct invoiceProduct) {
      super();
      this.id = (invoiceProduct.getId() == null ? null : invoiceProduct.getId());
      this.productCount = (invoiceProduct.getCount() == null ? null : invoiceProduct.getCount());
      this.productTitle = (invoiceProduct.getTitle() == null ? null : invoiceProduct.getTitle());
      this.productPrice = (invoiceProduct.getPrice() == null ? null : invoiceProduct.getPrice());
      this.productUnit = (invoiceProduct.getCountType() == null ? null : invoiceProduct.getCountType().toString());
      this.productMedia = (invoiceProduct.getMedia() == null ? null : invoiceProduct.getMedia());
   }
}
