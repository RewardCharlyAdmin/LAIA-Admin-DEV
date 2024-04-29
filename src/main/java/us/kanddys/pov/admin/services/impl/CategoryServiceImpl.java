package us.kanddys.pov.admin.services.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.exceptions.CategoryNotFoundException;
import us.kanddys.pov.admin.exceptions.utils.ExceptionMessage;
import us.kanddys.pov.admin.models.Category;
import us.kanddys.pov.admin.models.CategoryProduct;
import us.kanddys.pov.admin.models.dtos.ProductDTO;
import us.kanddys.pov.admin.models.utils.DateUtils;
import us.kanddys.pov.admin.models.utils.compositePKs.CategoryProductId;
import us.kanddys.pov.admin.repositories.jpa.CategoryJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.CategoryProductJpaRepository;
import us.kanddys.pov.admin.repositories.jpa.ProductJpaRepository;
import us.kanddys.pov.admin.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

   @Autowired
   private CategoryJpaRepository categoryJpaRepository;

   @Autowired
   private CategoryProductJpaRepository categoryProductJpaRepository;

   @Autowired
   private ProductJpaRepository productJpaRepository;

   @Override
   public Integer cAdminCategory(String category, Long merchant) {
      try {
         categoryJpaRepository
               .save(new Category(null, merchant, category, DateUtils.getCurrentDateWitheoutTime(), null));
      } catch (ParseException e) {
         throw new RuntimeException("Error al convertir la fecha actual");
      }
      return 1;
   }

   @Override
   public Integer uAdminCategory(Long id, String category) {
      Category categoryOptional = categoryJpaRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(ExceptionMessage.LIBRARY_NOT_FOUND));
      categoryOptional.setCategory(category);
      return 1;
   }

   @Override
   public Integer dAdminCategory(Long id) {
      categoryJpaRepository.deleteById(id);
      return 1;
   }

   @Override
   public Integer aAdminProductCategory(Long id, List<Long> products) {
      categoryProductJpaRepository.saveAll(
            products.stream().map(product -> new CategoryProduct(new CategoryProductId(id, product))).toList());
      return 1;
   }

   @Override
   public Integer uAdminProductCategory(Long id, List<Long> products) {
      categoryProductJpaRepository.deleteByCategoryId(id);
      categoryProductJpaRepository.saveAll(
            products.stream().map(product -> new CategoryProduct(new CategoryProductId(id, product))).toList());
      return 1;
   }

   @Override
   public Integer dAdminProductCategory(Long id) {
      categoryProductJpaRepository.deleteByCategoryId(id);
      return 1;
   }

   @Override
   public Set<ProductDTO> gAdminCategoryProducts(Long id) {
      // ! Extraemos todos los ids de los productos registrados en una categoria.
      return productJpaRepository.findAllById(categoryProductJpaRepository.findAllByCategoryId(id)).stream()
            .map(ProductDTO::new).collect(Collectors.toSet());
   }
}
