package us.kanddys.pov.admin.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.services.CategoryService;

@Controller
public class CategoryController {

   @Autowired
   private CategoryService categoryService;

   @QueryMapping
   public Set<ProductDTO> gAdminCategoryProducts(@Argument Long id) {
      return categoryService.gAdminCategoryProducts(id);
   }

   @MutationMapping
   public Long cAdminCategory(@Argument String category, @Argument Long merchant) {
      return categoryService.cAdminCategory(category, merchant);
   }

   @MutationMapping
   public Integer uAdminCategory(@Argument Long id, @Argument String category) {
      return categoryService.uAdminCategory(id, category);
   }

   @MutationMapping
   public Integer dAdminCategory(@Argument Long id) {
      return categoryService.dAdminCategory(id);
   }

   @MutationMapping
   public Integer aAdminProductCategory(@Argument Long id, @Argument List<Long> products) {
      return categoryService.aAdminProductCategory(id, products);
   }

   @MutationMapping
   public Integer uAdminProductCategory(@Argument Long id, @Argument List<Long> products) {
      return categoryService.uAdminProductCategory(id, products);
   }

   @MutationMapping
   public Integer dAdminProductCategory(@Argument Long id) {
      return categoryService.dAdminProductCategory(id);
   }
}
