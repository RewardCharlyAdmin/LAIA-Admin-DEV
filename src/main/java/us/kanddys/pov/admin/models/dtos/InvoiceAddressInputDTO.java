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
public class InvoiceAddressInputDTO {
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

   public InvoiceAddressInputDTO() {
   }
}
