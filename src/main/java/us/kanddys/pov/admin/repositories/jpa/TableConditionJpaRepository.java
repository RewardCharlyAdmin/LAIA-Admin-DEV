package us.kanddys.pov.admin.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import us.kanddys.pov.admin.models.TableCondition;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface TableConditionJpaRepository extends JpaRepository<TableCondition, Long> {

   @Query(value = "SELECT * FROM table_conditions WHERE library_id = ?1", nativeQuery = true)
   public List<TableCondition> findByLibraryId(Long libraryId);
}
