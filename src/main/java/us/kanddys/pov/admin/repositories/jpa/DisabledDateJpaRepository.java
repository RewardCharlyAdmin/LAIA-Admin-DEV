package us.kanddys.pov.admin.repositories.jpa;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.DisabledDate;

@Repository
public interface DisabledDateJpaRepository extends JpaRepository<DisabledDate, Long> {

   @Query(value = "SELECT CAST(e.date AS CHAR) FROM disables e WHERE calendar = ?3 AND e.date BETWEEN ?1 AND ?2", nativeQuery = true)
   Set<String> findDisabedDatesByCalendarIdRange(Date startDate, Date endDate, Long calendarId);
}
