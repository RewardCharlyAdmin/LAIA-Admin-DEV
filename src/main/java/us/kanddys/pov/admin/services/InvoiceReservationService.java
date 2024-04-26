package us.kanddys.pov.admin.services;

import java.util.Set;

import us.kanddys.pov.admin.models.dtos.InvoiceReservationDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface InvoiceReservationService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public InvoiceReservationDTO getInvoiceReservation(Long merchantId, Set<Long> articlesIds);
}
