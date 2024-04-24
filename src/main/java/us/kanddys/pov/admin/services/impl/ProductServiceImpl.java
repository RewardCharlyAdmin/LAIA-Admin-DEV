package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.HashtagAlreadyExistException;
import us.kanddys.pov.admin.exceptions.MerchantNotFoundException;
import us.kanddys.pov.admin.exceptions.ProductNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.AuxiliarProduct;
import us.kanddys.pov.admin.models.Hashtag;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.ProductMedia;
import us.kanddys.pov.admin.models.dtos.DifferentProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.dtos.DifferentProductMediaDTO;
import us.kanddys.pov.admin.models.dtos.HashtagDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarKeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarMultipleQuestionOptionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductKeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.AuxiliarProductSegmentJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.HashtagJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.KeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.UserJpaRepository;
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
public class ProductServiceImpl implements ProductService {

   @Autowired
   private UserJpaRepository userJpaRepository;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Autowired
   private ProductSegmentService productSegmentService;

   @Autowired
   private FirebaseStorageService firebaseStorageService;

   @Autowired
   private ProductQuestionService productQuestionService;

   @Autowired
   private KeywordService keywordService;

   @Autowired
   private HashtagJpaRepository hashtagJpaRepository;

   @Autowired
   private ProductMediaJpaRepository productMediaJpaRepository;

   @Autowired
   private AuxiliarProductJpaRepository auxiliarProductJpaRepository;

   @Autowired
   private AuxiliarProductSegmentJpaRepository auxiliarProductSegmentJpaRepository;

   @Autowired
   private AuxiliarProductKeywordJpaRepository auxiliarProductKeyWordJpaRepository;

   @Autowired
   private AuxiliarKeywordJpaRepository auxiliarKeywordJpaRepository;

   @Autowired
   private AuxiliarProductQuestionJpaRepository auxiliarProductQuestionJpaRepository;

   @Autowired
   private ProductSegmentJpaRepository productSegmentJpaRepository;

   @Autowired
   private AuxiliarMultipleQuestionOptionJpaRepository auxiliarMultipleQuestionOptionJpaRepository;

   @Autowired
   private KeywordJpaRepository keywordProductJpaRepository;

