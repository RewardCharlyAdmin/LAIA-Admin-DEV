package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.MerchantNotFoundException;
import us.kanddys.pov.admin.exceptions.ProductNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.ProductMedia;
import us.kanddys.pov.admin.models.dtos.DifferentProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.dtos.ProductImageDTO;
import us.kanddys.pov.admin.models.dtos.DifferentProductMediaDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.enums.ManufacturingTypeEnum;
import us.kanddys.pov.admin.models.utils.enums.StockTypeEnum;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.UserJpaRepository;
import us.kanddys.pov.admin.services.HashtagService;
import us.kanddys.pov.admin.services.KeywordService;
import us.kanddys.pov.admin.services.ProductQuestionService;
import us.kanddys.pov.admin.services.ProductSegmentService;
import us.kanddys.pov.admin.services.ProductService;
import us.kanddys.pov.admin.services.storage.FirebaseStorageService;

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
   private HashtagService hashtagService;

   @Autowired
   private ProductMediaJpaRepository productMediaJpaRepository;

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
                  // ! FALTA IMPLEMENTAR EL SERVICIO DEL FIREBASE PARA EL FRONT PAGE.
                  new Product(null, merchantId, null,
                        (!title.isPresent() ? null : title.get()),
                        (!price.isPresent() ? Double.valueOf(price.get().toString()) : null),
                        (!stock.isPresent() ? null : Integer.valueOf(stock.get())),
                        (!typeOfSale.isPresent() ? null
                              : (typeOfSale.get().equals("PACKAGE") ? StockTypeEnum.PACKAGE : StockTypeEnum.UNIT)),
                        (!manufacturingType.isPresent() ? null
                              : determinateManufacturingType(manufacturingType.get())),
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
    * Método privado que calcula el tipo de manufacturación del producto.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param manufacturingType
    * @return ManufacturingTypeEnum
    */
   private ManufacturingTypeEnum determinateManufacturingType(String manufacturingType) {
      switch (manufacturingType) {
         case "MH":
            return ManufacturingTypeEnum.MH;
         case "MN":
            return ManufacturingTypeEnum.MN;
         case "HR":
            return ManufacturingTypeEnum.HR;
         case "DY":
            return ManufacturingTypeEnum.DY;
         default:
            return null;
      }
   }

   private void createProductExtraAtributes(Optional<String> productId, Optional<String> invenstmentAmount,
         Optional<String> invenstmentNote, Optional<String> invenstmentTitle, Optional<String> segmentTitle,
         Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia, Optional<String> hashtagValue,
         Optional<List<String>> keywords, Optional<String> sellerQuestionValue, Optional<String> sellerQuestionType,
         Optional<String> sellerQuestionLimit, Optional<String> sellerQuestionRequired,
         Optional<List<String>> sellerQuestionOptions, Long userId) {
      // ! PREGUNTAR A MAXI COMO IMPLEMENTAR ESTO.
      // if (invenstmentAmount.isPresent() || invenstmentNote.isPresent() ||
      // invenstmentTitle.isPresent()) {
      // invenstmentService.createInvenstment(Long.valueOf(productId.get()),
      // Optional.of(Double.valueOf(invenstmentAmount.get())), invenstmentNote,
      // invenstmentTitle);
      // }
      if (segmentDescription.isPresent() || segmentMedia.isPresent() || segmentTitle.isPresent()) {
         productSegmentService.createProductSegment(segmentTitle, segmentMedia, Long.valueOf(productId.get()),
               segmentDescription);
      }
      if (hashtagValue.isPresent()) {
         hashtagService.createHashtag(hashtagValue.get(), Long.valueOf(productId.get()), userId);
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
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'updateAdminSellAssociation'");
   }

   @Override
   public DifferentProductDTO getAdminSellProduct(Long productId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getAdminSellProduct'");
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
      tStock.ifPresent(productToUpdate::);
      productJpaRepository.save(productToUpdate);
      return responseArticleMedias;
   }
}
