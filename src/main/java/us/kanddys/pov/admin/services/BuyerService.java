package us.kanddys.pov.admin.services;

import java.util.Optional;

import us.kanddys.pov.admin.models.dtos.BuyerDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface BuyerService {

   /**
    * Este método se encarga de obtener el comprador por su id.
    *
    * @author Igirod0
    * @version 1.0.0
    */
   public BuyerDTO getBuyerById(Long id);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Long cAdminBuyer(Long merchantId, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count, Optional<String> media, Optional<Integer> pickUp,
         Optional<Integer> delivery);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uAdminBuyer(Long id, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count, Optional<String> media, Optional<Integer> pickUp,
         Optional<Integer> delivery);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer dAdminBuyer(Long id);
}
