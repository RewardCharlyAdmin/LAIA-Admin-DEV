package us.kanddys.pov.admin.repositories.jpa;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.CategoryProduct;
import us.kanddys.pov.admin.models.utils.compositePKs.CategoryProductId;

@Repository
public interface CategoryProductJpaRepository extends JpaRepository<CategoryProduct, CategoryProductId> {

   @Query("DELETE FROM CategoryProduct cp WHERE cp.id.categoryId = :categoryId")
   void deleteByCategoryId(Long categoryId);

   @Query("SELECT cp.id.productId FROM CategoryProduct cp WHERE cp.id.categoryId = :id")
   Set<Long> findAllByCategoryId(Long id);
}
