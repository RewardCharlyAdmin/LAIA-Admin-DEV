package us.kanddys.pov.admin.services;

import us.kanddys.pov.admin.models.dtos.RCheckEmailDTO;
import us.kanddys.pov.admin.models.dtos.RLoginMerchantDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface SqqService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public RCheckEmailDTO sqqCheck(String email);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public RLoginMerchantDTO sqqLogin(Long merchantId, String password);
}
