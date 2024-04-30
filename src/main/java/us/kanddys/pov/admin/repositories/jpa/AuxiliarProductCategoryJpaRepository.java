package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.AuxiliarProductCategory;

@Repository
public interface AuxiliarProductCategoryJpaRepository extends JpaRepository<AuxiliarProductCategory, Long> {

   @Query("SELECT apc FROM AuxiliarProductCategory apc WHERE apc.auxProduct = ?1")
   AuxiliarProductCategory findByProduct(Long product);
}
