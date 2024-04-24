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
   public Long cBuyer(Long merchantId, String email, String name, String surname, String phone, Integer count,
         String media);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uBuyer(Long buyerId, Optional<String> email, Optional<String> name, Optional<String> surname,
         Optional<String> phone, Optional<Integer> count, Optional<String> media);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer dBuyer(Long buyerId);
}
