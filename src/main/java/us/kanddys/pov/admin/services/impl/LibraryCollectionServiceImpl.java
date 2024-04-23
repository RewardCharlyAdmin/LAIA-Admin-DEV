package us.kanddys.pov.admin.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.dtos.CreateCollectionBodyDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionConfigurationDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionMiniatureDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionOrderDTO;
import us.kanddys.pov.admin.services.LibraryCollectionService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class LibraryCollectionServiceImpl implements LibraryCollectionService {

   @Override
   public Integer uCollectionRename(Long collectionId, String title, Long library) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'uCollectionRename'");
   }

   @Override
   public Integer uCollectionOrder(Long collectionId, Integer ascDsc, Optional<String> orderProps) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'uCollectionOrder'");
   }

   @Override
   public LibraryCollectionOrderDTO gCollectionOrder(Long collectionId, Long libraryId, Long userId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollectionOrder'");
   }

   @Override
   public LibraryCollectionConfigurationDTO gCollectionConfiguration(Long collectionId, Long libraryId, Long userId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollectionConfiguration'");
   }

   @Override
   public Map<String, Object> cCollection(CreateCollectionBodyDTO createCollectionBodyDTO, Integer duplicateCollection,
         Optional<String> duplicateCollectionTitle) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'cCollection'");
   }

   @Override
   public Map<String, Object> gCollection(Long collectionId, Long libraryId, Long userId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollection'");
   }

   @Override
   public Map<String, Object> gCollectionElements(Long collectionId, Integer page) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollectionElements'");
   }

   @Override
   public Integer dCollection(Long collectionId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'dCollection'");
   }

   @Override
   public LibraryCollectionMiniatureDTO gCollectionMiniature(Long collectionId, Long libraryId, Long userId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollectionMiniature'");
   }

   @Override
   public Integer uCollectionMiniature(Long collectionId, String miniatureHeader, String miniatureTitle,
         String miniatureSubtitle) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'uCollectionMiniature'");
   }

   @Override
   public Map<String, Object> cCollectionRef(CreateCollectionBodyDTO createCollectionBodyDTO) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'cCollectionRef'");
   }

   @Override
   public Map<String, Object> gCollectionFilter(Long user, Long filterId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'gCollectionFilter'");
   }

}
