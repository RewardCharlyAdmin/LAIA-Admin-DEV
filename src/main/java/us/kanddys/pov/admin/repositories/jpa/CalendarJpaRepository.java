package us.kanddys.pov.admin.repositories.jpa;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Calendar;

@Repository
public interface CalendarJpaRepository extends JpaRepository<Calendar, Long> {

   @Query(value = "SELECT c.delay_type as type, c.delay as delay, c.id as id FROM calendars c WHERE c.merchant = ?1", nativeQuery = true)
   Map<String, Object> findTypeAndDelayAndCalendarIdByMerchantId(Long merchantId);
}
