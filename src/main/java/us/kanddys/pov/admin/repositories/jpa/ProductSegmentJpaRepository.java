package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.ProductSegment;

@Repository
public interface ProductSegmentJpaRepository extends JpaRepository<ProductSegment, Long> {

   public List<ProductSegment> findProductDetailsByProductId(Long productId);

   @Query(value = "SELECT COUNT(*) FROM products_details WHERE product_id = ?1", nativeQuery = true)
   public Integer countProductDetailsByProductId(Long productId);

   @Query(value = "SELECT url, type FROM products_details WHERE product_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
   public Map<String, Object> findLastProductDetailByProductId(Long productId);
}
