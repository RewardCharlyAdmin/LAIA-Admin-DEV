package us.kanddys.pov.admin.models.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
public class CompleteInvoiceDTO {
   @JsonProperty("cli")
   private CompleteInvoiceClientDTO cli;
   @JsonProperty("merchant")
   private CompleteInvoiceMerchantDTO merchant;
   @JsonProperty("products")
   private Set<CompleteInvoiceProductDTO> products;
   @JsonProperty("direction")
   private CompleteInvoiceDirectionDTO direction;
   @JsonProperty("calendar")
   private CompleteInvoiceCalendarDTO calendar;
   @JsonProperty
   private String message;
   @JsonProperty
   private Integer operation;

   public CompleteInvoiceDTO() {
   };

}
