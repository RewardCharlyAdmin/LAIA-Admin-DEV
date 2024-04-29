package us.kanddys.pov.admin.services;

import java.util.Optional;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface PaymentService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer cAdminPayment(Optional<String> title, Optional<String> cvu, Optional<String> email,
         Optional<Double> amount, Optional<String> amountType, Optional<String> payment, Long merchantId);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uAdminPayment(Long id, Optional<String> title, Optional<String> cvu, Optional<String> email,
         Optional<Double> amount, Optional<String> amountType, Optional<String> payment);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer dAdminPayment(Long id);

}
