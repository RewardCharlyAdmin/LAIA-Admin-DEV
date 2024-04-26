package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Invoice;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class CompleteInvoiceClientDTO {
   @JsonProperty
   private Long id;
   @JsonProperty
   private String name;
   @JsonProperty
   private String surname;
   @JsonProperty
   private String email;
   @JsonProperty
   private String phone;
   @JsonProperty
   private String media;

   public CompleteInvoiceClientDTO() {
   };

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.0
    */
   public CompleteInvoiceClientDTO(Invoice invoice) {
      super();
      this.id = (invoice.getClientId() != null) ? invoice.getClientId() : null;
      this.name = (invoice.getClientName() != null) ? invoice.getClientName() : null;
      this.surname = (invoice.getClientSurname() != null) ? invoice.getClientSurname() : null;
      this.email = (invoice.getClientEmail() != null) ? invoice.getClientEmail() : null;
      this.phone = (invoice.getClientPhone() != null) ? invoice.getClientPhone() : null;
      this.media = (invoice.getClientMedia() != null) ? invoice.getClientMedia() : null;
   }
}
