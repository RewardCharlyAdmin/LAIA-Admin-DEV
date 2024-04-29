package us.kanddys.pov.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.LibraryConfigDTO;
import us.kanddys.pov.admin.models.dtos.LibraryMiniatureDTO;
import us.kanddys.pov.admin.services.LibraryService;

@Controller
public class LibraryController {

   @Autowired
   private LibraryService libraryService;

   @MutationMapping("uLibraryRename")
   public Integer uLibraryRename(@Argument Long libraryId, @Argument String title, @Argument Long userId) {
      return libraryService.uLibraryRename(libraryId, title, userId);
   }

   @MutationMapping("uLibraryMiniatures")
   public Integer uLibraryMiniatures(@Argument Long libraryId, @Argument String miniatureHeader,
         @Argument String miniatureTitle, @Argument String miniatureSubtitle) {
      return libraryService.uLibraryMiniatures(libraryId, miniatureHeader, miniatureTitle,
            miniatureSubtitle);
   }

   @QueryMapping("gLibraryConf")
   public LibraryConfigDTO gLibraryConf(@Argument Long libraryId, @Argument Long userId) {
      return libraryService.gLibraryConf(libraryId, userId);
   }

   @QueryMapping("gLibraryMiniatures")
   public LibraryMiniatureDTO gLibraryMiniatures(@Argument Long libraryId, @Argument Long userId) {
      return libraryService.gLibraryMiniatures(libraryId, userId);
   }
}
