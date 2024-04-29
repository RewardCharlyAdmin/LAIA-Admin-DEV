package us.kanddys.pov.admin.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.services.BuyerService;

@Controller
public class BuyerController {

   @Autowired
   private BuyerService buyerService;

   @MutationMapping
   public Long cAdminBuyer(@Argument Long merchant, @Argument Optional<String> email, @Argument Optional<String> name,
         @Argument Optional<String> surname, @Argument Optional<String> phone, @Argument Optional<Integer> count,
         @Argument Optional<String> media, @Argument Optional<Integer> pickUp, @Argument Optional<Integer> delivery) {
      return buyerService.cAdminBuyer(merchant, email, name, surname, phone, count, media, pickUp, delivery);
   }

   @MutationMapping
   public Integer uAdminBuyer(@Argument Long id, @Argument Optional<String> email, @Argument Optional<String> name,
         @Argument Optional<String> surname, @Argument Optional<String> phone, @Argument Optional<Integer> count,
         @Argument Optional<String> media, @Argument Optional<Integer> pickUp, @Argument Optional<Integer> delivery) {
      return buyerService.uAdminBuyer(id, email, name, surname, phone, count, media, pickUp, delivery);
   }

   @MutationMapping
   public Integer dAdminBuyer(@Argument Long id) {
      return buyerService.dAdminBuyer(id);
   }
}
