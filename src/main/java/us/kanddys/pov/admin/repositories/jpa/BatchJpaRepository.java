package us.kanddys.pov.admin.repositories.jpa;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Batch;

@Repository
public interface BatchJpaRepository extends JpaRepository<Batch, Long> {

   @Query(value = "SELECT days FROM batches e WHERE calendar_id = ?1", nativeQuery = true)
   Set<Integer> findDaysByCalendarId(Long calendarId);

   @Query(value = "SELECT CAST(date as CHAR) FROM batches WHERE calendar_id = ?1 AND date BETWEEN ?2 AND ?3 AND date IS NOT NULL", nativeQuery = true)
   Set<String> findExceptionBatchesDatesByCalendarIdAndDateNotNull(Long calendarId, Date startDate, Date endDate);

   @Query(value = "SELECT * FROM batches WHERE calendar_id = ?1 AND date = ?2 AND date IS NOT NULL", nativeQuery = true)
   Set<Batch> findExceptionBatchesByCalendarIdAndDateNotNull(Long calendarId, Date date);

   @Query(value = "SELECT * FROM batches WHERE calendar_id = ?1 AND days LIKE %?2% AND date IS NULL", nativeQuery = true)
   Set<Batch> findByCalendarIdAndDaysContainingAndDateIsNull(Long calendarId, Integer days);

   @Query(value = "SELECT CAST(from_value AS CHAR), CAST(to_value AS CHAR) FROM batches WHERE id = :id", nativeQuery = true)
   Map<String, String> findFromTimeAndToTimeById(Long id);

   @Query(value = "SELECT * FROM batches WHERE calendar_id = ?1 AND date BETWEEN ?2 AND ?3 AND date IS NOT NULL", nativeQuery = true)
   Set<Batch> findExceptionlBatchesByCalendarId(Long calendarId, Date startDate, Date endDate);

   @Query(value = "SELECT * FROM batches WHERE calendar_id = ?1 AND date IS NULL", nativeQuery = true)
   Set<Batch> findNormalBatchesByCalendarId(Long calendarId);
}
