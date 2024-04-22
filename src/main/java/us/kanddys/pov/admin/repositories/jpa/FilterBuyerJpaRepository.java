package us.kanddys.pov.admin.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import us.kanddys.pov.admin.models.FilterBuyer;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface FilterBuyerJpaRepository extends JpaRepository<FilterBuyer, Long> {

   @Query("SELECT fb FROM FilterBuyer fb WHERE fb.collectionId = ?1")
   public Optional<FilterBuyer> findByCollectionId(Long collectionId);
}
