package us.kanddys.pov.admin.services.utils;

import us.kanddys.pov.admin.models.utils.enums.AmountTypeEnum;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public class PaymentUtils {

   /**
    * Método que determina que tipo de pregunta del vendedor es.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productQuestionType
    * @return QuestionTypeEnum
    */
   public static AmountTypeEnum determinateProductQuestionType(String amountTypeEnum) {
      switch (amountTypeEnum) {
         case "PERCENTAGE":
            return AmountTypeEnum.PERCENTAGE;
         case "AMOUNT":
            return AmountTypeEnum.AMOUNT;
         default:
            return null;
      }
   }
}
