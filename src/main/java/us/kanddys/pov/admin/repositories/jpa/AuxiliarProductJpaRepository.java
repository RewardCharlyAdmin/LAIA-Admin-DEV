package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarProduct;

@Repository
public interface AuxiliarProductJpaRepository extends JpaRepository<AuxiliarProduct, Long> {

   @Modifying
   @Query(value = "UPDATE aux_products SET front_page = ?1 WHERE aux_product_id = ?2", nativeQuery = true)
   void updateFrontPage(String frontPage, Long auxProductId);

   @Modifying
   @Query(value = "UPDATE aux_products SET segment_media = ?1 WHERE aux_product_id = ?2", nativeQuery = true)
   void updateSegmentMedia(String segmentMedia, Long auxProductId);
}
