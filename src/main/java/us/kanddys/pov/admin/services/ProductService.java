package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;
import us.kanddys.pov.admin.models.dtos.DifferentProductDTO;
import us.kanddys.pov.admin.models.dtos.DifferentProductMediaDTO;
import us.kanddys.pov.admin.models.dtos.ProductDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface ProductService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   @Modifying
   public String updateFrontPage(Long productId, MultipartFile image);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer deleteProduct(Long productId);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public ProductDTO createProduct(Optional<MultipartFile> frontPage, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         String merchantId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValue, Optional<String> productQuestionValue,
         Optional<String> productQuestionType, Optional<String> productQuestionLimit,
         Optional<String> productQuestionRequired, Optional<List<String>> productQuestionOptions,
         Optional<String> categoryTitle);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public DifferentProductDTO getAdminSellProduct(Long productId);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public List<DifferentProductMediaDTO> updateAdminSellProductMedia(Long productId, String index,
         Optional<String> title, Optional<String> price, Optional<String> tPrice, Optional<String> stock,
         Optional<String> tStock, List<String> existImages, List<MultipartFile> newImages);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public ProductDTO getProductById(Long productId);
}
