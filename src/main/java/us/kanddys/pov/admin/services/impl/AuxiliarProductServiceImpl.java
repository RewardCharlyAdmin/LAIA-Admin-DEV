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

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.ProductNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.AuxiliarMultipleQuestionOption;
import us.kanddys.pov.admin.models.AuxiliarProduct;
import us.kanddys.pov.admin.models.AuxiliarProductCategory;
import us.kanddys.pov.admin.models.AuxiliarProductKeyword;
import us.kanddys.pov.admin.models.AuxiliarProductMedia;
import us.kanddys.pov.admin.models.AuxiliarProductQuestion;
import us.kanddys.pov.admin.models.Hashtag;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.ProductMedia;
import us.kanddys.pov.admin.models.dtos.NewAuxiliarProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductImageDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarMultipleQuestionOptionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductCategoryJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductKeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductSegmentJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.HashtagJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentJpaRepository;
import us.kanddys.pov.admin.services.AuxiliarProductService;
import us.kanddys.pov.admin.services.CategoryService;
import us.kanddys.pov.admin.services.KeywordService;
import us.kanddys.pov.admin.services.ProductQuestionService;
import us.kanddys.pov.admin.services.ProductSegmentService;
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
   private AuxiliarProductSegmentJpaRepository auxiliarProductSegmentJpaRepository;

   @Autowired
   private AuxiliarProductQuestionJpaRepository auxiliarProductQuestionJpaRepository;

   @Autowired
   private AuxiliarProductKeywordJpaRepository auxiliarKeywordJpaRepository;

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
   private ProductSegmentService productSegmentService;

   @Autowired
   private ProductService productService;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Autowired
   private HashtagJpaRepository hashtagJpaRepository;

   @Autowired
   private KeywordService keywordService;

   @Autowired
   private ProductQuestionService productQuestionService;

   @Autowired
   private CategoryService categoryService;

   @Autowired
   private AuxiliarProductCategoryJpaRepository auxiliarProductCategoryJpaRepository;

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public NewAuxiliarProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> merchant, Optional<String> manufacturing, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValues, Optional<String> productQuestionValue,
         Optional<String> productQuestionType, Optional<String> productQuestionLimit,
         Optional<String> productQuestionRequired, Optional<List<String>> productQuestionOptions,
         Optional<String> categoryTitle) {
      if (merchant.isEmpty()) {
         Long auxProductId;
         try {
            auxProductId = auxiliarProductJpaRepository.save(new AuxiliarProduct(null, null, title.get(),
                  Double.parseDouble(price.get()), Integer.parseInt(stock.get()), (tStock.isPresent()
                        ? ProductUtils.determinateTypeOfStock(tStock.get())
                        : null),
                  hashtagValue.get(),
                  (manufacturingType.isPresent() ? ProductUtils.determinateManufacturingType(manufacturingType.get())
                        : null),
                  (manufacturing.isPresent() ? Integer.parseInt(manufacturing.get()) : null),
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
         if (productQuestionType.isPresent()) {
            auxiliarProductQuestionJpaRepository
                  .save(new AuxiliarProductQuestion(null, auxProductId,
                        (productQuestionValue.get() != null ? productQuestionValue.get() : null),
                        (productQuestionType.get() != null
                              ? ProductUtils.determinateProductQuestionType(productQuestionType.get())
                              : null),
                        (productQuestionLimit.isPresent() ? Integer.parseInt(productQuestionLimit.get()) : null),
                        (productQuestionRequired.isPresent() ? Integer.parseInt(productQuestionRequired.get())
                              : null)));
            if (productQuestionType.get().equals("MULTIPLE")) {
               // ! Agregar la pregunta primero.
               auxiliarMultipleQuestionJpaRepository
                     .save(new AuxiliarProductQuestion(null, auxProductId, productQuestionValue.get(),
                           ProductUtils.determinateProductQuestionType(productQuestionType.get()),
                           Integer.parseInt(productQuestionLimit.get()),
                           Integer.parseInt(productQuestionRequired.get())));
               productQuestionOptions.ifPresent(options -> {
                  options.forEach(option -> {
                     // ! Se agrega cada opción de la pregunta multiple.
                     auxiliarMultipleQuestionOptionJpaRepository
                           .save(new AuxiliarMultipleQuestionOption(null, auxProductId, option));
                  });
               });
            }
         }
         if (categoryTitle.isPresent()) {
            auxiliarProductCategoryJpaRepository
                  .save(new AuxiliarProductCategory(null, auxProductId, categoryTitle.get()));
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
               (!title.isEmpty() ? title : Optional.empty()),
               (!tStock.isEmpty() ? Optional.of(ProductUtils.determinateTypeOfStock(tStock.get()).toString())
                     : Optional.empty()),
               (!price.isEmpty() ? price : Optional.empty()),
               (!stock.isEmpty() ? stock : Optional.empty()), (!status.isEmpty() ? status : Optional.empty()),
               merchant.get(),
               (!manufacturing.isEmpty() ? manufacturing : Optional.empty()),
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
               (!productQuestionOptions.isEmpty() ? productQuestionOptions : Optional.empty()),
               (!categoryTitle.isEmpty() ? categoryTitle : Optional.empty()));
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

   @Override
   public Long updateAdminSellAssociation(Long productId, Long userId) {
      Optional<AuxiliarProduct> auxiliarProduct = auxiliarProductJpaRepository.findById(productId);
      Product product = null;
      if (auxiliarProduct.isPresent()) {
         product = null;
         product = new Product(null, userId,
               (auxiliarProduct.get().getFrontPage() != null ? auxiliarProduct.get().getFrontPage() : null),
               (auxiliarProduct.get().getTitle() != null ? auxiliarProduct.get().getTitle() : null),
               (auxiliarProduct.get().getPrice() != null ? auxiliarProduct.get().getPrice() : null),
               (auxiliarProduct.get().getStock() != null ? auxiliarProduct.get().getStock() : null),
               (auxiliarProduct.get().getStockType() != null ? auxiliarProduct.get().getStockType() : null),
               (auxiliarProduct.get().getManufacturingType() != null ? auxiliarProduct.get().getManufacturingType()
                     : null),
               (auxiliarProduct.get().getManufacturing() != null ? auxiliarProduct.get().getManufacturing()
                     : null),
               (auxiliarProduct.get().getCreated() != null ? auxiliarProduct.get().getCreated() : null),
               (auxiliarProduct.get().getUpdated() != null ? auxiliarProduct.get().getUpdated() : null),
               (auxiliarProduct.get().getStatus() != null ? auxiliarProduct.get().getStatus() : null),
               (auxiliarProduct.get().getSales() != null ? auxiliarProduct.get().getSales() : null));
         product = productJpaRepository.save(product);
         var auxiliarProductSegment = auxiliarProductSegmentJpaRepository.findById(productId);
         List<String> auxiliarProductKeywords = auxiliarKeywordJpaRepository
               .findKeywordsByIds(auxiliarProductKeyWordJpaRepository.findByProduct(productId));
         var auxiliarProductCategory = auxiliarProductCategoryJpaRepository.findByProduct(productId);
         var auxiliarProductQuestion = auxiliarProductQuestionJpaRepository.findByProduct(productId);
         createProductExtraAtributes(Optional.of(product.getId().toString()),
               // ! AGREGAR INVENTSMENT.
               Optional.empty(),
               Optional.empty(),
               Optional.empty(),
               Optional.empty(),
               Optional.empty(),
               Optional.empty(),
               (auxiliarProduct.get().getHashtag() != null ? Optional.of(auxiliarProduct.get().getHashtag())
                     : Optional.empty()),
               (!auxiliarProductKeywords.isEmpty()
                     ? Optional.of(auxiliarProductKeywords)
                     : Optional.empty()),
               (auxiliarProductQuestion.getQuestion() != null
                     ? Optional.of(auxiliarProductQuestion.getQuestion())
                     : Optional.empty()),
               (auxiliarProductQuestion.getType() != null
                     ? Optional.of(auxiliarProductQuestion.getType().toString())
                     : Optional.empty()),
               (auxiliarProductQuestion.getMaxLimit() != null
                     ? Optional.of(auxiliarProductQuestion.getMaxLimit().toString())
                     : Optional.empty()),
               (auxiliarProductQuestion.getRequired() != null
                     ? Optional.of(auxiliarProductQuestion.getRequired().toString())
                     : Optional.empty()),
               Optional.of(auxiliarMultipleQuestionOptionJpaRepository.findOptionsByQuestionId(productId)),
               userId, (auxiliarProductCategory.getTitle() != null ? Optional.of(auxiliarProductCategory.getTitle())
                     : Optional.empty()));
         if (auxiliarProductSegment.isPresent()) {
            productSegmentService.createProductSegmentMediaString(
                  (auxiliarProductSegment.get().getTitle() != null
                        ? Optional.of(auxiliarProductSegment.get().getTitle())
                        : null),
                  (auxiliarProductSegment.get().getMedia() != null
                        ? Optional.of(auxiliarProductSegment.get().getMedia())
                        : null),
                  productId,
                  (auxiliarProductSegment.get().getDescription() != null
                        ? Optional.of(auxiliarProductSegment.get().getDescription())
                        : Optional.empty()));
         }
      } else {
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      }
      // ! Borrado del producto auxiliar.
      auxiliarMultipleQuestionOptionJpaRepository.deleteOptionsByProductId(productId);
      auxiliarProductKeyWordJpaRepository.deleteWordsByProductId(productId);
      auxiliarProductJpaRepository.deleteById(productId);
      return product.getId();
   }

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   private void createProductExtraAtributes(Optional<String> productId, Optional<String> invenstmentAmount,
         Optional<String> invenstmentNote, Optional<String> invenstmentTitle, Optional<String> segmentTitle,
         Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia, Optional<String> hashtagValue,
         Optional<List<String>> keywords, Optional<String> sellerQuestionValue, Optional<String> sellerQuestionType,
         Optional<String> sellerQuestionLimit, Optional<String> sellerQuestionRequired,
         Optional<List<String>> sellerQuestionOptions, Long merchantId, Optional<String> categoryTitle) {
      // ! MAS ADELANTE.
      // if (invenstmentAmount.isPresent() || invenstmentNote.isPresent() ||
      // invenstmentTitle.isPresent()) {
      // invenstmentService.createInvenstment(Long.valueOf(productId.get()),
      // Optional.of(Double.valueOf(invenstmentAmount.get())), invenstmentNote,
      // invenstmentTitle);
      // }
      if (hashtagValue.isPresent()) {
         hashtagJpaRepository.save(new Hashtag(null, Long.valueOf(productId.get()), hashtagValue.get(), merchantId));
      }
      if (keywords.isPresent()) {
         keywordService.createKeywords(keywords.get(), merchantId, Long.valueOf(productId.get()));
      }
      if (sellerQuestionValue.isPresent() && sellerQuestionType.isPresent()) {
         productQuestionService.createQuestion(sellerQuestionValue.get(),
               (sellerQuestionRequired.isPresent() ? Optional.of(Integer.valueOf(sellerQuestionRequired.get())) : null),
               sellerQuestionType.get(),
               (sellerQuestionLimit.isPresent() ? Optional.of(Integer.valueOf(sellerQuestionLimit.get())) : null),
               Long.valueOf(productId.get()),
               sellerQuestionOptions);
      }
      if (segmentDescription.isPresent() || segmentMedia.isPresent() || segmentTitle.isPresent()) {
         productSegmentService.createProductSegment(segmentTitle, segmentMedia, Long.valueOf(productId.get()),
               segmentDescription);
      }
      if (categoryTitle.isPresent()) {
         var categoryId = categoryService.getCategoryIdByTitle(categoryTitle.get());
         if (categoryId == null) {
            categoryService.cAdminCategory(categoryTitle.get(), merchantId);
         } else {
            categoryService.aAdminProductCategory(categoryId, List.of(Long.valueOf(productId.get())));
         }
      }
   }
}
