package us.kanddys.pov.admin.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.LibraryCollectionNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.TableCondition;
import us.kanddys.pov.admin.repositories.jpa.LibraryCollectionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.LibraryJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.TableConditionJpaRepository;
import us.kanddys.pov.admin.services.BuyerLibraryService;
import us.kanddys.pov.admin.services.LibraryCollectionFilterService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class LibraryCollectionFilterServiceImpl implements LibraryCollectionFilterService {
   @Autowired
   private LibraryJpaRepository libraryJpaRepository;

   @Autowired
   private LibraryCollectionJpaRepository libraryCollectionJpaRepository;

   @Autowired
   private TableConditionJpaRepository tableConditionJpaRepository;

   @Autowired
   private BuyerLibraryService buyerLibraryService;

   @Override
   public Map<String, Object> gCollectionFilter(Long userId, Integer libraryType, Long libraryId) {
      checkLibraryType(userId, libraryType, libraryId);
      return null;
   }

   private List<Map<String, Object>> checkLibraryType(Long userId, Integer libraryType, Long libraryId) {
      switch (libraryType) {
         case 1:
            // ! AGREGAR BUCLE QUE VAYA PASANDO LAS TABLE CONDITIONS UNA POR UNA.
            getBuyerConditionsForLibraryId(null);
         case 2:

         default:
            throw new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND);
      }
   }

   /**
    * Método que tiene la obligación de obtener las condiciones de filtrado
    * perteneceintes a una colección de biblioteca.
    * 
    * @param tableCondition
    * @return
    */
   private Map<String, Object> getBuyerConditionsForLibraryId(TableCondition tableCondition) {
      Map<String, Object> conditionData = new HashMap<>();
      switch (tableCondition.getId().intValue()) {
         // ! Comprador.
         case 1:
            // TODO: TERMINAR.
            conditionData.put("id", tableCondition.getId());
            conditionData.put("title", tableCondition.getAlias());
            conditionData.put("type_condition", tableCondition.getTypeCondition());
            conditionData.put("props", buyerLibraryService.gTableConditionBuyerProperties(tableCondition.getId()));
            return null;
      }
      return null;
   }
}
