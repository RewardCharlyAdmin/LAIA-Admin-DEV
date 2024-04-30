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
    * @author Igirod0
    * @version 1.0.0
    */
   public NewAuxiliarProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> tStock, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> merchant, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
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
   public Long updateAdminSellAssociation(Long productId, Long userId);
}
