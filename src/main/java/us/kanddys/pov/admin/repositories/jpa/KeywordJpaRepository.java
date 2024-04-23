package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Keyword;

@Repository
public interface KeywordJpaRepository extends JpaRepository<Keyword, Long> {

   @Query(value = "SELECT COUNT(*) FROM keywords_products WHERE product_id = ?1", nativeQuery = true)
   public Integer countKeyWordProductByProductId(Long productId);
}
