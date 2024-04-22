package us.kanddys.pov.admin.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import us.kanddys.pov.admin.services.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryRestController {

   @Autowired
   private LibraryService libraryService;

   @GetMapping("/gLibrary")
   public ResponseEntity<Map<String, Object>> gAdminSellLibrary(@RequestParam Long userId,
         @RequestParam Long libraryId) {
      return ResponseEntity.ok(libraryService.gAdminSellLibrary(userId, libraryId));
   }
}
