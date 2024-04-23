package us.kanddys.pov.admin.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.dtos.NewProductDTO;
import us.kanddys.pov.admin.services.AuxiliarProductService;
import us.kanddys.pov.admin.services.storage.FirebaseStorageService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class AuxiliarProductServiceImpl implements AuxiliarProductService {

   @Autowired
   private FirebaseStorageService firebaseStorageService;

   @Override
   public NewProductDTO createAuxiliarProduct(Optional<List<MultipartFile>> medias, Optional<String> title,
         Optional<String> typeOfSale, Optional<String> price, Optional<String> stock, Optional<String> status,
         Optional<String> userId, Optional<String> manufacturingTime, Optional<String> invenstmentNote,
         Optional<String> invenstmentAmount, Optional<String> invenstmentTitle, Optional<String> manufacturingType,
         Optional<String> segmentTitle, Optional<String> segmentDescription, Optional<MultipartFile> segmentMedia,
         Optional<String> hashtagValue, Optional<List<String>> keywordValue, Optional<String> sellerQuestionValue,
         Optional<String> sellerQuestionType, Optional<String> sellerQuestionLimit,
         Optional<String> sellerQuestionRequired, Optional<String> typeOfPrice,
         Optional<List<String>> sellerQuestionOptions) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'createAuxiliarProduct'");
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
