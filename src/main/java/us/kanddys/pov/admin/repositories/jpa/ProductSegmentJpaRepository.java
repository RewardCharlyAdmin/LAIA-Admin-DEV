package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.ProductSegment;

@Repository
public interface ProductSegmentJpaRepository extends JpaRepository<ProductSegment, Long> {

   public List<ProductSegment> findProductDetailsByProduct(Long productId);

   @Query(value = "SELECT COUNT(*) FROM products_segments WHERE product = ?1", nativeQuery = true)
   public Integer countProductDetailsByProductId(Long productId);

   @Query(value = "SELECT url, type FROM products_segments WHERE product = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
   public Map<String, Object> findLastProductDetailByProductId(Long productId);
}
