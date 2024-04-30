package us.kanddys.pov.admin.repositories.jpa;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Repository
public interface ExceptionJpaRepository extends JpaRepository<us.kanddys.pov.admin.models.Exception, Long> {

   @Query(value = "SELECT * FROM exceptions WHERE calendar = ?1 AND date = ?2 AND date IS NOT NULL", nativeQuery = true)
   Set<us.kanddys.pov.admin.models.Exception> findExceptionsByCalendarIdAndDateNotNull(Long calendar,
         Date date);

   @Query(value = "SELECT CAST(date as CHAR) FROM exceptions WHERE calendar = ?1 AND date BETWEEN ?2 AND ?3 AND date IS NOT NULL", nativeQuery = true)
   Set<String> findExceptionsDatesByCalendarIdAndDateNotNull(Long calendarId,
         Date startDate, Date endDate);
}
