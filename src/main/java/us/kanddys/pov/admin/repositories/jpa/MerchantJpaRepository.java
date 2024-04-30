package us.kanddys.pov.admin.repositories.jpa;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Merchant;

@Repository
public interface MerchantJpaRepository extends JpaRepository<Merchant, Long> {

   @Query(value = "SELECT email, slug, phone FROM merchants WHERE id = ?1", nativeQuery = true)
   Map<String, Object> findMerchantEmailAndSlugByMerchantId(Long merchantId);

   @Query(value = "SELECT id FROM merchants WHERE id = ?1", nativeQuery = true)
   Optional<Long> existById(Long merchantId);

}
