package us.kanddys.pov.admin.services;

import java.util.List;

import us.kanddys.pov.admin.models.dtos.ProductMediaDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface ProductMediaService {

   /**
    * Este método tiene la obligación de traer todas las imagenes de un
    * producto buscando por si id.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return List<ImageProductDTO>
    */
   public List<ProductMediaDTO> getImagesProductByProductId(Long productId);
}
