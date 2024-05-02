package us.kanddys.pov.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.MerchantNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.dtos.RCheckEmailDTO;
import us.kanddys.pov.admin.models.dtos.RLoginMerchantDTO;
import us.kanddys.pov.admin.repositories.jpa.MerchantJpaRepository;
import us.kanddys.pov.admin.services.SqqService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class SqqServiceImpl implements SqqService {

   @Autowired
   private MerchantJpaRepository merchantJpaRepository;

   @Override
   public RCheckEmailDTO sqqCheck(String email) {
      var merchantAtributtes = merchantJpaRepository.findMerchantIdAndSlugByEmail(email);
      if (merchantAtributtes.isEmpty())
         throw new MerchantNotFoundException(ExceptionMessage.MERCHANT_NOT_FOUND);
      return new RCheckEmailDTO(
            (merchantAtributtes.get("slug") == null ? null : merchantAtributtes.get("slug").toString()),
            (merchantAtributtes.get("id") == null ? null : Long.valueOf(merchantAtributtes.get("id").toString())));
   }

   @Override
   public RLoginMerchantDTO sqqLogin(Long merchantId, String password) {
      var merchantAtributtes = merchantJpaRepository.findPhoneAndLogoAndPasswordByMerchantId(merchantId);
      if (merchantAtributtes.get("password").toString().equals(password))
         return new RLoginMerchantDTO(
               (merchantAtributtes.get("phone") == null ? null
                     : merchantAtributtes.get("phone").toString()),
               (merchantAtributtes.get("logo") == null ? null : merchantAtributtes.get("logo").toString()), 1);
      return new RLoginMerchantDTO(null, null, 0);
   }
}
