package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Invoice;

@AllArgsConstructor
@Data
public class CompleteInvoiceMerchantDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private String title;
   @JsonProperty
   private String email;
   @JsonProperty
   private String phone;

   public CompleteInvoiceMerchantDTO() {
   };

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @param invoice
    * @author Igirod0
    * @version 1.0.0
    */
   public CompleteInvoiceMerchantDTO(Invoice invoice) {
      super();
      this.id = (invoice.getMerchantId() != null) ? invoice.getMerchantId() : null;
      this.title = (invoice.getMerchantTitle() != null) ? invoice.getMerchantTitle() : null;
      this.email = (invoice.getMerchantEmail() != null) ? invoice.getMerchantEmail() : null;
      this.phone = (invoice.getMerchantPhone() != null) ? invoice.getMerchantPhone() : null;
   }
}