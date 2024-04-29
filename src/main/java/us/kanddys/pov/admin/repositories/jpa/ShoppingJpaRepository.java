package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Shopping;

@Repository
public interface ShoppingJpaRepository extends JpaRepository<Shopping, Long> {

   @Query("SELECT id as buyerId, sum(total) as total FROM Shopping WHERE buyerId IN :itemBuyerIds GROUP BY buyerId")
   List<Map<String, Object>> findTotalMaxByBuyerIds(List<Long> itemBuyerIds);

   @Query("SELECT id as buyerId, sum(count) as count FROM Shopping WHERE buyerId IN :itemBuyerIds GROUP BY buyerId")
   List<Map<String, Object>> findCountMaxByBuyerIds(List<Long> itemBuyerIds);

   @Query("SELECT id as buyerId, sum(count) as count FROM Shopping WHERE buyerId IN :itemBuyerIds GROUP BY buyerId ORDER BY count DESC")
   List<Map<String, Object>> findMaxCountCountByBuyerIds(List<Long> itemBuyerIds);

   @Query(value = "SELECT MAX(total) as valueMax, MIN(total) as valueMin FROM shoppings", nativeQuery = true)
   Map<String, Object> findMaxMinTotal();

   @Query(value = "SELECT MAX(CONCAT(year, '-', LPAD(month, 2, '0'), '-', LPAD(day, 2, '0'))) AS valueTo, MIN(CONCAT(year, '-', LPAD(month, 2, '0'), '-', LPAD(day, 2, '0'))) AS valueFrom FROM shoppings", nativeQuery = true)
   Map<String, Object> findMaxMinDate();
}
