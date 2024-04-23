package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarProductSegment;

@Repository
public interface AuxiliarProductSegmentJpaRepository extends JpaRepository<AuxiliarProductSegment, Long> {

   @Query(value = "SELECT * FROM aux_product_segments WHERE product = ?1", nativeQuery = true)
   AuxiliarProductSegment findByProduct(Long product);
}
