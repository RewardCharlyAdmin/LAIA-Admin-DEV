package us.kanddys.pov.admin.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import us.kanddys.pov.admin.models.dtos.DifferentProductMediaDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductRestController {
   @Autowired
   private ProductService productService;

   @RequestMapping(method = { RequestMethod.POST }, value = "/create", produces = {
         "application/json" }, consumes = { "multipart/form-data" })
   public ProductDTO createProduct(@RequestPart Optional<MultipartFile> frontPage,
         @RequestPart Optional<String> userId,
         @RequestPart Optional<String> title, @RequestPart Optional<String> typeOfSale,
         @RequestPart Optional<String> price, @RequestPart Optional<String> stock, @RequestPart Optional<String> status,
         @RequestPart Optional<String> manufacturingTime, @RequestPart Optional<String> invenstmentNote,
         @RequestPart Optional<String> invenstmentAmount, @RequestPart Optional<String> invenstmentTitle,
         @RequestPart Optional<String> manufacturingType, @RequestPart Optional<String> segmentTitle,
         @RequestPart Optional<String> segmentDescription, @RequestPart Optional<MultipartFile> segmentMedia,
         @RequestPart Optional<String> hashtagValue, @RequestPart Optional<List<String>> keywordValue,
         @RequestPart Optional<String> productQuestionValue, @RequestPart Optional<String> productQuestionType,
         @RequestPart Optional<String> productQuestionLimit, @RequestPart Optional<String> productQuestionRequired,
         @RequestPart Optional<List<String>> productQuestionOptions) {
      return productService.createProduct(frontPage, title, typeOfSale, price, stock, status,
            userId, manufacturingTime, invenstmentNote, invenstmentAmount, invenstmentTitle, manufacturingType,
            segmentTitle, segmentDescription, segmentMedia, hashtagValue, keywordValue, productQuestionValue,
            productQuestionType, productQuestionLimit, productQuestionRequired, productQuestionOptions);
   }

   @RequestMapping(method = { RequestMethod.PATCH }, value = "/update-medias", produces = {
         "application/json" }, consumes = { "multipart/form-data" })
   public List<DifferentProductMediaDTO> updateAdminSellProductMedia(@RequestPart String productId,
         @RequestPart String index, @RequestPart Optional<String> title, @RequestPart Optional<String> price,
         @RequestPart Optional<String> tPrice, @RequestPart Optional<String> stock,
         @RequestPart Optional<String> tStock, @RequestPart List<MultipartFile> newImages,
         @RequestPart List<String> existImages) {
      return productService.updateAdminSellProductMedia(Long.valueOf(productId), index, title, price, tPrice, stock,
            tStock, existImages, newImages);
   }
}
