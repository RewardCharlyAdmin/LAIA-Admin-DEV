package us.kanddys.pov.admin.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Buyer;

@Repository
public interface BuyerJpaRepository extends JpaRepository<Buyer, Long> {

   @Query(value = "SELECT count(*) FROM buyers WHERE merchant_id = ?1", nativeQuery = true)
   public Integer findCountBuyersByUserId(Long userId);
}
