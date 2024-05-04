package us.kanddys.pov.admin.services.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.ExistLibraryCollectionTitleException;
import us.kanddys.pov.admin.exceptions.LibraryCollectionNotFoundException;
import us.kanddys.pov.admin.exceptions.LibraryNotFoundException;
import us.kanddys.pov.admin.exceptions.LibraryTypeCollectionNotFoundException;
import us.kanddys.pov.admin.exceptions.LibraryUserNotEqualException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.FilterBuyer;
import us.kanddys.pov.admin.models.Library;
import us.kanddys.pov.admin.models.LibraryCollection;
import us.kanddys.pov.admin.models.dtos.CollectionMiniatureDTO;
import us.kanddys.pov.admin.models.dtos.CreateCollectionBodyDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionConfigurationDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionFilterDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionMiniatureDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionOrderDTO;
import us.kanddys.pov.admin.repositories.jpa.FilterBuyerJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.LibraryCollectionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.LibraryJpaRepository;
import us.kanddys.pov.admin.services.BuyerLibraryService;
import us.kanddys.pov.admin.services.LibraryCollectionService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class LibraryCollectionServiceImpl implements LibraryCollectionService {

   @Autowired
   private LibraryCollectionJpaRepository libraryCollectionJpaRepository;

   @Autowired
   private LibraryJpaRepository libraryJpaRepository;

   @Autowired
   private FilterBuyerJpaRepository filterBuyerJpaRepository;

   @Autowired
   private BuyerLibraryService buyerLibraryService;

   @Override
   public Integer uCollectionRename(Long collectionId, String title, Long library) {
      Optional<Long> existLibraryCollectionId = libraryCollectionJpaRepository
            .existLibraryCollectionWithLibraryIdAndTitleAndNotLibraryCollectionId(collectionId, title, library);
      if (existLibraryCollectionId.isPresent()) {
         throw new ExistLibraryCollectionTitleException(ExceptionMessage.EXIST_LIBRARY_COLLECTION_TITLE);
      }
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      libraryCollection.setTitle(title);
      libraryCollectionJpaRepository.save(libraryCollection);
      return 1;

   }

   @Override
   public Integer uCollectionOrder(Long collectionId, Integer ascDsc, Optional<String> orderProps) {
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      if (!libraryCollection.getConf().equals(0)) {
         return 0;
      }
      libraryCollection.setAscDsc(ascDsc);
      if (orderProps.isPresent() && libraryCollection.getConf().equals(0)) {
         libraryCollection.setOrdering(orderProps.get());
      }
      libraryCollectionJpaRepository.save(libraryCollection);
      return 1;
   }

   @Override
   public LibraryCollectionOrderDTO gCollectionOrder(Long collectionId, Long libraryId, Long merchant) {
      Library library = libraryJpaRepository.findById(libraryId)
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      if (!library.getMerchant().equals(merchant)) {
         return new LibraryCollectionOrderDTO(null, null, 0, new HashSet<>());
      }
      Set<String> orderingSet = Arrays.stream(libraryCollection.getOrdering().split(" "))
            .collect(Collectors.toSet());
      return new LibraryCollectionOrderDTO(
            libraryCollection.getTitle(),
            libraryCollection.getAscDsc(),
            1,
            orderingSet);
   }

   @Override
   public LibraryCollectionConfigurationDTO gCollectionConfiguration(Long collectionId, Long libraryId, Long merchant) {
      LibraryCollectionConfigurationDTO newLibraryCollectionConfigurationDTO = new LibraryCollectionConfigurationDTO();
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      Library library = libraryJpaRepository.findById(libraryCollection.getLibraryId())
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      if (!library.getMerchant().equals(merchant)) {
         newLibraryCollectionConfigurationDTO.setOperation(0);
         return newLibraryCollectionConfigurationDTO;
      }
      newLibraryCollectionConfigurationDTO.setTitle(libraryCollection.getTitle());
      newLibraryCollectionConfigurationDTO.setConf(libraryCollection.getConf());
      newLibraryCollectionConfigurationDTO.setAscDsc(libraryCollection.getAscDsc());
      newLibraryCollectionConfigurationDTO
            .setOrder(Arrays.asList(libraryCollection.getOrdering().split(" ")).get(0).toLowerCase());
      newLibraryCollectionConfigurationDTO.setOperation(1);
      switch (library.getTypeCollection()) {
         case 1:
            FilterBuyer filterBuyer = filterBuyerJpaRepository.findById(collectionId)
                  .orElse(new FilterBuyer()); // Create a new FilterBuyer if not found
            newLibraryCollectionConfigurationDTO
                  .setFilter(new LibraryCollectionFilterDTO(filterBuyer.getId(), filterBuyer.getAlias()));
            break;
         default:
            throw new LibraryTypeCollectionNotFoundException(ExceptionMessage.LIBRARY_TYPE_COLLECTION_NOT_FOUND);
      }
      return newLibraryCollectionConfigurationDTO;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Map<String, Object> cCollection(CreateCollectionBodyDTO createCollectionBodyDTO, Integer duplicateCollection,
         Optional<String> duplicateCollectionTitle) {
      Library library = libraryJpaRepository.findById(createCollectionBodyDTO.getLibraryId())
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      Integer collectionsCount = libraryCollectionJpaRepository.countByLibraryId(createCollectionBodyDTO.getLibraryId())
            + 1;
      LibraryCollection libraryCollection = new LibraryCollection();
      libraryCollection.setLibraryId(createCollectionBodyDTO.getLibraryId());
      libraryCollection.setConf(0);
      libraryCollection.setAscDsc(1);
      libraryCollection.setMiniatureHeader(library.getMiniatureHeader());
      libraryCollection.setMiniatureTitle(library.getMiniatureTitle());
      libraryCollection.setMiniatureSubtitle(library.getMiniatureSubtitle());
      String ordering = Arrays.stream(library.getMiniature().split(" "))
            .map(String::toUpperCase)
            .collect(Collectors.joining(" "));
      libraryCollection.setOrdering(ordering);
      String title = duplicateCollectionTitle.isPresent() && duplicateCollection.equals(1)
            ? duplicateCollectionTitle.get()
            : "Personalizado #" + collectionsCount;
      libraryCollection.setTitle(title);
      libraryCollectionJpaRepository.save(libraryCollection);
      Map<String, Object> collectionData = new HashMap<>();
      collectionData.put("id", libraryCollection.getId());
      collectionData.put("title", libraryCollection.getTitle());
      collectionData.put("miniatureHeader", libraryCollection.getMiniatureHeader().split(" "));
      collectionData.put("miniatureTitle", libraryCollection.getMiniatureTitle().split(" "));
      collectionData.put("miniatureSubtitle", libraryCollection.getMiniatureSubtitle().split(" "));
      getCollectionCountItemsByType(library.getTypeCollection(), library.getMerchant(), libraryCollection,
            collectionData);
      getCollectionItemsByTypeAndPageAndMaxResults(library.getTypeCollection(), library.getMerchant(),
            libraryCollection,
            collectionData, 10, 1);
      return collectionData;
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void getCollectionCountItemsByType(Integer typeCollection, Long merchant,
         LibraryCollection libraryCollection, Map<String, Object> collectionData) {
      String[] miniatureHeader = libraryCollection.getMiniatureHeader() != null
            ? libraryCollection.getMiniatureHeader().split(" ")
            : null;
      String[] miniatureTitle = libraryCollection.getMiniatureTitle() != null
            ? libraryCollection.getMiniatureTitle().split(" ")
            : null;
      String[] miniatureSubtitle = libraryCollection.getMiniatureSubtitle() != null
            ? libraryCollection.getMiniatureSubtitle().split(" ")
            : null;
      switch (typeCollection) {
         case 1:
            Integer count = buyerLibraryService.gBuyerLibraryCollectionTotalItems(
                  libraryCollection.getId(),
                  miniatureHeader,
                  miniatureTitle,
                  miniatureSubtitle,
                  libraryCollection.getOrdering(),
                  libraryCollection.getAscDsc(),
                  merchant);
            collectionData.put("count", count);
            break;
         default:
            break;
      }
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void getCollectionItemsByTypeAndPageAndMaxResults(Integer typeCollection, Long merchant,
         LibraryCollection libraryCollection, Map<String, Object> collectionData, Integer maxResults, Integer page) {
      switch (typeCollection) {
         case 1:
            collectionData.put("items",
                  buyerLibraryService.gBuyerLibraryCollectionItems(libraryCollection.getId(),
                        (libraryCollection.getMiniatureHeader() != null
                              ? libraryCollection.getMiniatureHeader().split(" ")
                              : null),
                        (libraryCollection.getMiniatureTitle() != null
                              ? libraryCollection.getMiniatureTitle().split(" ")
                              : null),
                        libraryCollection.getMiniatureSubtitle() != null
                              ? libraryCollection.getMiniatureSubtitle().split(" ")
                              : null,
                        libraryCollection.getOrdering(),
                        libraryCollection.getAscDsc(),
                        merchant, page, maxResults));
            break;
         default:
            break;
      }
   }

   @Override
   public Map<String, Object> gCollection(Long collectionId, Long libraryId, Long merchant) {
      Library library = libraryJpaRepository.findById(libraryId)
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      if (!library.getMerchant().equals(merchant)) {
         Map<String, Object> response = new HashMap<>();
         response.put("operation", 0);
         return response;
      }
      String[] miniatureHeader = libraryCollection.getMiniatureHeader() != null
            ? libraryCollection.getMiniatureHeader().split(" ")
            : null;
      String[] miniatureTitle = libraryCollection.getMiniatureTitle() != null
            ? libraryCollection.getMiniatureTitle().split(" ")
            : null;
      String[] miniatureSubtitle = libraryCollection.getMiniatureSubtitle() != null
            ? libraryCollection.getMiniatureSubtitle().split(" ")
            : null;
      Map<String, Object> collectionData = new HashMap<>();
      collectionData.put("title", libraryCollection.getTitle());
      collectionData.put("miniatureHeader", miniatureHeader);
      collectionData.put("miniatureTitle", miniatureTitle);
      collectionData.put("miniatureSubtitle", miniatureSubtitle);
      getCollectionCountItemsByType(library.getTypeCollection(), merchant, libraryCollection, collectionData);
      getCollectionItemsByTypeAndPageAndMaxResults(library.getTypeCollection(), merchant, libraryCollection,
            collectionData, 10, 1);
      Map<String, Object> response = new HashMap<>();
      response.put("collection", collectionData);
      response.put("operation", 1);
      return response;

   }

   @Override
   public Map<String, Object> gCollectionElements(Long collectionId, Integer page) {
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      Library library = libraryJpaRepository.findById(libraryCollection.getLibraryId())
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      Map<String, Object> response = new HashMap<>();
      response.put("libraryId", library.getId());
      getCollectionItemsByTypeAndPageAndMaxResults(
            library.getTypeCollection(),
            library.getMerchant(),
            libraryCollection,
            response,
            10,
            page);
      return response;

   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Integer dCollection(Long collectionId) {
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      libraryCollectionJpaRepository.deleteById(libraryCollection.getId());
      return 1;
   }

   @Override
   public LibraryCollectionMiniatureDTO gCollectionMiniature(Long collectionId, Long libraryId, Long merchant) {
      Library library = libraryJpaRepository.findById(libraryId)
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      if (!library.getMerchant().equals(merchant)) {
         return new LibraryCollectionMiniatureDTO(null, 0, null);
      }
      String[] miniature = library.getMiniature() != null ? library.getMiniature().split(" ") : null;
      String[] miniatureHeader = libraryCollection.getMiniatureHeader() != null
            ? libraryCollection.getMiniatureHeader().split(" ")
            : null;
      String[] miniatureSubtitle = libraryCollection.getMiniatureSubtitle() != null
            ? libraryCollection.getMiniatureSubtitle().split(" ")
            : null;
      String[] miniatureTitle = libraryCollection.getMiniatureTitle() != null
            ? libraryCollection.getMiniatureTitle().split(" ")
            : null;
      CollectionMiniatureDTO collectionMiniatureDTO = new CollectionMiniatureDTO(
            libraryCollection.getId(),
            miniature,
            miniatureHeader,
            miniatureSubtitle,
            miniatureTitle);
      LibraryCollectionMiniatureDTO newLibraryCollectionMiniatureDTO = new LibraryCollectionMiniatureDTO(
            libraryCollection.getTitle(),
            1,
            collectionMiniatureDTO);
      return newLibraryCollectionMiniatureDTO;
   }

   @Override
   public Integer uCollectionMiniature(Long collectionId, String miniatureHeader, String miniatureTitle,
         String miniatureSubtitle) {
      LibraryCollection libraryCollection = libraryCollectionJpaRepository.findById(collectionId)
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      libraryCollection.setMiniatureHeader(miniatureHeader);
      libraryCollection.setMiniatureTitle(miniatureTitle);
      libraryCollection.setMiniatureSubtitle(miniatureSubtitle);
      libraryCollectionJpaRepository.save(libraryCollection);
      return 1;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Map<String, Object> cCollectionRef(CreateCollectionBodyDTO createCollectionBodyDTO) {
      Library library = libraryJpaRepository.findById(createCollectionBodyDTO.getLibraryId())
            .orElseThrow(() -> new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      LibraryCollection libraryCollection = libraryCollectionJpaRepository
            .findById(createCollectionBodyDTO.getCollectionId())
            .orElseThrow(() -> new LibraryCollectionNotFoundException(ExceptionMessage.LIBRARY_COLLECTION_NOT_FOUND));
      if (!library.getMerchant().equals(createCollectionBodyDTO.getUserId())) {
         throw new LibraryUserNotEqualException(ExceptionMessage.LIBRARY_USER_NOT_EQUAL);
      }
      Integer duplicateCollectionCount = libraryCollectionJpaRepository.countByLibraryIdAndTitle(
            createCollectionBodyDTO.getLibraryId(), libraryCollection.getTitle());
      String newDuplicateCollectionTitle;
      if ((libraryCollection.getTitle().length() + 12 + duplicateCollectionCount.toString().length()) >= 20) {
         newDuplicateCollectionTitle = libraryCollection.getTitle().substring(0,
               20 - 12 - duplicateCollectionCount.toString().length()) + "... (copia#" + duplicateCollectionCount + ")";
      } else {
         newDuplicateCollectionTitle = libraryCollection.getTitle() + "... (copia#" + duplicateCollectionCount + ")";
      }
      return cCollection(createCollectionBodyDTO, 1, Optional.of(newDuplicateCollectionTitle));
   }

   @Override
   public Map<String, Object> gCollectionFilter(Long user, Long filterId) {
      return null;
   }
}
