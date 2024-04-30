package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarProductKeyword;

@Repository
public interface AuxiliarProductKeywordJpaRepository extends JpaRepository<AuxiliarProductKeyword, Long> {

   @Query(value = "SELECT keyword FROM aux_product_keywords WHERE product = ?1", nativeQuery = true)
   List<Long> findByProduct(Long product);

   @Modifying
   @Query(value = "DELETE FROM aux_products_key_words WHERE aux_product_id = ?1", nativeQuery = true)
   void deleteWordsByProductId(Long productId);

   @Query(value = "SELECT keyword FROM aux_product_keywords WHERE product = ?1", nativeQuery = true)
   List<String> findKeywordsByIds(List<Long> byProduct);
}
