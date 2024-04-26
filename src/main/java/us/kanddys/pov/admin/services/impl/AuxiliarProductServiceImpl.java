package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.AuxiliarMultipleQuestionOption;
import us.kanddys.pov.admin.models.AuxiliarProduct;
import us.kanddys.pov.admin.models.AuxiliarProductKeyword;
import us.kanddys.pov.admin.models.AuxiliarProductMedia;
import us.kanddys.pov.admin.models.AuxiliarProductQuestion;
import us.kanddys.pov.admin.models.ProductMedia;
import us.kanddys.pov.admin.models.dtos.NewAuxiliarProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductImageDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarMultipleQuestionOptionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductKeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentJpaRepository;
import us.kanddys.pov.admin.services.AuxiliarProductService;
import us.kanddys.pov.admin.services.ProductService;
import us.kanddys.pov.admin.services.storage.FirebaseStorageService;
import us.kanddys.pov.admin.services.utils.ProductUtils;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class AuxiliarProductServiceImpl implements AuxiliarProductService {

   @Autowired
   private FirebaseStorageService firebaseStorageService;

   @Autowired
   private AuxiliarProductJpaRepository auxiliarProductJpaRepository;

   @Autowired
   private AuxiliarProductKeywordJpaRepository auxiliarProductKeyWordJpaRepository;

   @Autowired
   private AuxiliarProductQuestionJpaRepository auxiliarMultipleQuestionJpaRepository;

   @Autowired
   private AuxiliarMultipleQuestionOptionJpaRepository auxiliarMultipleQuestionOptionJpaRepository;

   @Autowired
   private AuxiliarProductMediaJpaRepository auxiliarProductMediaJpaRepository;

   @Autowired
   private ProductMediaJpaRepository productMediaJpaRepository;

   @Autowired
   private ProductSegmentJpaRepository productSegmentJpaRepository;

   @Autowired
   private ProductService productService;

   @Override
   public NewAuxiliarProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValues, Optional<String> productQuestionValue,
         Optional<String> productQuestionType, Optional<String> productQuestionLimit,
         Optional<String> productQuestionRequired, Optional<List<String>> productQuestionOptions) {
      if (userId.isEmpty()) {
         Long auxProductId;
         try {
            auxProductId = auxiliarProductJpaRepository.save(new AuxiliarProduct(null, null, title.get(),
                  Double.parseDouble(price.get()), Integer.parseInt(stock.get()), (tStock.isPresent()
                        ? ProductUtils.determinateTypeOfStock(tStock.get())
                        : null),
                  hashtagValue.get(),
                  (manufacturingType.isPresent() ? ProductUtils.determinateManufacturingType(manufacturingType.get())
                        : null),
                  Integer.parseInt(manufacturingTime.get()),
                  DateUtils.getCurrentDate(), DateUtils.getCurrentDate(), Integer.parseInt(status.get()), 0)).getId();
         } catch (ParseException e) {
            throw new RuntimeException("Error al parsear la fecha.");
         }
         ProductImageDTO frontPage = null;
         ProductImageDTO segmentMediaArticle = null;
         if (medias.isPresent()) {
            frontPage = (new ProductImageDTO(
                  firebaseStorageService.uploadFile(medias.get().get(0),
                        "front-page-product-" + auxProductId.toString(),
                        "frontPages"),
                  "IMAGE"));
            auxiliarProductJpaRepository.updateFrontPage(frontPage.getUrl(),
                  auxProductId);
         }
         if (segmentMedia.isPresent()) {
            segmentMediaArticle = new ProductImageDTO(
                  firebaseStorageService.uploadFile(segmentMedia.get(),
                        "product-detail" + auxProductId.toString() + "-" +
                              UUID.randomUUID().toString(),
                        "productDetails"),
                  "IMAGE");
            auxiliarProductJpaRepository.updateSegmentMedia(segmentMediaArticle.getUrl(),
                  auxProductId);
         }
         if (keywordValues.isPresent()) {
            auxiliarProductKeyWordJpaRepository.saveAll(keywordValues.get().stream()
                  .map(keyword -> new AuxiliarProductKeyword(null, keyword, auxProductId, null))
                  .collect(Collectors.toList()));
         }
         if (productQuestionType.isPresent() &&
               productQuestionType.get().equals("MULTIPLE")) {
            // ! Agregar la pregunta primero.
            auxiliarMultipleQuestionJpaRepository
                  .save(new AuxiliarProductQuestion(null, auxProductId, productQuestionValue.get(),
                        ProductUtils.determinateProductQuestionType(productQuestionType.get()),
                        Integer.parseInt(productQuestionLimit.get()), Integer.parseInt(productQuestionRequired.get())));
            productQuestionOptions.ifPresent(options -> {
               options.forEach(option -> {
                  // ! Se agrega cada opción de la pregunta multiple.
                  auxiliarMultipleQuestionOptionJpaRepository
                        .save(new AuxiliarMultipleQuestionOption(null, auxProductId, option));
               });
            });
         }
         return new NewAuxiliarProductDTO(auxProductId,
               uploadAuxiliarProductMedias(medias, auxProductId, (frontPage != null ? frontPage.getUrl() : null),
                     (frontPage != null ? frontPage.getType() : null), 0,
                     (segmentMediaArticle != null ? segmentMediaArticle.getUrl() : null),
                     (segmentMediaArticle != null ? segmentMediaArticle.getType() : null)),
               (segmentMediaArticle != null ? segmentMediaArticle : null));
      } else {
         // ! En caso de que se pase el userId por parámetro recurrimos a crear
         // ! directamente el articulo.
         ProductDTO newProductDTO = productService.createProduct(
               (!medias.isEmpty() ? Optional.of(medias.get().get(0)) : Optional.empty()),
               (!title.isEmpty() ? title : Optional.empty()), (!price.isEmpty() ? price : Optional.empty()),
               (!tStock.isEmpty() ? Optional.of(ProductUtils.determinateTypeOfStock(tStock.get()).toString())
                     : Optional.empty()),
               (!stock.isEmpty() ? stock : Optional.empty()), (!status.isEmpty() ? status : Optional.empty()),
               (!userId.isEmpty() ? userId : Optional.empty()),
               (!manufacturingTime.isEmpty() ? manufacturingTime : Optional.empty()),
               Optional.empty(),
               Optional.empty(),
               Optional.empty(),
               (!manufacturingType.isEmpty() ? manufacturingType : Optional.empty()),
               (!segmentTitle.isEmpty() ? segmentTitle : Optional.empty()),
               (!segmentDescription.isEmpty() ? segmentDescription : Optional.empty()),
               (!segmentMedia.isEmpty() ? segmentMedia : Optional.empty()),
               (!hashtagValue.isEmpty() ? hashtagValue : Optional.empty()),
               (!keywordValues.isEmpty() ? keywordValues : Optional.empty()),
               (!productQuestionValue.isEmpty() ? productQuestionValue : Optional.empty()),
               (!productQuestionType.isEmpty() ? productQuestionType : Optional.empty()),
               (!productQuestionLimit.isEmpty()
                     ? productQuestionLimit
                     : Optional.empty()),
               (!productQuestionRequired.isEmpty() ? productQuestionRequired : Optional.empty()),
               (!productQuestionOptions.isEmpty() ? productQuestionOptions : Optional.empty()));
         Map<String, Object> segmentAtributtes = null;
         ProductImageDTO productImageDTO = null;
         if (!segmentMedia.isEmpty()) {
            segmentAtributtes = productSegmentJpaRepository
                  .findLastProductDetailByProductId(newProductDTO.getId());
            productImageDTO = new ProductImageDTO((segmentAtributtes.get("url").toString()),
                  (segmentAtributtes.get("type").toString()));
         }
         return new NewAuxiliarProductDTO(newProductDTO.getId(),
               uploadAuxiliarProductMedias(medias, newProductDTO.getId(),
                     newProductDTO.getFrontPage(), "IMAGE", 1,
                     (productImageDTO != null ? productImageDTO.getUrl() : null),
                     (productImageDTO != null ? productImageDTO.getType() : null)),
               (productImageDTO != null ? productImageDTO : null));
      }
   }

   private List<ProductImageDTO> uploadAuxiliarProductMedias(Optional<List<MultipartFile>> medias, Long auxProductId,
         String frontPageUrl, String frontPageType, Integer operationType, String segmentMediaUrl,
         String segmentMediaType) {
      if (operationType == 0) {
         List<ProductImageDTO> articleProductMedias = new ArrayList<ProductImageDTO>();
         List<AuxiliarProductMedia> auxiliarProductMedias = new ArrayList<AuxiliarProductMedia>();
         if (frontPageUrl != null && frontPageType != null) {
            articleProductMedias.add(new ProductImageDTO(frontPageUrl, frontPageType));
         }
         medias.ifPresent(mediasList -> {
            mediasList.stream()
                  .skip(1) // ! Se salta la primera imagen ya que esta se guarda en el frontPage.
                  .forEach(media -> {
                     var auxiliarProductMedia = new AuxiliarProductMedia(null, auxProductId,
                           firebaseStorageService.uploadFile(media,
                                 "image-product-" + auxProductId.toString() + "-" + UUID.randomUUID().toString(),
                                 "imageProducts"),
                           "IMAGE");
                     // ! Por ahora todas las medias son de tipo image.
                     auxiliarProductMedias.add(auxiliarProductMedia);
                     articleProductMedias.add(new ProductImageDTO(auxiliarProductMedia.getMedia(),
                           auxiliarProductMedia.getType()));
                  });
            auxiliarProductMediaJpaRepository.saveAll(auxiliarProductMedias);
         });
         return articleProductMedias;
      } else {
         List<ProductImageDTO> articleProductMedias = new ArrayList<ProductImageDTO>();
         List<ProductMedia> imageProducts = new ArrayList<ProductMedia>();
         if (frontPageUrl != null && frontPageType != null) {
            articleProductMedias.add(new ProductImageDTO(frontPageUrl, frontPageType));
         }
         medias.ifPresent(mediasList -> {
            mediasList.stream()
                  .skip(1) // ! Se salta la primera imagen ya que esta se guarda en el frontPage.
                  .forEach(media -> {
                     var imageProduct = new ProductMedia(null, auxProductId,
                           firebaseStorageService.uploadFile(media,
                                 "image-product-" + auxProductId.toString() + "-" + UUID.randomUUID().toString(),
                                 "imageProducts"),
                           "IMAGE");
                     // ! Por ahora todas las medias son de tipo image.
                     imageProducts.add(imageProduct);
                     articleProductMedias.add(new ProductImageDTO(imageProduct.getUrl(),
                           imageProduct.getType()));
                  });
            productMediaJpaRepository.saveAll(imageProducts);
         });
         return articleProductMedias;
      }
   }
}
