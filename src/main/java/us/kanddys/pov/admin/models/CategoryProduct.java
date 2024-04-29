package us.kanddys.pov.admin.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.kanddys.pov.admin.models.utils.compositePKs.CategoryProductId;

@AllArgsConstructor
@Data
@Table(name = "categories_products")
@Entity
public class CategoryProduct {

   @EmbeddedId
   private CategoryProductId id;

   public CategoryProduct() {
   }
}
