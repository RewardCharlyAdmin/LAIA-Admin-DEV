package us.kanddys.pov.admin.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import us.kanddys.pov.admin.models.dtos.CreateCollectionBodyDTO;
import us.kanddys.pov.admin.services.LibraryCollectionService;

@RestController
@RequestMapping("/library-collection")
public class LibraryCollectionRestController {
   @Autowired
   private LibraryCollectionService libraryCollectionService;

   @PostMapping("/cCollection")
   public ResponseEntity<Map<String, Object>> cCollection(
         @RequestBody CreateCollectionBodyDTO createCollectionBodyDTO) {
      try {
         return ResponseEntity.ok(libraryCollectionService.cCollection(createCollectionBodyDTO, 0, Optional.empty()));
      } catch (RuntimeException e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      } catch (Exception e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      }
   }

   @GetMapping("/gCollection")
   public ResponseEntity<Map<String, Object>> gCollection(@RequestParam Long collectionId, @RequestParam Long libraryId,
         @RequestParam Long userId) {
      try {
         return ResponseEntity.ok(libraryCollectionService.gCollection(collectionId, libraryId, userId));
      } catch (RuntimeException e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      } catch (Exception e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      }
   }

   @GetMapping("/gCollectionElements")
   public ResponseEntity<Map<String, Object>> gCollectionElements(@RequestParam Long collectionId,
         @RequestParam Integer page) {
      try {
         return ResponseEntity.ok(libraryCollectionService.gCollectionElements(collectionId, page));
      } catch (RuntimeException e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      } catch (Exception e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      }
   }

   @PostMapping("/cCollectionRef")
   public ResponseEntity<Map<String, Object>> cCollectionRef(
         @RequestBody CreateCollectionBodyDTO createCollectionBodyDTO) {
      try {
         return ResponseEntity.ok(libraryCollectionService.cCollectionRef(createCollectionBodyDTO));
      } catch (RuntimeException e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      } catch (Exception e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      }
   }

   public ResponseEntity<Map<String, Object>> gCollectionFilter(@RequestParam Long user, @RequestParam Long filterId) {
      try {
         return ResponseEntity.ok(libraryCollectionService.gCollectionFilter(user, filterId));
      } catch (RuntimeException e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      } catch (Exception e) {
         return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
      }
   }
}
