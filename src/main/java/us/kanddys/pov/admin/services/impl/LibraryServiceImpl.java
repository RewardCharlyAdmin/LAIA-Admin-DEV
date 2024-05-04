package us.kanddys.pov.admin.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.ExistLibraryTitleException;
import us.kanddys.pov.admin.exceptions.LibraryNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Library;
import us.kanddys.pov.admin.models.LibraryCollection;
import us.kanddys.pov.admin.models.dtos.LibraryConfigDTO;
import us.kanddys.pov.admin.models.dtos.LibraryMiniatureDTO;
import us.kanddys.pov.admin.repositories.jpa.BuyerJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.LibraryCollectionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.LibraryJpaRepository;
import us.kanddys.pov.admin.services.BuyerLibraryService;
import us.kanddys.pov.admin.services.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {
   @Autowired
   private LibraryJpaRepository libraryJpaRepository;

   @Autowired
   private BuyerJpaRepository buyerJpaRepository;

   @Autowired
   private LibraryCollectionJpaRepository libraryCollectionJpaRepository;

   @Autowired
   private BuyerLibraryService buyerLibraryService;

   @Override
   public Map<String, Object> gAdminSellLibrary(Long merchant, Long libraryId) {
      Map<String, Object> response = new HashMap<String, Object>();
      List<Map<String, Object>> collections = new ArrayList<Map<String, Object>>();
      Optional<Library> library = libraryJpaRepository.findById(libraryId);
      if (library.isEmpty()) {
         throw new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND);
      }
      // ! VALIDACIÓN: SI EL USUARIO ENVIADO ES EL MISMO QUE EL DE LA BIBLIOTECA.
      if (!library.get().getMerchant().equals(merchant)) {
         response.put("operation", 0);
         return response;
      }
      // * Propiedades generales.
      response.put("title", (library.get().getTitle() == null ? null : library.get().getTitle()));
      response.put("type", library.get().getTypeCollection());
      response.put("count", buyerJpaRepository.findCountBuyersByUserId(merchant));
      response.put("operation", 1);
      switch (library.get().getTypeCollection()) {
         case 1:
            // * Colleciones pertenecientes a la biblioteca de vendedores.
            List<LibraryCollection> sellerLibraryCollections = libraryCollectionJpaRepository
                  .findByLibraryId(library.get().getId());
            for (LibraryCollection libraryCollection : sellerLibraryCollections) {
               Map<String, Object> collectionData = new HashMap<String, Object>();
               collectionData.put("id", libraryCollection.getId());
               collectionData.put("title", libraryCollection.getTitle());
               collectionData.put("miniatureHeader", (libraryCollection.getMiniatureHeader()) == null ? null
                     : libraryCollection.getMiniatureHeader().split(" "));
               collectionData.put("miniatureTitle", (libraryCollection.getMiniatureTitle()) == null ? null
                     : libraryCollection.getMiniatureTitle().split(" "));
               collectionData.put("miniatureSubtitle", (libraryCollection.getMiniatureSubtitle()) == null ? null
                     : libraryCollection.getMiniatureSubtitle().split(" "));
               collectionData.put("items",
                     // ! La miniature define las propiedades de los objetos de la collection.
                     buyerLibraryService.gBuyerLibraryCollectionItems(libraryCollection.getId(),
                           (libraryCollection.getMiniatureHeader() != null
                                 ? libraryCollection.getMiniatureHeader().split(" ")
                                 : null),
                           (libraryCollection.getMiniatureTitle() != null
                                 ? libraryCollection.getMiniatureTitle().split(" ")
                                 : null),
                           (libraryCollection.getMiniatureSubtitle() != null
                                 ? libraryCollection.getMiniatureSubtitle().split(" ")
                                 : null),
                           libraryCollection.getOrdering(),
                           libraryCollection.getAscDsc(), merchant, 1, 10));
               collectionData.put("count",
                     buyerLibraryService.gBuyerLibraryCollectionTotalItems(libraryCollection.getId(),
                           (libraryCollection.getMiniatureHeader() != null
                                 ? libraryCollection.getMiniatureHeader().split(" ")
                                 : null),
                           (libraryCollection.getMiniatureTitle() != null
                                 ? libraryCollection.getMiniatureTitle().split(" ")
                                 : null),
                           (libraryCollection.getMiniatureSubtitle() != null
                                 ? libraryCollection.getMiniatureSubtitle().split(" ")
                                 : null),
                           libraryCollection.getOrdering(),
                           libraryCollection.getAscDsc(), merchant));
               collections.add(collectionData);
            }
            break;
         case 2:
            // // * Colleciones pertenecientes a la biblioteca de articulos.
            // List<LibraryCollection> articleLibraryCollections =
            // libraryCollectionJpaRepository
            // .findByLibraryId(library.get().getId());
            // for (LibraryCollection libraryCollection : articleLibraryCollections) {
            // Map<String, Object> collectionData = new HashMap<String, Object>();
            // collectionData.put("id", libraryCollection.getId());
            // collectionData.put("title", libraryCollection.getTitle());
            // collectionData.put("miniature", libraryCollection.getMiniature());
            // collectionData.put("items",
            // // ! La miniature define las propiedades de los objetos de la collection.
            // articleLibraryService.gArticleLibraryCollectionItems(libraryCollection.getId(),
            // libraryCollection.getMiniature()));
            // collections.add(collectionData);
            // }
         case 3:
            // // * Colleciones pertenecientes a la biblioteca de direcciones.
            // // TODO: REEMPLAZAR POR REPOSITORIOS DE DIRECCIONES.
            // List<LibraryCollection> directionLibraryCollections =
            // libraryCollectionJpaRepository
            // .findByLibraryId(library.get().getId());
            // for (LibraryCollection libraryCollection : directionLibraryCollections) {
            // Map<String, Object> collectionData = new HashMap<String, Object>();
            // collectionData.put("id", libraryCollection.getId());
            // collectionData.put("title", libraryCollection.getTitle());
            // collectionData.put("miniature", libraryCollection.getMiniature());
            // collectionData.put("items",
            // // ! La miniature define las propiedades de los objetos de la collection.
            // articleLibraryService.gArticleLibraryCollectionItems(libraryCollection.getId(),
            // libraryCollection.getMiniature()));
            // collections.add(collectionData);
            // }
            break;
      }
      // ! Colleciones totales.
      response.put("collections", collections);
      return response;
   }

   @Override
   public Integer uLibraryRename(Long libraryId, String title, Long userId) {
      Optional<Long> existLibraryId = libraryJpaRepository.existLibraryWithUserIdAndTitleAndNotLibraryId(userId, title,
            libraryId);
      if (existLibraryId.isEmpty()) {
         Optional<Library> library = libraryJpaRepository.findById(libraryId);
         if (library.isEmpty()) {
            throw new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND);
         }
         library.get().setTitle(title);
         libraryJpaRepository.save(library.get());
      } else {
         throw new ExistLibraryTitleException(ExceptionMessage.EXIST_LIBRARY_TITLE);
      }
      return 1;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Integer uLibraryMiniatures(Long libraryId, String miniatureHeader, String miniatureTitle,
         String miniatureSubtitle) {
      List<Long> libraryCollections = libraryCollectionJpaRepository.findLibraryCollectionsByLibraryId(libraryId);
      libraryJpaRepository.updateMiniatureByLibraryId(libraryId, miniatureHeader,
            miniatureTitle, miniatureSubtitle);
      // ! Actualizamos la conf para indicar que se cambió la miniatura por defecto.
      if (libraryJpaRepository.findConfByLibraryId(libraryId).equals(0))
         libraryJpaRepository.updateConfByLibraryId(1, libraryId);
      // ! Actualizamos la miniaturas de todas las colecciones de la biblioteca.
      libraryCollectionJpaRepository.updateMiniaturesByLibraryCollectionsIds(libraryCollections, miniatureHeader,
            miniatureTitle, miniatureSubtitle);
      return 1;
   }

   @Override
   public LibraryConfigDTO gLibraryConf(Long libraryId, Long userId) {
      Map<String, Object> libraryAtributtes = libraryJpaRepository
            .findConfAndUserIdAndTypeCollectionAndTitleByLibraryId(libraryId);
      if (libraryAtributtes.size() == 0) {
         throw new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND);
      }
      // ! No se valida si es nulo porque siempre va a existir un usuario.
      if (!Long.valueOf(libraryAtributtes.get("user_id").toString()).equals(userId)) {
         return new LibraryConfigDTO(null, null, null, 0);
      }
      return new LibraryConfigDTO(
            (libraryAtributtes.get("title") != null ? libraryAtributtes.get("title").toString() : null),
            Integer.valueOf(libraryAtributtes.get("type_collection").toString()),
            Integer.valueOf(libraryAtributtes.get("conf").toString()), 1);
   }

   @Override
   public LibraryMiniatureDTO gLibraryMiniatures(Long libraryId, Long userId) {
      Map<String, Object> libraryAtributtes = libraryJpaRepository
            .findTitleAndMiniatureAndUserIdAndMiniatureHeaderAndMiniatureTitleAndMiniatureSubtitleByLibraryId(
                  libraryId);
      if (libraryAtributtes.size() == 0)
         throw new LibraryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND);
      // ! No se valida si es nulo porque siempre va a existir un usuario.
      if (!Long.valueOf(libraryAtributtes.get("user_id").toString()).equals(userId))
         return new LibraryMiniatureDTO(null, null, null, null, null, 0);
      return new LibraryMiniatureDTO(
            (libraryAtributtes.get("title") != null ? libraryAtributtes.get("title").toString() : null),
            (libraryAtributtes.get("miniature") != null ? libraryAtributtes.get("miniature").toString().split(" ")
                  : null),
            (libraryAtributtes.get("miniature_header") != null
                  ? libraryAtributtes.get("miniature_header").toString().split(" ")
                  : null),
            (libraryAtributtes.get("miniature_title") != null
                  ? libraryAtributtes.get("miniature_title").toString().split(" ")
                  : null),
            (libraryAtributtes.get("miniature_subtitle") != null
                  ? libraryAtributtes.get("miniature_subtitle").toString().split(" ")
                  : null),
            1);
   }
}
