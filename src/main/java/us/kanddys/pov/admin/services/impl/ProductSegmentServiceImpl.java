package us.kanddys.pov.admin.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import us.kanddys.pov.admin.exceptions.ProductNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.ProductSegment;
import us.kanddys.pov.admin.models.dtos.ProductSegmentDTO;
import us.kanddys.pov.admin.models.dtos.ProductSegmentShortDTO;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductSegmentShortJpaRepository;
import us.kanddys.pov.admin.services.ProductSegmentService;
import us.kanddys.pov.admin.services.storage.FirebaseStorageService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class ProductSegmentServiceImpl implements ProductSegmentService {

   @Autowired
   private ProductSegmentJpaRepository productSegmentJpaRepository;

   @Autowired
   private FirebaseStorageService firebaseStorageService;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Autowired
   private ProductSegmentShortJpaRepository productSegmentShortJpaRepository;

   @Override
   public List<ProductSegmentDTO> getProductSegmentsByProductId(Long productId) {
      return productSegmentJpaRepository.findProductDetailsByProductId(productId).stream().map(t -> {
         try {
            return new ProductSegmentDTO(t);
         } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
         }
      })
            .collect(Collectors.toList());
   }

   @Transactional(rollbackOn = { Exception.class, RuntimeException.class })
   @Override
   public ProductSegmentDTO createProductSegment(Optional<String> title, Optional<MultipartFile> frontPage,
         Long productId, Optional<String> description) {
      if (productJpaRepository.findProductIdIfExists(productId).isEmpty())
         throw new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND);
      try {
         return new ProductSegmentDTO(
               productSegmentJpaRepository.save(new ProductSegment(null, productId,
                     title.orElse(null), description.orElse(null),
                     (frontPage.isPresent())
                           ? firebaseStorageService.uploadFile(frontPage.get(),
                                 "product-detail-" + productId.toString() + "-" + UUID.randomUUID().toString(),
                                 "imageProducts")
                           : null,
                     "IMAGE")));
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
   }

   @Override
   public ProductSegmentShortDTO getProductSegmentShort(Long productId) {
      return new ProductSegmentShortDTO(productSegmentShortJpaRepository.findProductDetailsByProductId(productId)
            .orElseThrow(() -> new ProductNotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND)));
   }

   @Override
   public ProductSegmentDTO createProductSegmentFrontPageString(Optional<String> title, Optional<String> frontPage,
         Long productId, Optional<String> description) {
      try {
         return new ProductSegmentDTO(
               productSegmentJpaRepository.save(new ProductSegment(null, productId, title.orElse(null),
                     description.orElse(null), frontPage.orElse(null), "IMAGE")));
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
   }
}
