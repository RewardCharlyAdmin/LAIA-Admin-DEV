package us.kanddys.pov.admin.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.AuxiliarProduct;
import us.kanddys.pov.admin.models.AuxiliarProductKeyword;
import us.kanddys.pov.admin.models.dtos.NewAuxiliarProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductImageDTO;
import us.kanddys.pov.admin.models.dtos.ProductMediaDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductJpaRepository;
import us.kanddys.pov.admin.services.AuxiliarProductService;
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

   @Override
   public NewAuxiliarProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValue, Optional<String> sellerQuestionValue,
         Optional<String> sellerQuestionType, Optional<String> sellerQuestionLimit,
         Optional<String> sellerQuestionRequired, Optional<List<String>> sellerQuestionOptions) {
      return null;
      if (userId.isEmpty()) {
         var auxProductId = auxiliarProductJpaRepository.save(new AuxiliarProduct(null, null, title.get(),
               Double.parseDouble(price.get()), Integer.parseInt(stock.get()), (tStock.isPresent()
                     ? ProductUtils.determinateTypeOfStock(tStock.get())
                     : null),
               hashtagValue.get(),
               (manufacturingType.isPresent() ? ProductUtils.determinateManufacturingType(manufacturingType.get())
                     : null),
               Integer.parseInt(manufacturingTime.get()),
               DateUtils.getCurrentDate(), DateUtils.getCurrentDate(), Integer.parseInt(status.get()), 0)).getId();
         // ! SEGUIR AQUI.
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
         if (keywordValue.isPresent()) {
            auxiliarProductKeyWordJpaRepository.saveAll(keywordValue.get().stream()
                  .map(keyword -> new AuxiliarProductKeyword(null, auxProductId, keyword))
                  .collect(Collectors.toList()));
         }
         if (sellerQuestionType.isPresent() &&
               sellerQuestionType.get().equals("MULTIPLE")) {
            sellerQuestionOptions.ifPresent(options -> {
               options.forEach(option -> {
                  // ! Se agrega cada opción de la pregunta multiple.
                  auxiliarMultipleQuestionRepository
                        .save(new AuxiliarMultipleOptionQuestion(null, auxProductId, option));
               });
            });
         }
         return new NewArticleDTO(auxProductId,
               uploadProductMedias(medias, auxProductId, (frontPage != null ? frontPage.getUrl() : null),
                     (frontPage != null ? frontPage.getType() : null), 0,
                     (segmentMediaArticle != null ? segmentMediaArticle.getUrl() : null),
                     (segmentMediaArticle != null ? segmentMediaArticle.getType() : null)),
               (segmentMediaArticle != null ? segmentMediaArticle : null));
      } else {
         // ! En caso de que se pase el userId por parámetro recurrimos a crear
         // ! directamente el articulo.
         ProductDTO newProductDTO = productService.createProduct(
               (!medias.isEmpty() ? Optional.of(medias.get().get(0)) : Optional.empty()),
               (!title.isEmpty() ? title : Optional.empty()),
               (!typeOfSale.isEmpty() ? typeOfSale : Optional.empty()), (!price.isEmpty() ? price : Optional.empty()),
               (!stock.isEmpty() ? stock : Optional.empty()), (!status.isEmpty() ? status : Optional.empty()),
               (!userId.isEmpty() ? userId : Optional.empty()),
               (!manufacturingTime.isEmpty() ? manufacturingTime : Optional.empty()),
               (!invenstmentNote.isEmpty() ? invenstmentNote : Optional.empty()),
               (!invenstmentAmount.isEmpty() ? invenstmentAmount : Optional.empty()),
               (!invenstmentTitle.isEmpty() ? invenstmentTitle : Optional.empty()),
               (!manufacturingType.isEmpty() ? manufacturingType : Optional.empty()),
               (!segmentTitle.isEmpty() ? segmentTitle : Optional.empty()),
               (!segmentDescription.isEmpty() ? segmentDescription : Optional.empty()),
               (!segmentMedia.isEmpty() ? segmentMedia : Optional.empty()),
               (!hashtagValue.isEmpty() ? hashtagValue : Optional.empty()),
               (!keywords.isEmpty() ? keywords : Optional.empty()),
               (!sellerQuestionValue.isEmpty() ? sellerQuestionValue : Optional.empty()),
               (!sellerQuestionType.isEmpty() ? sellerQuestionType : Optional.empty()),
               (!sellerQuestionLimit.isEmpty()
                     ? sellerQuestionLimit
                     : Optional.empty()),
               (!sellerQuestionRequired.isEmpty() ? sellerQuestionRequired : Optional.empty()), (!typeOfPrice.isEmpty()
                     ? typeOfPrice
                     : Optional.empty()),
               (!sellerQuestionOptions.isEmpty() ? sellerQuestionOptions : Optional.empty()));
         Map<String, Object> segmentAtributtes = null;
         ArticleImageDTO segmentMediaArticle = null;
         if (!segmentMedia.isEmpty()) {
            segmentAtributtes = productDetailJpaRepository
                  .findLastProductDetailByProductId(newProductDTO.getId());
            segmentMediaArticle = new ArticleImageDTO((segmentAtributtes.get("url").toString()),
                  (segmentAtributtes.get("type").toString()));
         }
         return new NewArticleDTO(newProductDTO.getId(),
               uploadProductMedias(medias, newProductDTO.getId(),
                     newProductDTO.getFrontPage(), "IMAGE", 1,
                     (segmentMediaArticle != null ? segmentMediaArticle.getUrl() : null),
                     (segmentMediaArticle != null ? segmentMediaArticle.getType() : null)),
               (segmentMediaArticle != null ? segmentMediaArticle : null));
      }
   }

   @Override
   public Integer uploadMedias(List<MultipartFile> medias, Long productId) {
      medias.stream().forEach(media -> {
         firebaseStorageService.uploadFile(media,
               "auxiliar-image-" + productId.toString() + "-" + UUID.randomUUID().toString(), "auxiliarImages");
      });
      return 1;
   }

}