package us.kanddys.pov.admin.exceptions;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class PaymentNotFoundException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public PaymentNotFoundException(String message) {
      super(message);
   }
}
