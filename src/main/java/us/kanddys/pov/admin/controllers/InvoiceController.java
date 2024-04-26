package us.kanddys.pov.admin.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.CompleteInvoiceDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceReservationDTO;
import us.kanddys.pov.admin.services.InvoiceService;

@Controller
public class InvoiceController {

   @Autowired
   private InvoiceService invoiceService;

   @QueryMapping("gAdminSellInvoice")
   public CompleteInvoiceDTO gAdminSellInvoice(@Argument Long user, @Argument Long invoice) {
      return invoiceService.gAdminSellInvoice(user, invoice);
   }

   @QueryMapping("gAdminSellInvoiceReservation")
   public InvoiceReservationDTO gAdminSellInvoiceReservation(@Argument Long merchant, @Argument Set<Long> products) {
      return invoiceService.gAdminSellInvoiceReservation(merchant, products);
   }
}
