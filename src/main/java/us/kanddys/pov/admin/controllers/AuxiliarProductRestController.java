package us.kanddys.pov.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.services.AuxiliarProductService;

@RestController
@RequestMapping("/auxiliar-product")
public class AuxiliarProductRestController {

   @Autowired
   private AuxiliarProductService auxiliarProductService;

   @RequestMapping(method = { RequestMethod.POST }, value = "/upload-medias", produces = {
         "application/json" }, consumes = { "multipart/form-data" })
   public ResponseEntity<Integer> uploadMedias(@RequestPart("medias") List<MultipartFile> medias,
         @RequestPart("productId") Long productId) {
      return ResponseEntity.ok(auxiliarProductService.uploadMedias(medias, productId));
   }
}
