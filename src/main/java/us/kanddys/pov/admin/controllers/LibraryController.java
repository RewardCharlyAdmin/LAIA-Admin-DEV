package us.kanddys.pov.admin.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.LibraryCollectionConfigurationDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionMiniatureDTO;
import us.kanddys.pov.admin.models.dtos.LibraryCollectionOrderDTO;
import us.kanddys.pov.admin.services.LibraryCollectionService;

@Controller
public class LibraryController {
   @Autowired
   private LibraryCollectionService libraryCollectionService;

   @MutationMapping("uCollectionRename")
   public Integer uCollectionRename(@Argument Long collection, @Argument String title,
         @Argument Long library) {
      return libraryCollectionService.uCollectionRename(collection, title, library);
   }

   @MutationMapping("uCollectionOrder")
   public Integer uCollectionOrder(@Argument Long collection, @Argument Integer ascDsc,
         @Argument Optional<String> orderProps) {
      return libraryCollectionService.uCollectionOrder(collection, ascDsc, orderProps);
   }

   @QueryMapping("gCollectionOrder")
   public LibraryCollectionOrderDTO gCollectionOrder(@Argument Long collectionId, @Argument Long libraryId,
         @Argument Long userId) {
      return libraryCollectionService.gCollectionOrder(collectionId, libraryId, userId);
   }

   @MutationMapping("dCollection")
   public Integer dCollection(@Argument Long collectionId) {
      return libraryCollectionService.dCollection(collectionId);
   }

   @QueryMapping("gCollectionMiniature")
   public LibraryCollectionMiniatureDTO gCollectionMiniature(@Argument Long collectionId, @Argument Long libraryId,
         @Argument Long userId) {
      return libraryCollectionService.gCollectionMiniature(collectionId, libraryId, userId);
   }

   @MutationMapping("uCollectionMiniature")
   public Integer uCollectionMiniature(@Argument Long collectionId, @Argument String miniatureHeader,
         @Argument String miniatureTitle, @Argument String miniatureSubtitle) {
      return libraryCollectionService.uCollectionMiniature(collectionId, miniatureHeader, miniatureTitle,
            miniatureSubtitle);
   }

   @QueryMapping("gCollectionConfiguration")
   public LibraryCollectionConfigurationDTO gCollectionConfiguration(@Argument Long collectionId,
         @Argument Long libraryId, @Argument Long userId) {
      return libraryCollectionService.gCollectionConfiguration(collectionId, libraryId, userId);
   }
}
