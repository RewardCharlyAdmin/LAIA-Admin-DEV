package us.kanddys.pov.admin.services;

import java.util.Map;
import java.util.Optional;

import us.kanddys.pov.admin.models.dtos.CreateCollectionBodyDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionConfigurationDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionMiniatureDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionOrderDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface LibraryCollectionService {
   /**
    * Este método se encarga de renombrar una colección.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param title
    * @param library
    * @return Integer
    */
   public Integer uCollectionRename(Long collectionId, String title, Long library);

   /**
    * Este método tiene la obligación de actualizar una orden de una colección.
    *
    * @author Igirod0
    * @version 1.0.1
    * @param collectionId
    * @param ascDsc
    * @param orderProps
    * @return Integer
    */
   public Integer uCollectionOrder(Long collectionId, Integer ascDsc, Optional<String> orderProps);

   /**
    * Este método tiene la obligación de obtener una orden de una colección.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param libraryId
    * @param userId
    * @return LibraryCollectionOrderDTO
    */
   public LibraryCollectionOrderDTO gCollectionOrder(Long collectionId, Long libraryId, Long userId);

   /**
    * Este método tiene la obligación de obtener la configuración de una colección.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param libraryId
    * @param userId
    * @return Map<String, Object>
    */
   public LibraryCollectionConfigurationDTO gCollectionConfiguration(Long collectionId, Long libraryId, Long userId);

   /**
    * Este método tiene la obligación de crear una colección.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param createCollectionBodyDTO
    * @param duplicateCollection
    * @param duplicateCollectionTitle
    * @return Map<String, Object>
    */
   public Map<String, Object> cCollection(CreateCollectionBodyDTO createCollectionBodyDTO, Integer duplicateCollection,
         Optional<String> duplicateCollectionTitle);

   /**
    * Esta método tiene la obligación de obtener una colección asociada a una
    * biblioteca.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param libraryId
    * @param userId
    * @return
    */
   public Map<String, Object> gCollection(Long collectionId, Long libraryId, Long userId);

   /**
    * Este método tiene la obligación de obtener los elementos de una colección
    * recibiendo el identificador de la colección y el número de página.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param page
    * @return Map<String, Object>
    */
   public Map<String, Object> gCollectionElements(Long collectionId, Integer page);

   /**
    * Este método tiene la obligación de eliminar una colección.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @return Integer
    */
   public Integer dCollection(Long collectionId);

   /**
    * Este método tiene la obligación de obtener las miniaturas de una colección.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param libraryId
    * @param userId
    * @return Map<String, Object>
    */
   public LibraryCollectionMiniatureDTO gCollectionMiniature(Long collectionId, Long libraryId, Long userId);

   /**
    * Este método tiene la obligación de actualizar las miniaturas de una
    * colección.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param collectionId
    * @param miniatureHeader
    * @param miniatureTitle
    * @param miniatureSubtitle
    * @return Integer
    */
   public Integer uCollectionMiniature(Long collectionId, String miniatureHeader, String miniatureTitle,
         String miniatureSubtitle);

   /**
    * Este método tiene la obligación de duplicar una colección existente.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param createCollectionBodyDTO
    * @return Map<String, Object>
    */
   public Map<String, Object> cCollectionRef(CreateCollectionBodyDTO createCollectionBodyDTO);

   /**
    * Este método tiene la obligación de obtener los datos de un filtro.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param user
    * @param filterId
    * @return Map<String, Object>
    */
   public Map<String, Object> gCollectionFilter(Long user, Long filterId);
}
