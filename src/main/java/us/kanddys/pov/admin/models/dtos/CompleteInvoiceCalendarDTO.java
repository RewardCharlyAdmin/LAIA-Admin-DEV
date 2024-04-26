package us.kanddys.pov.admin.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.Invoice;

@AllArgsConstructor
@Data
public class CompleteInvoiceCalendarDTO {
   @JsonProperty
   private String tReservation;
   @JsonProperty
   private String reservation;
   @JsonProperty
   private Integer day;
   @JsonProperty
   private Integer month;
   @JsonProperty
   private Integer year;
   @JsonProperty
   private String from;
   @JsonProperty
   private String to;

   public CompleteInvoiceCalendarDTO() {
   };

   /**
    * Constructor personalizado utilizado en diferentes servicios.
    *
    * @author Igirod0
    * @version 1.0.0
    */
   public CompleteInvoiceCalendarDTO(Invoice invoice) {
      super();
      this.day = (invoice.getCalendarDay() != null ? invoice.getCalendarDay() : null);
      this.month = (invoice.getCalendarMonth() != null ? invoice.getCalendarMonth() : null);
      this.year = (invoice.getCalendarYear() != null ? invoice.getCalendarYear() : null);
      this.from = (invoice.getCalendarFrom() != null ? invoice.getCalendarFrom() : null);
      this.to = (invoice.getCalendarTo() != null ? invoice.getCalendarTo() : null);
   }
}
