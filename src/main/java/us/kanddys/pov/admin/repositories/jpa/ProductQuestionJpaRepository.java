package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.ProductQuestion;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Repository
public interface ProductQuestionJpaRepository extends JpaRepository<ProductQuestion, Long> {

   @Query(value = "SELECT COUNT(*) FROM product_questions WHERE product_id = ?1", nativeQuery = true)
   public Integer countQuestionsByProductId(Long productId);
}
