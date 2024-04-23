package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import us.kanddys.pov.admin.models.dtos.ProductSegmentDTO;
import us.kanddys.pov.admin.models.dtos.ProductSegmentShortDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface ProductSegmentService {
   /**
    * Este método retorna los detalles de un producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return List<ProductSegmentDTO>
    */
   List<ProductSegmentDTO> getProductSegmentsByProductId(Long productId);

   /**
    * Este método crea un nuevo detalle de producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param description
    * @param frontPage
    * @param productId
    * @param title
    * @return ProductSegmentDTO
    */
   public ProductSegmentDTO createProductSegment(Optional<String> title, Optional<MultipartFile> frontPage,
         Long productId,
         Optional<String> description);

   /**
    * Este método crea un nuevo detalle de producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param description
    * @param media
    * @param productId
    * @param title
    * @return ProductSegmentDTO
    */
   public ProductSegmentDTO createProductSegmentMediaString(Optional<String> title, Optional<String> media,
         Long productId, Optional<String> description);

   /**
    * Este método se encarga de obtener el detalle acortado de un producto.
    *
    * @author Igirod0
    * @version 1.0.0
    * @param productId
    * @return ProductSegmentShortDTO
    */
   public ProductSegmentShortDTO getProductSegmentShort(Long productId);
}
