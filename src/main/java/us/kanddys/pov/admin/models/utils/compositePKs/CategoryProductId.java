package us.kanddys.pov.admin.models.utils.compositePKs;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@Embeddable
public class CategoryProductId {
   private Long categoryId;
   private Long productId;

   public CategoryProductId() {
   }
}