   @Autowired
   private ProductQuestionJpaRepository productQuestionJpaRepository;

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Integer deleteProduct(Long productId) {
      productJpaRepository.deleteById(productId);
      return 1;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public ProductDTO createProduct(Optional<MultipartFile> frontPage, Optional<String> title,
         Optional<String> typeOfSale, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywords, Optional<String> sellerQuestionValue,
         Optional<String> sellerQuestionType, Optional<String> sellerQuestionLimit,
         Optional<String> sellerQuestionRequired, Optional<List<String>> sellerQuestionOptions) {
      var userid = Long.valueOf(userId.get());
      var merchantId = userJpaRepository.existIdByUserId(userid);
      ProductDTO newProductDTO = null;
      if (merchantId == null)
         throw new MerchantNotFoundException(ExceptionMessage.MERCHANT_NOT_FOUND);
      else {
         // * Se crea el producto asociado a un merchant.
         try {
            newProductDTO = createProductAndDTO(
                  new Product(null, merchantId, null,
                        (!title.isPresent() ? null : title.get()),
                        (!price.isPresent() ? Double.valueOf(price.get().toString()) : null),
                        (!stock.isPresent() ? null : Integer.valueOf(stock.get())),
                        (!typeOfSale.isPresent() ? null
                              : (typeOfSale.get().equals("PACKAGE") ? StockTypeEnum.PACKAGE : StockTypeEnum.UNIT)),
                        (!manufacturingType.isPresent() ? null
                              : ProductUtils.determinateManufacturingType(manufacturingType.get())),
                        (!manufacturingTime.isPresent() ? null : Integer.valueOf(manufacturingTime.get())),
                        DateUtils.getCurrentDateWitheoutTime(), null,
                        (!status.isPresent() ? null : Integer.valueOf(status.get())), 0),
                  (!frontPage.isPresent() ? null : frontPage.get()));
            createProductExtraAtributes(Optional.of(newProductDTO.getId().toString()),
                  invenstmentAmount, invenstmentNote, invenstmentTitle, segmentTitle,
                  segmentDescription, segmentMedia, hashtagValue, keywords, sellerQuestionValue,
                  sellerQuestionType, sellerQuestionLimit, sellerQuestionRequired, sellerQuestionOptions, userid);
         } catch (ParseException e) {
            throw new RuntimeException("Error al convertir la fecha");
         }
      }
      return newProductDTO;
   }

   private ProductDTO createProductAndDTO(Product product, MultipartFile frontPage) {
      var productDTO = new ProductDTO(productJpaRepository.save(product));
      // ! Carga solo la portada.
      if (frontPage != null)
         productDTO.setFrontPage(updateFrontPage(productDTO.getId(), frontPage));
      return productDTO;
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
         Optional<List<String>> sellerQuestionOptions, Long userId) {
      // ! MAS ADELANTE.
      // if (invenstmentAmount.isPresent() || invenstmentNote.isPresent() ||
      // invenstmentTitle.isPresent()) {
      // invenstmentService.createInvenstment(Long.valueOf(productId.get()),
      // Optional.of(Double.valueOf(invenstmentAmount.get())), invenstmentNote,
      // invenstmentTitle);
      // }
      if (hashtagValue.isPresent()) {
         if (hashtagJpaRepository.findIdByValueAndUser(hashtagValue.get(), userId).isEmpty()) {
            hashtagJpaRepository.save(new Hashtag(null, Long.valueOf(productId.get()), hashtagValue.get(), userId));
         } else {
            throw new HashtagAlreadyExistException(ExceptionMessage.EXISTING_HASHTAG);
         }
      }
      if (keywords.isPresent()) {
         keywordService.createKeywords(keywords.get(), userId, Long.valueOf(productId.get()));
      }
      if (sellerQuestionValue.isPresent() && sellerQuestionType.isPresent()) {
         productQuestionService.createQuestion(sellerQuestionValue.get(),
               (sellerQuestionRequired.isPresent() ? Optional.of(Integer.valueOf(sellerQuestionRequired.get())) : null),
               sellerQuestionType.get(),
               (sellerQuestionLimit.isPresent() ? Optional.of(Integer.valueOf(sellerQuestionLimit.get())) : null),
               Long.valueOf(productId.get()),
               sellerQuestionOptions);
      }
      // ! FALTA IMPLEMENTAR.
      // if (categoryTitle.isPresent()) {
      // var categoryId = categoryService.getCategoryIdByTitle(categoryTitle.get());
      // if (categoryId == null) {
      // categoryProductService.createCategoryProduct(categoryService.createCategory(categoryTitle.get()),
      // Long.valueOf(productId.get()));
      // } else {
      // categoryProductService.createCategoryProduct(categoryId,
      // Long.valueOf(productId.get()));
      // }
      // }
   }

   @Override
   public String updateFrontPage(Long productId, MultipartFile image) {
      var url = firebaseStorageService.uploadFile(image, "front-page-product-", "frontPages");
      productJpaRepository.updateFrontPage(productId,
            url);
      return url;
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
               (auxiliarProduct.get().getManufacturingTime() != null ? auxiliarProduct.get().getManufacturingTime()
                     : null),
               (auxiliarProduct.get().getCreated() != null ? auxiliarProduct.get().getCreated() : null),
               (auxiliarProduct.get().getUpdated() != null ? auxiliarProduct.get().getUpdated() : null),
               (auxiliarProduct.get().getStatus() != null ? auxiliarProduct.get().getStatus() : null),
               (auxiliarProduct.get().getSales() != null ? auxiliarProduct.get().getSales() : null));
         product = productJpaRepository.save(product);
         var auxiliarProductSegment = auxiliarProductSegmentJpaRepository.findById(productId);
         List<String> auxiliarProductKeywords = auxiliarKeywordJpaRepository
               .findKeywordsByIds(auxiliarProductKeyWordJpaRepository.findByProduct(productId));
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
               userId);
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

