package us.kanddys.pov.admin.repositories.jpa;

import java.util.Calendar;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarJpaRepository extends JpaRepository<Calendar, Long> {

   @Query(value = "SELECT c.type_delay as type, c.delay as delay, c.id as id FROM calendar c WHERE c.merchant_id = ?1", nativeQuery = true)
   Map<String, Object> findTypeAndDelayAndCalendarIdByMerchantId(Long merchantId);
}
