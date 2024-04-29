package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Set;

import us.kanddys.pov.admin.models.dtos.CompleteInvoiceDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceAddressInputDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceProductInputDTO;
import us.kanddys.pov.admin.models.dtos.InvoiceReservationDTO;
import us.kanddys.pov.admin.models.dtos.utils.LongDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface InvoiceService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public LongDTO createAdminSellInvoice(Long userId, String email, String phone,
         String name, String lastName, Long merchantId, List<InvoiceProductInputDTO> articles,
         InvoiceAddressInputDTO address, Long batchId, String reservation, String reservationType,
         String message);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public CompleteInvoiceDTO gAdminSellInvoice(Long invoiceId, Long userId);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public InvoiceReservationDTO gAdminSellInvoiceReservation(Long merchantId, Set<Long> articlesIds);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uAdminSellInvoiceStatus(Long invoice, String status);
}
