package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.MerchantNotFoundException;
import us.kanddys.pov.admin.exceptions.ProductNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Hashtag;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.ProductMedia;
import us.kanddys.pov.admin.models.dtos.DifferentProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.dtos.DifferentProductMediaDTO;
import us.kanddys.pov.admin.models.dtos.HashtagDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;
import us.kanddys.pov.admin.repositories.jpa.CategoryJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.HashtagJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.KeywordJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.MerchantJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductQuestionJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentJpaRepository;
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
public class ProductServiceImpl implements ProductService {

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
   private ProductSegmentJpaRepository productSegmentJpaRepository;

   @Autowired
   private KeywordJpaRepository keywordProductJpaRepository;

   @Autowired
   private ProductQuestionJpaRepository productQuestionJpaRepository;

   @Autowired
   private MerchantJpaRepository merchantJpaRepository;

   @Autowired
   private CategoryJpaRepository categoryJpaRepository;

   @Autowired
   private CategoryService categoryService;

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public Integer deleteProduct(Long productId) {
      productJpaRepository.deleteById(productId);
      return 1;
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public ProductDTO createProduct(Optional<MultipartFile> frontPage, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         String merchantId, Optional<String> manufacturing, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywords, Optional<String> productQuestionValue,
         Optional<String> productQuestionType, Optional<String> productQuestionLimit,
         Optional<String> productQuestionRequired, Optional<List<String>> productQuestionOptions,
         Optional<String> categoryTitle) {
      Long existMerchantId = merchantJpaRepository.existById(Long.valueOf(merchantId))
            .orElseThrow(() -> new MerchantNotFoundException(
                  ExceptionMessage.MERCHANT_NOT_FOUND));
      ProductDTO newProductDTO = null;
      // * Se crea el producto asociado a un merchant.
      try {
         newProductDTO = createProductAndDTO(
               new Product(null, existMerchantId, null,
                     (!title.isPresent() ? null : title.get()),
                     (!price.isPresent() ? null : Double.valueOf(price.get().toString())),
                     (!stock.isPresent() ? null : Integer.valueOf(stock.get().toString())),
                     (!tStock.isPresent() ? null
                           : (tStock.get().equals("PACKAGE") ? StockTypeEnum.PACKAGE : StockTypeEnum.UNIT)),
                     (!manufacturingType.isPresent() ? null
                           : ProductUtils.determinateManufacturingType(manufacturingType.get())),
                     (!manufacturing.isPresent() ? null : Integer.valueOf(manufacturing.get())),
                     DateUtils.getCurrentDateWitheoutTime(), null,
                     (!status.isPresent() ? null : Integer.valueOf(status.get())), 0),
               (!frontPage.isPresent() ? null : frontPage.get()));
         createProductExtraAtributes(Optional.of(newProductDTO.getId().toString()),
               invenstmentAmount, invenstmentNote, invenstmentTitle, segmentTitle,
               segmentDescription, segmentMedia, hashtagValue, keywords, productQuestionValue,
               productQuestionType, productQuestionLimit, productQuestionRequired, productQuestionOptions,
               existMerchantId, categoryTitle);
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha");
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
         var categoryId = categoryJpaRepository.findByCategory(categoryTitle.get()).map(t -> t.getId()).orElse(null);
         if (categoryId == null) {
            categoryService.aAdminProductCategory(categoryService.cAdminCategory(categoryTitle.get(), merchantId),
                  List.of(Long.valueOf(productId.get())));
         } else {
            categoryService.aAdminProductCategory(categoryId, List.of(Long.valueOf(productId.get())));
         }
      }
   }

   @Override
   public String updateFrontPage(Long productId, MultipartFile image) {
      var url = firebaseStorageService.uploadFile(image, "front-page-product-", "frontPages");
      productJpaRepository.updateFrontPage(productId,
            url);
      return url;
   }

   @Override
   public DifferentProductDTO getAdminSellProduct(Long productId) {
      ProductDTO productDTO = getProductById(productId);
      if (productDTO == null)
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      DifferentProductDTO differentProductDTO = new DifferentProductDTO();
      differentProductDTO.setProductId(productId);
      Set<String> medias = new HashSet<String>();
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
            (productDTO.getManufacturingType() != null ? productDTO.getManufacturingType().toString() : null));
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
