package us.kanddys.pov.admin.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.services.PaymentService;

@Controller
public class PaymentController {

   @Autowired
   private PaymentService paymentService;

   @MutationMapping
   public Integer cAdminPayment(@Argument Optional<String> title, @Argument Optional<String> cvu,
         @Argument Optional<String> email, @Argument Optional<Double> amount, @Argument Optional<String> amountType,
         @Argument Optional<String> payment, @Argument Long merchant) {
      return paymentService.cAdminPayment(title, cvu, email, amount, amountType, payment, merchant);
   }

   @MutationMapping
   public Integer uAdminPayment(@Argument Long id, @Argument Optional<String> title, @Argument Optional<String> cvu,
         @Argument Optional<String> email, @Argument Optional<Double> amount, @Argument Optional<String> amountType,
         @Argument Optional<String> payment) {
      return paymentService.uAdminPayment(id, title, cvu, email, amount, amountType, payment);
   }

   @MutationMapping
   public Integer dAdminPayment(@Argument Long id) {
      return paymentService.dAdminPayment(id);
   }
}
