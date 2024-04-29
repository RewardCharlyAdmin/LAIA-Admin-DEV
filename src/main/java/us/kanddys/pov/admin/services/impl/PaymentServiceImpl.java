package us.kanddys.pov.admin.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import us.kanddys.pov.admin.exceptions.PaymentNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Payment;
import us.kanddys.pov.admin.repositories.jpa.PaymentJpaRepository;
import us.kanddys.pov.admin.services.PaymentService;
import us.kanddys.pov.admin.services.utils.PaymentUtils;

@Service
public class PaymentServiceImpl implements PaymentService {

   @Autowired
   private PaymentJpaRepository paymentJpaRepository;

   @Override
   public Integer cAdminPayment(Optional<String> title, Optional<String> cvu, Optional<String> email,
         Optional<Double> amount, Optional<String> amountType, Optional<String> payment, Long merchantId) {
      paymentJpaRepository
            .save(new Payment(null, title.orElse(null), cvu.orElse(null), email.orElse(null), amount.orElse(null),
                  PaymentUtils.determinateProductQuestionType(amountType.orElse(null)), payment.orElse(null),
                  merchantId));
      return 1;
   }

   @Override
   public Integer uAdminPayment(Long id, Optional<String> title, Optional<String> cvu, Optional<String> email,
         Optional<Double> amount, Optional<String> amountType, Optional<String> payment) {
      Payment existPayment = paymentJpaRepository.findById(id)
            .orElseThrow(() -> new PaymentNotFoundException(ExceptionMessage.PAYMENT_NOT_FOUND));
      existPayment.setTitle(title.orElse(existPayment.getTitle()));
      existPayment.setCvu(cvu.orElse(existPayment.getCvu()));
      existPayment.setEmail(email.orElse(existPayment.getEmail()));
      existPayment.setAmount(amount.orElse(existPayment.getAmount()));
      existPayment.setAmountType(
            PaymentUtils.determinateProductQuestionType(amountType.orElse(existPayment.getAmountType().toString())));
      existPayment.setPayment(payment.orElse(existPayment.getPayment()));
      paymentJpaRepository.save(existPayment);
      return 1;
   }

   @Override
   public Integer dAdminPayment(Long id) {
      paymentJpaRepository.deleteById(id);
      return 1;
   }
}
