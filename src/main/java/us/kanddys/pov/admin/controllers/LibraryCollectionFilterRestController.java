package us.kanddys.pov.admin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import us.kanddys.pov.admin.services.LibraryCollectionFilterService;

@RestController
@RequestMapping("/library-collection-filter")
public class LibraryCollectionFilterRestController {
   @Autowired
   private LibraryCollectionFilterService libraryCollectionFilterService;

   @GetMapping("/gCollectionFilter")
   public ResponseEntity<Map<String, Object>> gAdminSellCollectionFilter(@RequestParam Long collectionId) {
      // TODO: TERMINAR.
      // ResponseEntity.ok(libraryCollectionFilterService.gAdminSellCollectionFilter(collectionId))
      return null;
   }
}
