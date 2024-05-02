package us.kanddys.pov.admin.services.utils;

import us.kanddys.pov.admin.models.utils.enums.ManufacturingTypeEnum;
import us.kanddys.pov.admin.models.utils.enums.QuestionTypeEmun;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;

/**
 * Esta clase contiene las utilizadades relacionadas a producto.
 * 
 * @author Igirod0
 * @version 1.0.0
 */
public class ProductUtils {

   /**
    * Método que se encarga de determinar el tipo de stock de un producto.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param typeOfStock
    * @return StockTypeEnum
    */
   public static StockTypeEnum determinateTypeOfStock(String typeOfStock) {
      switch (typeOfStock) {
         case "PACKAGE":
            return StockTypeEnum.PACKAGE;
         case "UNIT":
            return StockTypeEnum.UNIT;
         default:
            return null;
      }
   }

   /**
    * Método que calcula el tipo de manufacturación del producto.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param manufacturingType
    * @return ManufacturingTypeEnum
    */
   public static ManufacturingTypeEnum determinateManufacturingType(String manufacturingType) {
      switch (manufacturingType) {
         case "MH":
            return ManufacturingTypeEnum.MH;
         case "MN":
            return ManufacturingTypeEnum.MN;
         case "HR":
            return ManufacturingTypeEnum.HR;
         case "DY":
            return ManufacturingTypeEnum.DY;
         default:
            return null;
      }
   }

   /**
    * Método que determina que tipo de pregunta del vendedor es.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productQuestionType
    * @return QuestionTypeEnum
    */
   public static QuestionTypeEmun determinateProductQuestionType(String productQuestionType) {
      switch (productQuestionType) {
         case "TXT":
            return QuestionTypeEmun.TXT;
         case "MULTIPLE":
            return QuestionTypeEmun.MULTI;
         case "TIME":
            return QuestionTypeEmun.TIME;
         case "CALENDAR":
            return QuestionTypeEmun.CALENDAR;
         default:
            return null;
      }
   }
}
