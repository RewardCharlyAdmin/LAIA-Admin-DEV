package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Map;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface BuyerLibraryService {
   /**
    * Este metodo se encarga de obtener la biblioteca de vendedores.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param libraryCollectionId
    * @param miniature
    * @param ordering
    * @param ascDsc
    * @param userId
    * @param page
    * @param maxResults
    * @return Map<String, Object>
    */
   public List<Map<String, Object>> gBuyerLibraryCollectionItems(Long libraryCollectionId, String[] miniatureHeader,
         String[] miniatureTitle, String[] miniatureSubtitle, String ordering, Integer ascDsc, Long userId,
         Integer page, Integer maxResults);

   /**
    * Este método se encarga de obtener el total de elementos de una colección.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param libraryCollectionId
    * @param miniature
    * @param ordering
    * @param ascDsc
    * @param userId
    * @param page
    * @param maxResults
    * @return Integer
    */
   public Integer gBuyerLibraryCollectionTotalItems(Long libraryCollectionId,
         String[] miniatureHeader,
         String[] miniatureTitle, String[] miniatureSubtitle, String ordering, Integer ascDsc, Long userId);

   /**
    * Método que tiene la obligación de obtener las propiedades asociadas a la
    * condición de una tabla.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param tableConditionId
    * @return Map<String, Object>
    */
   public Map<String, Object> gTableConditionBuyerProperties(Long tableConditionId);
}
