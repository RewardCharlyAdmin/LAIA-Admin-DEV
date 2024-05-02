package us.kanddys.pov.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.RCheckEmailDTO;
import us.kanddys.pov.admin.models.dtos.RLoginMerchantDTO;
import us.kanddys.pov.admin.services.SqqService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Controller
public class SqqController {

   @Autowired
   private SqqService sqqService;

   @QueryMapping
   public RCheckEmailDTO sqqCheck(@Argument String email) {
      return sqqService.sqqCheck(email);
   }

   @QueryMapping
   public RLoginMerchantDTO sqqLogin(@Argument Long merchantId, @Argument String password) {
      return sqqService.sqqLogin(merchantId, password);
   }
}
