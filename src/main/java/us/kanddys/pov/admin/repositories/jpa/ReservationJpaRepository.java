package us.kanddys.pov.admin.repositories.jpa;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import us.kanddys.pov.admin.models.Reservation;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

   @Query(value = "SELECT batch_id, COUNT(*), CAST(Date as CHAR) FROM reservations WHERE batch_id IN :batchIds AND `date` BETWEEN :startDate AND :endDate GROUP BY date", nativeQuery = true)
   Set<Object[]> countRecordsByBatchIdsAndDate(List<Long> batchIds, Date startDate, Date endDate);
}