   @Override
   public DifferentProductDTO getAdminSellProduct(Long productId) {
      ProductDTO productDTO = getProductById(productId);
      if (productDTO == null)
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      DifferentProductDTO differentProductDTO = new DifferentProductDTO();
      differentProductDTO.setProductId(productId);
      List<String> medias = new ArrayList<String>();
      String frontPage = (productDTO.getFrontPage() != null ? productDTO.getFrontPage() : null);
      if (frontPage != null)
         medias.add(frontPage);
      medias.addAll(productMediaJpaRepository.findAllByProduct(productId).stream().map(t -> t.getUrl())
            .collect(Collectors.toList()));
      differentProductDTO.setMedias(medias);
      // ! ANALIZAR CON MAXI.
      // articleDTO.setInvenstmentsCount(invenstmentJpaRepository.countInvenstmentsByProductId(id));
      differentProductDTO
            .setManufacturing((productDTO.getManufacturing() != null ? productDTO.getManufacturing()
                  : null));
      differentProductDTO.setManufacturingType(
            (productDTO.getManufacturingType() != null ? productDTO.getManufacturingType() : null));
      differentProductDTO.setTitle((productDTO.getTitle() != null ? productDTO.getTitle() : null));
      differentProductDTO.setPrice((productDTO.getPrice() != null ? productDTO.getPrice() : null));
      differentProductDTO.setStock((productDTO.getStock() != null ? productDTO.getStock() : null));
      differentProductDTO.setSegments(productSegmentJpaRepository.countProductDetailsByProductId(productId));
      Optional<Hashtag> hashtag = hashtagJpaRepository.findByProductId(productId);
      if (hashtag.isPresent())
         differentProductDTO.setHashtag(new HashtagDTO(hashtag.get()));
      differentProductDTO.setKeywords(keywordProductJpaRepository.countKeyWordProductByProductId(productId));
      differentProductDTO.setQuestions(productQuestionJpaRepository.countQuestionsByProductId(productId));
      return differentProductDTO;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public List<DifferentProductMediaDTO> updateAdminSellProductMedia(Long productId, String index,
         Optional<String> title,
         Optional<String> price,
         Optional<String> tPrice, Optional<String> stock, Optional<String> tStock,
         List<String> existImages,
         List<MultipartFile> newImages) {
      String[] indexArray = index.split(" ");
      Optional<Product> product = productJpaRepository.findById(productId);
      if (product.isEmpty())
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      List<DifferentProductMediaDTO> newArticleImagesDTOs = new ArrayList<DifferentProductMediaDTO>();
      List<ProductMedia> newImageProducts = new ArrayList<ProductMedia>();
      var productToUpdate = product.get();
      if (indexArray[0].equals("E")) {
         newImages.forEach(t -> {
            DifferentProductMediaDTO DifferentProductMediaDTO = new DifferentProductMediaDTO(
                  firebaseStorageService.uploadFile(t,
                        "image-product-" + productToUpdate.getId().toString() + "-" + UUID.randomUUID().toString(),
                        "imageProducts"),
                  "IMAGE");
            newArticleImagesDTOs.add(DifferentProductMediaDTO);
            newImageProducts
                  .add(
                        new ProductMedia(null, productToUpdate.getId(), DifferentProductMediaDTO.getUrl(), "IMAGE"));
         });
      }
      if (indexArray[0].equals("N")) {
         // * Se sube a Firebase la nueva portada del producto.
         newArticleImagesDTOs.add(new DifferentProductMediaDTO(
               firebaseStorageService.uploadFile(newImages.get(0),
                     "front-page-product-" + productToUpdate.getId().toString(),
                     "frontPages"),
               "IMAGE"));
         productJpaRepository.updateFrontPage(productId, newArticleImagesDTOs.get(0).getUrl());
         newImages.stream().skip(1).forEach(t -> {
            DifferentProductMediaDTO DifferentProductMediaDTO = new DifferentProductMediaDTO(
                  firebaseStorageService.uploadFile(t,
                        "image-product-" + productToUpdate.getId().toString() + "-" + UUID.randomUUID().toString(),
                        "imageProducts"),
                  "IMAGE");
            newArticleImagesDTOs.add(DifferentProductMediaDTO);
            newImageProducts
                  .add(
                        new ProductMedia(null, productToUpdate.getId(), DifferentProductMediaDTO.getUrl(), "IMAGE"));
         });
      }
      // * Se guardan las nuevas imagenes.
      productMediaJpaRepository.saveAll(newImageProducts);
      List<DifferentProductMediaDTO> responseArticleMedias = new ArrayList<>();
      var indexE = 0;
      var indexN = 0;
      for (String existIndex : indexArray) {
         if (existIndex.equals("E")) {
            responseArticleMedias
                  .add(new DifferentProductMediaDTO(existImages.get(indexE), "IMAGE"));
            indexE = indexE + 1;
         }
         if (existIndex.equals("N")) {
            responseArticleMedias.add(new DifferentProductMediaDTO(
                  newArticleImagesDTOs.get(indexN).getUrl(), newArticleImagesDTOs.get(indexN).getType()));
            indexN = indexN + 1;
         }
      }
      title.ifPresent(productToUpdate::setTitle);
      price.ifPresent(t -> productToUpdate.setPrice(Double.valueOf(t)));
      stock.ifPresent(t -> productToUpdate.setStock(Integer.valueOf(t)));
      tStock.ifPresent(t -> productToUpdate.setStockType(ProductUtils.determinateTypeOfStock(tStock.get())));
      productJpaRepository.save(productToUpdate);
      return responseArticleMedias;
   }

   @Override
   public ProductDTO getProductById(Long productId) {
      var product = productJpaRepository.findById(productId);
      if (product.isEmpty())
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      return new ProductDTO(product.get());
   }
}
