package us.kanddys.pov.admin.services;

import java.util.List;
import java.util.Set;

import us.kanddys.pov.admin.models.dtos.ProductDTO;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface CategoryService {

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Set<ProductDTO> gAdminCategoryProducts(Long id);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer cAdminCategory(String category, Long merchant);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uAdminCategory(Long id, String category);

   /**
    * @author Igirod0
    * @version 1.0.0
    */

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer dAdminCategory(Long id);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer aAdminProductCategory(Long id, List<Long> products);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer uAdminProductCategory(Long id, List<Long> products);

   /**
    * @author Igirod0
    * @version 1.0.0
    */
   public Integer dAdminProductCategory(Long id);
}
