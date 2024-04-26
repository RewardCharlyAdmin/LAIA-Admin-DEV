package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import us.kanddys.pov.admin.models.Product;

@Service
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

   @Query(value = "SELECT id FROM products WHERE id = ?1", nativeQuery = true)
   Optional<Long> findProductIdIfExists(Long id);

   @Modifying
   @Query(value = "UPDATE products SET front_page = ?2 WHERE id = ?1", nativeQuery = true)
   void updateFrontPage(Long productId, String image);

   @Query(value = "SELECT * FROM products WHERE id IN ?1", nativeQuery = true)
   Set<Product> findAllByProductIds(Set<Long> productIds);
}
