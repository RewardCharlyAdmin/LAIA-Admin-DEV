package us.kanddys.pov.admin.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.ProductSegmentShort;

@Repository
public interface ProductSegmentShortJpaRepository extends JpaRepository<ProductSegmentShort, Long> {

   Optional<ProductSegmentShort> findProductSegmentsByProductId(Long productId);
}
