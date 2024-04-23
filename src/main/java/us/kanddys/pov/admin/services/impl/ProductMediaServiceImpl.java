package us.kanddys.pov.admin.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.dtos.ProductMediaDTO;
import us.kanddys.pov.admin.repositories.jpa.ProductMediaJpaRepository;
import us.kanddys.pov.admin.services.ProductMediaService;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class ProductMediaServiceImpl implements ProductMediaService {

   @Autowired
   private ProductMediaJpaRepository productMediaJpaRepository;

   @Override
   public List<ProductMediaDTO> getImagesProductByProductId(Long productId) {
      return productMediaJpaRepository.findAllByProduct(productId).stream().map(ProductMediaDTO::new)
            .toList();
   }

}
