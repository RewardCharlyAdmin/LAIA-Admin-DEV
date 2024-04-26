package us.kanddys.pov.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.kanddys.pov.admin.models.dtos.InvoiceRBodyDTO;
import us.kanddys.pov.admin.models.dtos.utils.LongDTO;
import us.kanddys.pov.admin.services.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceRestController {

   @Autowired
   private InvoiceService invoiceService;

   @PostMapping(value = "/create")
   public LongDTO createAdminSellInvoice(@RequestBody InvoiceRBodyDTO invoiceRBodyDTO) {
      return invoiceService.createAdminSellInvoice(
            (invoiceRBodyDTO.getUserId() != null ? invoiceRBodyDTO.getUserId() : null),
            (invoiceRBodyDTO.getEmail() != null ? invoiceRBodyDTO.getEmail() : null),
            (invoiceRBodyDTO.getPhone() != null ? invoiceRBodyDTO.getPhone() : null),
            (invoiceRBodyDTO.getName() != null ? invoiceRBodyDTO.getName() : null),
            (invoiceRBodyDTO.getSurname() != null ? invoiceRBodyDTO.getSurname() : null),
            invoiceRBodyDTO.getMerchantId(), invoiceRBodyDTO.getProducts(),
            (invoiceRBodyDTO.getAddress() == null ? null : invoiceRBodyDTO.getAddress()),
            invoiceRBodyDTO.getBatchId(), invoiceRBodyDTO.getReservation(), invoiceRBodyDTO.getReservationType(),
            (invoiceRBodyDTO.getMessage() != null ? invoiceRBodyDTO.getMessage() : null));
   }
}
