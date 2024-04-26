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
public class InvoiceRBodyDTO {
   @JsonProperty
   private Long userId;
   @JsonProperty
   private String email;
   @JsonProperty
   private String phone;
   @JsonProperty
   private String name;
   @JsonProperty
   private String surname;
   @JsonProperty
   private String reservation;
   @JsonProperty
   private String reservationType;
   @JsonProperty
   private Long merchantId;
   @JsonProperty
   private String message;
   @JsonProperty
   private Long batchId;
   @JsonProperty(value = "products")
   private List<InvoiceProductInputDTO> products;
   @JsonProperty(value = "address")
   private InvoiceAddressInputDTO address;

   public InvoiceRBodyDTO() {
   }
}
