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
    * Este método tiene la obligación de actualizar el frontPage de un producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param valueOf
    * @param image
    * @return Integer
    */
   @Modifying
   public String updateFrontPage(Long productId, MultipartFile image);

   /**
    * Este método tiene la obligación de eliminar un producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return Integer
    */
   public Integer deleteProduct(Long productId);

   /**
    * Este método tiene la obligación de crear un producto.
    * 
    *
    * @author Igirod0
    * @version 1.0.0
    * @param frontPage
    * @param userId
    * @param productId
    * @param title
    * @param typeOfSale
    * @param price
    * @param stock
    * @param status
    * @param manufacturingTime
    * @param invenstmentNote
    * @param invenstmentAmount
    * @param invenstmentTitle
    * @param manufacturingType
    * @param segmentTitle
    * @param segmentDescription
    * @param segmentMedia
    * @param hashtagValue
    * @param keywordValue
    * @param sellerQuestionValue
    * @param sellerQuestionType
    * @param sellerQuestionLimit
    * @param sellerQuestionRequired
    * @param typeOfPrice
    * @param sellerQuestionOptions
    * @return Long
    */
   public ProductDTO createProduct(Optional<MultipartFile> frontPage, Optional<String> title,
         Optional<String> typeOfSale, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValue, Optional<String> sellerQuestionValue,
         Optional<String> sellerQuestionType, Optional<String> sellerQuestionLimit,
         Optional<String> sellerQuestionRequired, Optional<List<String>> sellerQuestionOptions);

   /**
    * Método que tiene la obligación de asociar un producto a un comerciante.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @param userId
    * @return Long
    */
   public Long updateAdminSellAssociation(Long productId, Long userId);

   /**
    * Método que tiene la obligación de obtener un producto por su id.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return ArticleDTO
    */
   public DifferentProductDTO getAdminSellProduct(Long productId);

   /**
    * Método que tiene la obligación de actualizar las medias de un producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @param index
    * @param title
    * @param price
    * @param tPrice
    * @param stock
    * @param tStock
    * @param existImages
    * @param newImages
    * @return List<ArticleImageDTO>
    */
   public List<DifferentProductMediaDTO> updateAdminSellProductMedia(Long productId, String index,
         Optional<String> title,
         Optional<String> price, Optional<String> tPrice, Optional<String> stock, Optional<String> tStock,
         List<String> existImages, List<MultipartFile> newImages);

   /**
    * Método que tiene la obligación de obtener un producto por su id.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return ProductDTO
    */
   public ProductDTO getProductById(Long productId);
}
