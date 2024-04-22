package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.MerchantNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Product;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.UserJpaRepository;
import us.kanddys.pov.admin.services.ProductService;

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
         Optional<String> sellerQuestionRequired, Optional<String> typeOfPrice,
         Optional<List<String>> sellerQuestionOptions) {
      var userid = Long.valueOf(userId.get());
      var merchantId = userJpaRepository.existIdByUserId(userid);
      ProductDTO newProductDTO = null;
      if (merchantId == null)
         throw new MerchantNotFoundException(ExceptionMessage.MERCHANT_NOT_FOUND);
      else {
         // * Se crea el producto asociado a un merchant.
         try {
            newProductDTO = createProductAndDTO(
                  new Product(null, (!title.isEmpty() ? title.get() : null),
                        (!price.isEmpty() ? Double.valueOf(price.get()) : null),
                        (!stock.isEmpty() ? Integer.valueOf(stock.get()) : null), null,
                        merchantId, (!status.isEmpty() ? Integer.valueOf(status.get()) : null),
                        DateUtils.getCurrentDate(), (!typeOfSale.isEmpty() ? typeOfSale.get() : null)),
                  (!frontPage.isEmpty() ? frontPage.get() : null));
            createProductExtraAtributes(Optional.of(newProductDTO.getId().toString()), manufacturingTime,
                  invenstmentAmount, invenstmentNote, invenstmentTitle, manufacturingType, segmentTitle,
                  segmentDescription, segmentMedia, hashtagValue, keywords, sellerQuestionValue,
                  sellerQuestionType, sellerQuestionLimit, sellerQuestionRequired, sellerQuestionOptions, userid);
         } catch (ParseException e) {
            throw new RuntimeException("Error al convertir la fecha");
         }
      }
      return newProductDTO;
   }
}
