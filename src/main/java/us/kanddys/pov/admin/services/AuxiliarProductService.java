package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.dtos.NewAuxiliarProductDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface AuxiliarProductService {

   /**
    * Este método tiene la obligación de crear un producto auxiliar.
    *
    * @param frontPage
    * @param title
    * @param typeOfSale
    * @param price
    * @param stock
    * @param status
    * @param userId
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
    * @param categoryTitle
    * @param typeOfPrice
    * @return Integer
    */
   public NewAuxiliarProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValue, Optional<String> sellerQuestionValue,
         Optional<String> sellerQuestionType, Optional<String> sellerQuestionLimit,
         Optional<String> sellerQuestionRequired, Optional<List<String>> sellerQuestionOptions);

   /**
    * Este método se encarga de subir los medias de un producto auxiliar.
    * 
    * @author Igirod0
    * @version 1.0.0
    * @param medias
    * @param productId
    * @return Integer
    */
   public Integer uploadMedias(List<MultipartFile> medias, Long productId);
}
