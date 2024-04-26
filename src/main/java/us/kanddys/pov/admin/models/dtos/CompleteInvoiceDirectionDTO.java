package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Invoice;

@AllArgsConstructor
@Data
public class CompleteInvoiceDirectionDTO {
   @JsonProperty
   private String code;
   @JsonProperty
   private String country;
   @JsonProperty
   private String state;
   @JsonProperty
   private String city;
   @JsonProperty
   private String street;
   @JsonProperty
   private String number;
   @JsonProperty
   private String ref;
   @JsonProperty
   private String note;
   @JsonProperty
   private String lat;
   @JsonProperty
   private String lng;

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param invoice
    */
   public CompleteInvoiceDirectionDTO(Invoice invoice) {
      super();
      this.code = (invoice.getDirectionCode() != null) ? invoice.getDirectionCode() : null;
      this.country = (invoice.getDirectionCountry() != null) ? invoice.getDirectionCountry() : null;
      this.state = (invoice.getDirectionState() != null) ? invoice.getDirectionState() : null;
      this.city = (invoice.getDirectionCity() != null) ? invoice.getDirectionCity() : null;
      this.street = (invoice.getDirectionStreet() != null) ? invoice.getDirectionStreet() : null;
      this.number = (invoice.getDirectionNumber() != null) ? invoice.getDirectionNumber() : null;
      this.ref = (invoice.getDirectionRef() != null) ? invoice.getDirectionRef() : null;
      this.note = (invoice.getDirectionNote() != null) ? invoice.getDirectionNote() : null;
      this.lat = (invoice.getDirectionLat() != null) ? invoice.getDirectionLat() : null;
      this.lng = (invoice.getDirectionLng() != null) ? invoice.getDirectionLng() : null;
   }
}
