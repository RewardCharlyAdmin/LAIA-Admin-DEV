package us.kanddys.pov.admin.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.dtos.NewAuxiliarProductDTO;
import us.kanddys.pov.admin.services.AuxiliarProductService;

@RestController
@RequestMapping("/auxiliar-product")
public class AuxiliarProductRestController {

   @Autowired
   private AuxiliarProductService auxiliarProductService;

   @RequestMapping(method = { RequestMethod.POST }, value = "/create", produces = {
         "application/json" }, consumes = { "multipart/form-data" })
   public NewAuxiliarProductDTO createAuxiliarProduct(@RequestPart Optional<List<MultipartFile>> medias,
         @RequestPart Optional<String> merchant, @RequestPart Optional<String> title,
         @RequestPart Optional<String> tStock, @RequestPart Optional<String> price, @RequestPart Optional<String> stock,
         @RequestPart Optional<String> status,
         @RequestPart Optional<String> manufacturingTime, @RequestPart Optional<String> invenstmentNote,
         @RequestPart Optional<String> invenstmentAmount, @RequestPart Optional<String> invenstmentTitle,
         @RequestPart Optional<String> manufacturingType, @RequestPart Optional<String> segmentTitle,
         @RequestPart Optional<String> segmentDescription, @RequestPart Optional<MultipartFile> segmentMedia,
         @RequestPart Optional<String> hashtagValue, @RequestPart Optional<String> keywords,
         @RequestPart Optional<String> productQuestionValue, @RequestPart Optional<String> productQuestionType,
         @RequestPart Optional<String> productQuestionLimit, @RequestPart Optional<String> productQuestionRequired,
         @RequestPart Optional<String> productQuestionOptions, @RequestPart Optional<String> categoryTitle) {
      return auxiliarProductService.createAuxiliarProduct(medias, title, tStock, price, stock, status,
            merchant, manufacturingTime, invenstmentNote, invenstmentAmount, invenstmentTitle, manufacturingType,
            segmentTitle, segmentDescription, segmentMedia, hashtagValue,
            (keywords.isPresent()) ? Optional.of(List.of(keywords.get().split("♀")))
                  : Optional.empty(),
            productQuestionValue, productQuestionType, productQuestionLimit, productQuestionRequired,
            (productQuestionOptions.isPresent()) ? Optional.of(List.of(productQuestionOptions.get().split("♀")))
                  : Optional.empty(),
            categoryTitle);
   }
}
